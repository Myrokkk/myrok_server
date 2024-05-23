package com.example.myrok.service;

import com.example.myrok.domain.Project;
import com.example.myrok.dto.ProjectDto;
import jakarta.transaction.Transactional;

import java.time.LocalDate;

@Transactional
public interface ProjectService {
    //프로젝트 등록
    Long register(ProjectDto.RegisterProject requestDto, Long memberId);

    Long checkProjectDelete(Long projectId);

    String getInviteCode(Long projectId);

    ProjectDto.ProjectMembersDto getProjectMembers(Long projectId);

    default Project dtoToEntity(ProjectDto.RegisterProject projectDto){
        return Project.builder()
                .projectName(projectDto.getProjectName())
                .startDate(LocalDate.parse(projectDto.getStartDate()))
                .endDate(LocalDate.parse(projectDto.getEndDate()))
                .build();
    }

    default ProjectDto.RegisterProject entityToDto(Project project){
        return ProjectDto.RegisterProject.builder()
                .projectName(project.getProjectName())
                .startDate(String.valueOf(project.getStartDate()))
                .endDate(String.valueOf(project.getEndDate()))
                .build();
    }
}
