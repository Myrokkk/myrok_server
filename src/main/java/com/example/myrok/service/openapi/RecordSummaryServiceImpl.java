package com.example.myrok.service.openapi;

import com.example.myrok.component.ClovaSummaryComponent;
import com.example.myrok.domain.Record;
import com.example.myrok.dto.ClovaDto;
import com.example.myrok.repository.MemberProjectRepository;
import com.example.myrok.repository.RecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class RecordSummaryServiceImpl implements RecordSummaryService{

    private final RecordRepository recordRepository;
    private final ClovaSummaryComponent clovaSummaryComponent;


    @Override
    public Long makeRecordSummary(ClovaDto.RecordRequestDto requestDto) {
        Long recordId = requestDto.getId();
        Record record = recordRepository.findById(recordId)
                .orElseThrow(() -> new NoSuchElementException("No record found with ID: " + recordId));
        String content = record.getRecordContent();
        System.out.println(content.length());
        if(content.length()>1000){
          throw new IllegalArgumentException("1000자 이상임");
        }

        ClovaDto.RequestDto requestSummary = ClovaDto.RequestDto
                .builder()
                .title(record.getRecordName())
                .content(record.getRecordContent())
                .build();
        String summary = clovaSummaryComponent.get(requestSummary);
        record.updateSummary(summary);

        return recordRepository.save(record).getId();
    }

    @Override
    public ClovaDto.ResponseDto getSummaryRecord(Long recordId) {
        Record record = recordRepository.findById(recordId)
                .orElseThrow(() -> new NoSuchElementException("No record found with ID: " + recordId));

        return ClovaDto.ResponseDto.builder()
                .id(record.getId())
                .summary(record.getRecordContentSummary())
                .build();
    }
}
