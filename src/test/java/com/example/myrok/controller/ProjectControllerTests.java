package com.example.myrok.controller;

import com.example.myrok.dto.ProjectDto;
import com.example.myrok.service.MemberService;
import com.example.myrok.service.ProjectService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class ProjectControllerTests {
    @Autowired
    MemberService memberService;
    @Autowired
    ProjectService projectService;

    @Test
    public void createProject(){
        memberService.checkMemberHaveProject(1L);
        ProjectDto.RegisterProject registerProject = ProjectDto.RegisterProject.builder()
                .projectName("test_member2")
                .startDate("")
                .endDate("")
                .build();
        Long projectId = projectService.register(registerProject);
        log.info(memberService.registerProjectToMember(1L, projectId));
    }

    @Test
    public void participantProject(){
        memberService.checkMemberHaveProject(2L);
        log.info(memberService.participateProject(2L, "63a392d3"));
    }
}