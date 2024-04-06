package com.example.myrok.service;

import com.example.myrok.domain.Record;
import com.example.myrok.domain.RecordTag;
import com.example.myrok.domain.Tag;
import com.example.myrok.repository.RecordTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordTagServiceImpl implements RecordTagService{

    @Autowired
    RecordTagRepository recordTagRepository;
    @Override
    public void save(Record record, Tag tag){
        RecordTag recordTag = RecordTag.builder()
                .record(record)
                .tag(tag)
                .build();
        recordTagRepository.save(recordTag);
    }
    @Override
    public void delete(Long id){
        List<RecordTag> recordTags = recordTagRepository.findAllByRecordId(id);
        for (RecordTag recordTag : recordTags) {
            recordTagRepository.delete(recordTag);
        }

    }
}
