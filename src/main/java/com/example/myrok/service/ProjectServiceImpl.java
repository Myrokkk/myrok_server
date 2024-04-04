package com.example.myrok.service;

import com.example.myrok.domain.Member;
import com.example.myrok.domain.Project;
import com.example.myrok.dto.ProjectDto;
import com.example.myrok.repository.MemberRepository;
import com.example.myrok.repository.ProjectRepository;
import com.example.myrok.type.MemberProjectType;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import java.util.NoSuchElementException;

@RequiredArgsConstructor // property에 대한 의존성 주입
@Service
@Log4j2
public class ProjectServiceImpl implements ProjectService{

    private final ProjectRepository projectRepository;

    @Override
    public Long register(ProjectDto projectDto) {
        Project project = dtoToEntity(projectDto);
        return projectRepository.save(project).getId();
    }

    @Override
    //멤버가 프로젝트 나가기를 할 때마다, 해당 프로젝트 내 소속된 인원이 없는지 확인 필요
    //소속된 멤버의 상태가 모두 NON_PROJECT_MEMBER일 경우
    public Long checkProjectDelete(Project project) {
        boolean canDelete = true;
        //한명이라도 소속 멤버인 경우 delete하지 않는다
        for(int i = 0; i<project.getMemberList().size(); i++){
            if(project.getMemberList().get(i).getMemberProjectType() == MemberProjectType.PROJECT_MEMBER){
                canDelete = false;
            }
        }
        if(canDelete){
            project.changeDeleted();
        }
        projectRepository.save(project);
        return project.getId();
    }
}
