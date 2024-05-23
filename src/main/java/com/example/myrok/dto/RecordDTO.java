package com.example.myrok.dto;

import com.example.myrok.domain.*;
import com.example.myrok.domain.Record;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import jakarta.validation.constraints.NotBlank;
@Builder
public record RecordDTO (
        @NotBlank(message = "제목은 필수 입력 사항입니다.")
    String recordName,
        @NotBlank(message = "본문은 필수 입력 사항입니다.")
    String recordContent,
        LocalDate recordDate,
    @NotNull
    Long recordWriterId,
        @NotEmpty
    List<Long> memberList,
        List<String> tagList,
        @NotNull
    Long projectId
){

    public Record toEntity(Project project){
        return Record.builder()
                .recordName(recordName)
                .recordContent(recordContent)
                .recordDate(recordDate)
                .recordWriterId(recordWriterId)
                .project(project)
                .build();
    }

}
