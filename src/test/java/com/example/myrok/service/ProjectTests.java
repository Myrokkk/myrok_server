package com.example.myrok.service;

import com.example.myrok.domain.Project;
import com.example.myrok.dto.ProjectDto;
import com.example.myrok.repository.ProjectRepository;
import com.example.myrok.service.MemberService;
import com.example.myrok.service.MemberServiceImpl;
import com.example.myrok.service.ProjectService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class ProjectTests {
    @Autowired
    ProjectService projectService;
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    MemberService memberService;

    @Test
    public void testRegister(){
        ProjectDto.RegisterProject projectDto = ProjectDto.RegisterProject.builder()
                .projectName("test name 1")
                .startDate("")
                .endDate("")
                .build();
        Long projectId = projectService.register(projectDto, 1L);
    }

    @Test
    public void testGetMembersByProjectId(){
        ProjectDto.ProjectMembersDto projectMembersDto = projectService.getProjectMembers(4L);
        log.info(projectMembersDto);
    }
}
