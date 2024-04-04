package com.example.myrok;

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
        ProjectDto projectDto = ProjectDto.builder()
                .projectName("test name 1")
                .startDate("2024-01-01")
                .endDate("2025-01-01")
                .build();
        Long projectId = projectService.register(projectDto);
        Project project = memberService.registerProjectToMember(2L, projectId);
        log.info(project.getId());
    }

    @Test
    public void testParticipate(){
        memberService.participateProject(2L, "e6d75f72-0d56-4834-b5a0-ba72e90b226a");
    }
}
