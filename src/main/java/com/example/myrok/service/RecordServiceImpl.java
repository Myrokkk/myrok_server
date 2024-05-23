package com.example.myrok.service;

import com.example.myrok.event.RecordSavedEvent;
import com.example.myrok.domain.*;
import com.example.myrok.domain.Record;
import com.example.myrok.dto.RecordDTO;
import com.example.myrok.dto.RecordResponseDTO;
import com.example.myrok.dto.RecordUpdateDTO;
import com.example.myrok.exception.CustomException;
import com.example.myrok.repository.*;
import com.example.myrok.type.ErrorCode;
import com.example.myrok.type.Role;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.myrok.type.MemberProjectType.PROJECT_MEMBER;

@RequiredArgsConstructor
@Service
public class RecordServiceImpl implements RecordService{

    @Autowired
    private final RecordRepository recordRepository;
    @Autowired
    private final ProjectRepository projectRepository;
    @Autowired
    private final MemberProjectRepository memberProjectRepository;

    @Autowired
    private RecordTagService recordTagService;
    @Autowired
    private MemberRecordService memberRecordService;
    @Autowired
    private MemberRecordRepository memberRecordRepository;


    private final ApplicationEventPublisher eventPublisher;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Record save(RecordDTO recordDTO){

        // 멤버 리스트 & 태그 리스트 받아와서 Record 저장
        List<String> tags = recordDTO.tagList();
        List<Long> members = recordDTO.memberList();
        Long projectId = recordDTO.projectId();
        Long recordWriterId=recordDTO.recordWriterId();

        // 작성자 아이디가 멤버 리스트에 없다면 예외
        if (!members.contains(recordWriterId)){
            throw new IllegalArgumentException("작성자 아이디가 멤버 리스트에 없습니다.");
        }

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 프로젝트입니다. id: " + projectId));

        // 멤버가 프로젝트 소속인지 확인
        for (Long memberId : members){
            MemberProject memberProject = memberProjectRepository.findByMemberIdAndProjectId(memberId,projectId)
                    .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_IN_PROJECT, HttpStatus.BAD_REQUEST));
            // 탈퇴 여부도 확인.
            if (memberProject.getMemberProjectType()!=PROJECT_MEMBER){
                throw new CustomException(ErrorCode.NON_PROJECT_MEMBER_ERROR, HttpStatus.BAD_REQUEST);
            }
        }

        Record record=recordDTO.toEntity(project);
        Record savedRecord = recordRepository.save(record);

        // Tag 저장
        for (String tagName : tags) {
            recordTagService.save(projectId,savedRecord,tagName);
        }

        memberRecordService.save(members,savedRecord,recordWriterId);

        eventPublisher.publishEvent(new RecordSavedEvent(savedRecord));

        return savedRecord;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteUpdate(Long recordId) {
        // 회의록 삭제
        Record record = recordRepository.findById(recordId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 회의록입니다. id: " + recordId));
        if (record.getDeleted()) {
            throw new CustomException(ErrorCode.DELETED_RECORD_CODE, HttpStatus.BAD_REQUEST);
        }
        record.delete();
        recordRepository.save(record);

        //MemberRecord 매핑객체 삭제
        memberRecordService.delete(record.getId());

        //RecordTag 매핑객체 삭제
        recordTagService.delete(record.getId());

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Record update(Long recordId, RecordUpdateDTO recordUpdateDTO) {
        List<String> updatedTags = recordUpdateDTO.tagList();
        Long recordWriterId = recordUpdateDTO.recordWriterId();
        Record record = recordRepository.findById(recordId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 회의록입니다. id: " + recordId));
        //삭제된 회의록이라면 예외
        if(record.getDeleted()){
            throw new CustomException(ErrorCode.DELETED_RECORD_CODE, HttpStatus.BAD_REQUEST);
        }
        //회의 참여자가 아니라면 예외
        MemberRecord memberRecord =memberRecordRepository.findByMemberIdAndRecordIdAndDeletedIsFalse(recordWriterId,recordId)
                .orElseThrow(() -> new CustomException(ErrorCode.WRONG_RECORD_ACCESS, HttpStatus.BAD_REQUEST));
        //작성자 외 수정 시도시 예외
        if (memberRecord.getRole()!=Role.ADMIN){
            throw new CustomException(ErrorCode.WRONG_UPDATE_ACCESS, HttpStatus.BAD_REQUEST);
        }
        //수정 로직

        //이전 태그들 삭제
        recordTagService.delete(record.getId());

        List<RecordTag> updateRecordTagList = new ArrayList<>();
        for (String tagName : updatedTags) {
            updateRecordTagList.add(recordTagService.save(record.getProject().getId(),record,tagName));
        }

        record.setRecordName(recordUpdateDTO.recordName());
        record.setRecordTagList(updateRecordTagList);

        return recordRepository.save(record);
    }


}
