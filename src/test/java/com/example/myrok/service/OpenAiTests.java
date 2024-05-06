package com.example.myrok.service;

import com.example.myrok.dto.ClovaDto;
import com.example.myrok.service.openapi.RecordSummaryServiceImpl;
import com.example.myrok.service.openapi.OpenAIService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OpenAiTests {

    @Autowired
    OpenAIService openAi;
    @Autowired
    RecordSummaryServiceImpl clovaSummary;

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
}
