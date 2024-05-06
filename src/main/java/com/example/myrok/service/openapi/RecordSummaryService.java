package com.example.myrok.service.openapi;

import com.example.myrok.dto.ClovaDto;
import org.springframework.stereotype.Service;

@Service
public interface RecordSummaryService {
    Long makeRecordSummary(ClovaDto.RecordRequestDto requestDto);

    ClovaDto.ResponseDto getSummaryRecord(Long recordId);
}
