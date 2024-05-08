package com.example.myrok.component;

import com.example.myrok.event.RecordSavedEvent;
import com.example.myrok.domain.Record;
import com.example.myrok.dto.ClovaDto;
import com.example.myrok.service.openapi.RecordSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class RecordEventListener {

    private final RecordSummaryService recordSummaryService;

    @Autowired
    public RecordEventListener(RecordSummaryService recordSummaryService) {
        this.recordSummaryService = recordSummaryService;
    }

    @Async
    @EventListener
    public void handleRecordSavedEvent(RecordSavedEvent event) {
        // RecordSavedEvent를 수신하면 makeRecordSummary 메서드를 호출하여 두 번째 작업 수행
        Record record = event.getRecord();
        ClovaDto.RecordRequestDto requestDto = createRecordRequestDto(record);
        recordSummaryService.makeRecordSummary(requestDto);
    }

    private ClovaDto.RecordRequestDto createRecordRequestDto(Record record) {
        // Record를 이용하여 RecordRequestDto를 생성하는 비즈니스 로직 작성
        return ClovaDto.RecordRequestDto.builder()
                .id(record.getId())
                .build();
    }
}
