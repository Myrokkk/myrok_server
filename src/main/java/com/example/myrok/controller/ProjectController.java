package com.example.myrok.controller;

import com.example.myrok.domain.Project;
import com.example.myrok.dto.ProjectDto;
import com.example.myrok.service.MemberService;
import com.example.myrok.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/project")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final MemberService memberService;
    @PostMapping("/")
    public ResponseEntity<Long> createProject(Long memberId, @RequestBody ProjectDto projectDto){
        Long projectId = projectService.register(projectDto);
        Project project = memberService.registerProjectToMember(memberId, projectId);
        return ResponseEntity.ok().body(project.getId());
    }

}