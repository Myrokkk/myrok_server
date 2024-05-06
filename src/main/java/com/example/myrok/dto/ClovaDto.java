package com.example.myrok.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

public class ClovaDto {
    @Data
    @NoArgsConstructor
    @Builder
    @AllArgsConstructor
    public static class RecordRequestDto{
        //회의록 아이디
        private Long id;
    }

    @Data
    @NoArgsConstructor
    @Builder
    @AllArgsConstructor
    public static class RequestDto{
        @NotEmpty
        private String title;
        @NotEmpty
        private String content;
    }

    @Data
    @NoArgsConstructor
    @Builder
    @AllArgsConstructor
    public static class ResponseDto{
        //회의록 아이디
        private Long id;
        @NotEmpty
        private String summary;
    }
}
