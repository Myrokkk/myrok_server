package com.example.myrok.service;

import com.example.myrok.domain.Member;
import com.example.myrok.domain.MemberRecord;
import com.example.myrok.domain.Record;
import com.example.myrok.domain.RecordTag;
import com.example.myrok.exception.NotFoundException;
import com.example.myrok.repository.MemberRecordRepository;
import com.example.myrok.repository.MemberRepository;
import com.example.myrok.type.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberRecordServiceImpl implements MemberRecordService{
    @Autowired
    MemberRecordRepository memberRecordRepository;
    @Autowired
    MemberRepository memberRepository;
    @Override
    public void save(List<Long> members, Record record, Role role){
        for (Long memberId : members) {
            Member member = memberRepository.findById(memberId)
                    .orElseThrow(() -> new NotFoundException("존재하지 않는 팀원입니다."));

            MemberRecord memberRecord = MemberRecord.builder()
                    .record(record)
                    .member(member)
                    .role(role)
                    .build();
            memberRecordRepository.save(memberRecord);
        }
    }
    @Override
    public void delete(Long id){
        List<MemberRecord> memberRecords = memberRecordRepository.findAllByRecordId(id);
        for (MemberRecord memberRecord : memberRecords) {
            memberRecord.delete();
            memberRecordRepository.save(memberRecord);
        }
    }
}
