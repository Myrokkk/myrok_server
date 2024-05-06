package com.example.myrok.service;

import com.example.myrok.controller.RecordSavedEvent;
import com.example.myrok.domain.*;
import com.example.myrok.domain.Record;
import com.example.myrok.dto.RecordDTO;
import com.example.myrok.exception.CustomException;
import com.example.myrok.repository.*;
import com.example.myrok.type.ErrorCode;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
    private TagService tagService;

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
            tagService.save(tagName);
        }

        memberRecordService.save(members,savedRecord,recordWriterId);
        recordTagService.save(tags,savedRecord);

        eventPublisher.publishEvent(new RecordSavedEvent(savedRecord));

        return savedRecord;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteUpdate(Long recorId) {
        // 회의록 삭제
        Record record = recordRepository.findById(recorId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 회의록입니다. id: " + recorId));
        if (record.getDeleted()) {
            throw new CustomException(ErrorCode.DELETED_RECORD_CODE, HttpStatus.BAD_REQUEST);
        }
        record.delete();
        recordRepository.save(record);

        //회의록 안의 태그 리스트 삭제
        tagService.delete(record.getRecordTagList());

        //MemberRecord 매핑객체 삭제
        memberRecordService.delete(record.getId());

        //RecordTag 매핑객체 삭제
        recordTagService.delete(record.getId());

    }
}
