package com.example.myrok.controller;

import com.example.myrok.dto.ClovaDto;
import com.example.myrok.dto.ProjectDto;
import com.example.myrok.service.MemberService;
import com.example.myrok.service.ProjectService;
import com.example.myrok.service.openapi.RecordSummaryServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<Long> createProject(@RequestBody ProjectDto.RegisterProject projectDto, Long memberId) {
        memberService.checkMemberHaveProject(memberId);
        Long projectId = projectService.register(projectDto);
        return ResponseEntity.ok().body(memberService.registerProjectToMember(memberId, projectId));
    }

    @Operation(
            summary = "프로젝트 참가",
            description = "프로젝트에 참가합니다"
    )
    @ApiResponse(
            responseCode = "200",
            description = "프로젝트 참가를 완료하였습니다."
    )
    @PostMapping("/participate")
    public ResponseEntity<Long> participateProject(Long memberId, @RequestBody ProjectDto.ParticipateProject projectDto) {
        memberService.checkMemberHaveProject(memberId);
        return ResponseEntity.ok().body(memberService.participateProject(memberId, projectDto.getInviteCode()));
    }

    @Operation(
            summary = "프로젝트 나가기",
            description = "프로젝트에서 나갑니다."
    )
    @ApiResponse(
            responseCode = "200",
            description = "프로젝트에서 나갔습니다."
    )
    @DeleteMapping("/")
    public ResponseEntity<Long> getOutProject(@RequestBody ProjectDto.ProjectMemberDto dto) {
        memberService.getOutFromProject(dto.getMemberId(), dto.getProjectId());
        Long id = projectService.checkProjectDelete(dto.getProjectId());
        return ResponseEntity.ok().body(id);
    }
}
