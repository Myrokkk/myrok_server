package com.example.myrok.service;

import com.example.myrok.domain.*;
import com.example.myrok.domain.Record;
import com.example.myrok.dto.RecordDTO;
import com.example.myrok.exception.NotFoundException;
import com.example.myrok.repository.*;
import com.example.myrok.type.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RecordServiceImpl implements RecordService{

    @Autowired
    private final RecordRepository recordRepository;
    @Autowired
    private final MemberRepository memberRepository;
    @Autowired
    private final TagRepository tagRepository;
    @Autowired
    private final ProjectRepository projectRepository;

    @Autowired
    private RecordTagService recordTagService;
    @Autowired
    private MemberRecordService memberRecordService;
    @Autowired
    private TagService tagService;

    private static final Logger logger = LoggerFactory.getLogger(RecordServiceImpl.class);

    @Override
    public Record save(RecordDTO recordDTO){

        // 멤버 리스트 & 태그 리스트 받아와서 Record 저장
        List<String> tags = recordDTO.tagList();
        List<Long> members = recordDTO.memberList();
        Long projectId = recordDTO.projectId();

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException("해당 프로젝트를 찾을 수 없습니다. ID: " + projectId));

        Record record= recordDTO.toEntity(memberRepository.findAllByIdIn(members),
                                            tagRepository.findAllByTagNameIn(tags),
                                            project);
        Record savedRecord = recordRepository.save(record);

        // Tag 저장
        for (String tagName : tags) {
            Tag tag=tagService.save(tagName);
            // record 와 tag 매핑객체 생성
            recordTagService.save(record,tag);
        }
        // 멤버 권한 지정 및 매핑객체 생성
        Long recordWriterId=recordDTO.recordWriterId();
        for (Long memberId : members) {
            Role role = memberId.equals(recordWriterId) ? Role.ADMIN : Role.PARTICIPANT;
            memberRecordService.save(members, record, role);
        }

        return savedRecord;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteUpdate(Long id) {

            // 회의록 삭제
            Record record = recordRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("해당 회의록을 찾을 수 없습니다. ID: "+id));
            record.delete();
            recordRepository.save(record);

            //회의록 안의 태그 리스트 삭제
            tagService.delete(record.getTagList());

            //MemberRecord 매핑객체 삭제
            memberRecordService.delete(record.getId());

            //RecordTag 매핑객체 삭제
            recordTagService.delete(record.getId());

    }
}
