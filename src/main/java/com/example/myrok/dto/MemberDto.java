package com.example.myrok.dto;

import lombok.*;

public class MemberDto {
    @Data
    @NoArgsConstructor
    @Builder
    @AllArgsConstructor
    @ToString
    public static class MemberNameDto{
        private Long memberId;
        private String name;
    }
}
