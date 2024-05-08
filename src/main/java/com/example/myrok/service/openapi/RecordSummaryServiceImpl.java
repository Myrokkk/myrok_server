package com.example.myrok.service.openapi;

import com.example.myrok.component.ClovaSummaryComponent;
import com.example.myrok.domain.Record;
import com.example.myrok.dto.ClovaDto;
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

        StringBuilder totalSummary = new StringBuilder();
        if(content.length()>1000){
            for (int i = 0; i < content.length(); i += 1000) {
                int end = Math.min(i + 1000, content.length());
                String substring = content.substring(i, end);
                System.out.println("잘린 문자열: " + substring);
                System.out.println("길이: " + substring.length());
                ClovaDto.RequestDto requestSummary = ClovaDto.RequestDto
                        .builder()
                        .title(record.getRecordName())
                        .content(record.getRecordContent())
                        .build();
                String summary = clovaSummaryComponent.get(requestSummary);
                totalSummary.append(summary);
                if (substring.length() > 1000) {
                    throw new IllegalArgumentException("잘린 부분의 길이가 1000자를 초과했습니다.");
                }
            }
        }

        record.updateSummary(String.valueOf(totalSummary));
        return recordRepository.save(record).getId();
    }

    @Override
    public ClovaDto.ResponseDto getSummaryRecord(Long recordId) {
        Record record = recordRepository.findById(recordId)
                .orElseThrow(() -> new NoSuchElementException("No record found with ID: " + recordId));

        return ClovaDto.ResponseDto.builder()
                .summaryId(record.getId())
                .summary(record.getRecordContentSummary())
                .build();
    }
}
