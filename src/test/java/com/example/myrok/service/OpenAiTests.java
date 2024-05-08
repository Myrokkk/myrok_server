package com.example.myrok.service;

import com.example.myrok.event.RecordSavedEvent;
import com.example.myrok.domain.Record;
import com.example.myrok.dto.ClovaDto;
import com.example.myrok.repository.RecordRepository;
import com.example.myrok.service.openapi.RecordSummaryServiceImpl;
import com.example.myrok.service.openapi.OpenAIService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;

import java.time.LocalDate;

@SpringBootTest
public class OpenAiTests {

    @Autowired
    OpenAIService openAi;
    @Autowired
    RecordSummaryServiceImpl clovaSummary;
    @Autowired
    RecordRepository recordRepository;
    @Autowired
    ApplicationEventPublisher eventPublisher;
    @Test
    public void openAitest() throws JsonProcessingException, JSONException {
        openAi.makeRequest();
        openAi.makeThread();
        openAi.makeMessage();
        openAi.makeThreadRun();
        openAi.getMessage();
    }

    @Test
    public void clovaTest() throws JsonProcessingException, JSONException {
        ClovaDto.RecordRequestDto requestDto = ClovaDto.RecordRequestDto.builder()
                .id(4L)
                .build();
        clovaSummary.makeRecordSummary(requestDto);
    }

    @Test
    public void getSummary() throws JsonProcessingException, JSONException {
        ClovaDto.RecordRequestDto requestDto = ClovaDto.RecordRequestDto.builder()
                .id(4L)
                .build();
        clovaSummary.getSummaryRecord(4L);
    }

    @Test
    public void saveRecord() throws JsonProcessingException, JSONException {
        Record record = Record
                .builder()
                .recordDate(LocalDate.now())
                .recordWriterId(1L)
                .recordContent("그러나 법조계에선 직무관련성을 인정받으려면 김 여사와 최 목사 간의 대가성 청탁까지 입증돼야 한다고 본다. 부장판사 출신 변호사는 “대통령의 인사권 범위가 넓긴 하지만, 실제 둘 사이 어떤 대화가 오갔는지가 중요하다”며 “직무 관련 부정 청탁 여부가 수사에서 규명돼야 할 부분”이라고 짚었다. 고위공무원단 소속 공무원으로 근무했던 한 변호사는 “김 여사와 최 목사가 어떤 이해관계인지, 구체적으로 어떤 청탁 이슈가 있었는지 드러나야 한다”고 말했다. 관련 수사 경험이 많은 검찰 관계자는 “대통령 직무권한이 포괄적이라 해도, (박 전 대통령의 뇌물죄가 인정된) 삼성 사건 정도의 대가성은 입증돼야 한다”며 “최 목사의 업무가 구체적이지 않아 받는 입장에서도 뇌물로 인지하기는 쉽지 않아 보인다”고 말했다.\n")
                .recordName("법조계")
                .build();

        Record record1 = recordRepository.save(record);

        RecordSavedEvent event = new RecordSavedEvent(record1);
        eventPublisher.publishEvent(event);
    }
}
