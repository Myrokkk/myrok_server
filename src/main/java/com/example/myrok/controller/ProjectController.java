package com.example.myrok.controller;

import com.example.myrok.domain.Project;
import com.example.myrok.dto.ProjectDto;
import com.example.myrok.dto.error.ErrorResponse;
import com.example.myrok.exception.CustomException;
import com.example.myrok.service.MemberService;
import com.example.myrok.service.ProjectService;
import com.example.myrok.type.ErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Project", description = "Project 관련 API 입니다.")
@RequestMapping("myrok/project")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final MemberService memberService;

    @Operation(
            summary = "프로젝트 생성",
            description = "프로젝트를 생성합니다"
    )
    @ApiResponse(
            responseCode = "200",
            description = "프로젝트 생성을 완료하였습니다."
    )
    @PostMapping("/")
    public ResponseEntity<Object> createProject(Long memberId, @RequestBody ProjectDto projectDto){
        memberService.checkMemberHaveProject(memberId);
        Long projectId = projectService.register(projectDto);
        Project project = memberService.registerProjectToMember(memberId, projectId);
        return ResponseEntity.ok().body(project.getId());
    }

    @PostMapping("/participate")
    public ResponseEntity<Long> participateProject(Long memberId, String inviteCode){
        memberService.checkMemberHaveProject(memberId);
        return ResponseEntity.ok().body(memberService.participateProject(memberId, inviteCode).getId());
    }
    @DeleteMapping("/")
    public ResponseEntity<Long> getOutProject(Long memberId, Long projectId){
        Project project = memberService.getOutFromProject(memberId,projectId);
        projectService.checkProjectDelete(project);
        return ResponseEntity.ok().body(project.getId());
    }
}
