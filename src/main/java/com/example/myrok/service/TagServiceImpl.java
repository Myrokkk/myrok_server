package com.example.myrok.service;

import com.example.myrok.domain.RecordTag;
import com.example.myrok.domain.Tag;
import com.example.myrok.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TagServiceImpl implements TagService{
    @Autowired
    private TagRepository tagRepository;
    @Override
    public Tag save(String tagName){
        Tag tag = tagRepository.findByTagName(tagName)
                .orElseGet(() -> tagRepository.save(new Tag(tagName,0,false)));
        // count 증가
        tag.incrementCount();
        tagRepository.save(tag);
        return tag;
    }
    @Transactional
    @Override
    public void delete(List<Tag> tagList){
        //Tag Count 감소, 0이라면 삭제
        //deleted 여부도 검사해야될듯
        for (Tag tag : tagList) {
            tag.decrementCount();
            tagRepository.save(tag);
            if (tag.getCount() == 0) {
                tagRepository.delete(tag);
            }
        }

    }
}
