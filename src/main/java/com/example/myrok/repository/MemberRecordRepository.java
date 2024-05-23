package com.example.myrok.repository;

import com.example.myrok.domain.MemberRecord;
import com.example.myrok.type.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRecordRepository extends JpaRepository<MemberRecord,Long> {
    List<MemberRecord> findAllByRecordId(Long id);
    Optional<MemberRecord> findByMemberIdAndRecordIdAndDeletedIsFalse(Long memberId, Long recordId);
}
