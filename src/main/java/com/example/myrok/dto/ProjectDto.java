package com.example.myrok.dto;

import com.example.myrok.domain.Member;
import jakarta.persistence.Column;
import jdk.jfr.Description;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ProjectDto {
    private String projectName;
    private String endDate;
    private String startDate;
    @Builder.Default
    private int limitMember = 6;
}
