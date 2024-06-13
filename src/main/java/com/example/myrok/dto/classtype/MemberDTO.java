package com.example.myrok.dto.classtype;

import com.example.myrok.domain.MemberProject;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

public class MemberDTO {
    @Data
    @NoArgsConstructor
    @Builder
    @AllArgsConstructor
    @ToString
    public static class MemberNameDto{
        private Long memberId;
        private String name;
    }

    @Data
    @NoArgsConstructor
    @Builder
    @AllArgsConstructor
    @ToString
    public static class MemberProjectDto{
        private Long memberId;
        private String name;
        private List<MemberProject> memberProjects  = new ArrayList<>();
    }
}
