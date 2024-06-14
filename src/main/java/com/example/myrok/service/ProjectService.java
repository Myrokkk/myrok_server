package com.example.myrok.service;

import com.example.myrok.domain.Project;
import com.example.myrok.dto.classtype.ProjectDTO;
import jakarta.transaction.Transactional;

import java.time.LocalDate;

@Transactional
public interface ProjectService {
    //프로젝트 등록
    Long register(ProjectDTO.RegisterProject requestDto, Long memberId);

    Long checkProjectDelete(Long projectId);

    ProjectDTO.ProjectMembersDto getProjectMembers(Long projectId);
    ProjectDTO.ParticipateProject getInviteCode(Long projectId);

    default Project dtoToEntity(ProjectDTO.RegisterProject projectDto){
        return Project.builder()
                .projectName(projectDto.getProjectName())
                .startDate(LocalDate.parse(projectDto.getStartDate()))
                .endDate(LocalDate.parse(projectDto.getEndDate()))
                .build();
    }

    default ProjectDTO.RegisterProject entityToDto(Project project){
        return ProjectDTO.RegisterProject.builder()
                .projectName(project.getProjectName())
                .startDate(String.valueOf(project.getStartDate()))
                .endDate(String.valueOf(project.getEndDate()))
                .build();
    }
}
