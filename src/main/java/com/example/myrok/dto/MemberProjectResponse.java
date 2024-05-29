package com.example.myrok.dto;

import com.example.myrok.domain.MemberProject;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public record MemberProjectResponse(
        Long Id,
        String projectName,
        LocalDate startDate,
        LocalDate endDate
) {
    public static MemberProjectResponse of(MemberProject memberProject) {
        return new MemberProjectResponse(
                memberProject.getProject().getId(),
                memberProject.getProject().getProjectName(),
                memberProject.getProject().getStartDate(),
                memberProject.getProject().getEndDate()
        );
    }

    public List<MemberProjectResponse> memberProject() {
        return memberProject();

    }



}
