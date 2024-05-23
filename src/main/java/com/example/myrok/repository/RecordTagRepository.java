package com.example.myrok.repository;

import com.example.myrok.domain.RecordTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordTagRepository extends JpaRepository<RecordTag,Long> {
    List<RecordTag> findAllByRecordIdAndDeletedIsFalse(Long id);
}
