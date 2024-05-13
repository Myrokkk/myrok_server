package com.example.myrok.dto.classtype;

import lombok.*;

import java.util.List;

public class ProjectDTO {
    @Data
    @NoArgsConstructor
    @Builder
    @AllArgsConstructor
    public static class RegisterProject{
        private String projectName;
        @NonNull
        private String endDate;
        @NonNull
        private String startDate;
    }

    @Data
    @NoArgsConstructor
    @Builder
    @AllArgsConstructor
    public static class ParticipateProject{
        @NonNull
        private String inviteCode;
    }

    @Data
    @NoArgsConstructor
    @Builder
    @AllArgsConstructor
    public static class ProjectMemberDto{
        @NonNull
        private Long projectId;
        @NonNull
        private Long memberId;
    }

    @Data
    @NoArgsConstructor
    @Builder
    @AllArgsConstructor
    @ToString
    public static class ProjectMembersDto{
        private List<MemberDTO.MemberNameDto> projectMemberNames;
    }

}