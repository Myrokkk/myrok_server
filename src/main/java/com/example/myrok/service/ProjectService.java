package com.example.myrok.service;

import com.example.myrok.domain.Project;
import com.example.myrok.dto.ProjectDto;
import jakarta.transaction.Transactional;

import java.time.LocalDate;

@Transactional
public interface ProjectService {
    Long register(ProjectDto requestDto);

    Long checkProjectDelete(Project project);


    default Project dtoToEntity(ProjectDto projectDto){
        return Project.builder()
                .projectName(projectDto.getProjectName())
                .startDate(LocalDate.parse(projectDto.getStartDate()))
                .endDate(LocalDate.parse(projectDto.getEndDate()))
                .limitMember(projectDto.getLimitMember())
                .build();
    }

    default ProjectDto entityToDto(Project project){
        return ProjectDto.builder()
                .projectName(project.getProjectName())
                .startDate(String.valueOf(project.getStartDate()))
                .endDate(String.valueOf(project.getEndDate()))
                .limitMember(project.getLimitMember())
                .build();
    }
}
