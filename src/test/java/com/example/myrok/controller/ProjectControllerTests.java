package com.example.myrok.controller;

import com.example.myrok.domain.Project;
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
        memberService.checkMemberHaveProject(2L);
        ProjectDto.RegisterProject registerProject = ProjectDto.RegisterProject.builder()
                .projectName("test_member2")
                .startDate("")
                .endDate("")
                .build();
        Long projectId = projectService.register(registerProject, 2L);
    }

    @Test
    public void participantProject(){
        memberService.checkMemberHaveProject(3L);
        log.info(memberService.participateProject(3L, "0ddd3e25"));
    }

//    @Test
//    public void getOutFromProject() {
//        memberService.getOutFromProject(3L, 3L);
//        projectService.checkProjectDelete(3L);
//    }
}
