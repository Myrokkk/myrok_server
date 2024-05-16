package com.example.myrok.controller;

import com.example.myrok.domain.Project;
import com.example.myrok.dto.ClovaDto;
import com.example.myrok.dto.ProjectDto;
import com.example.myrok.dto.error.ErrorResponse;
import com.example.myrok.exception.CustomException;
import com.example.myrok.service.MemberService;
import com.example.myrok.service.ProjectService;
import com.example.myrok.service.openAi.ClovaSummary;
import com.example.myrok.type.ErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Project", description = "Project 관련 API 입니다.")
@RequestMapping("/myrok/project")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final MemberService memberService;
    private final ClovaSummary clovaSummary;

    @Operation(
            summary = "프로젝트 생성",
            description = "프로젝트를 생성합니다"
    )
    @ApiResponse(
            responseCode = "200",
            description = "프로젝트 생성을 완료하였습니다."
    )
    @PostMapping("/")
    public ResponseEntity<Long> createProject(@RequestBody ProjectDto.RegisterProject projectDto, Long memberId){
        memberService.checkMemberHaveProject(memberId);
        return ResponseEntity.ok().body(projectService.register(projectDto , memberId));
    }

    @Operation(
            summary = "프로젝트 초대코드를 가져옵니다.",
            description = "프로젝트 초대코드를 가져옵니다."
    )
    @ApiResponse(
            responseCode = "200",
            description = "프로젝트 초대코드를 가져옵니다."
    )
    @GetMapping("/{projectId}")
    public ResponseEntity<String> getInviteCode(@PathVariable Long projectId) {
        return ResponseEntity.ok().body(projectService.getInviteCode(projectId));
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
    public ResponseEntity<Long> participateProject(Long memberId, @RequestBody ProjectDto.ParticipateProject projectDto){
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
    @PreAuthorize("isAuthenticated")
    @GetMapping("/")
    public OAuth2User home(@AuthenticationPrincipal OAuth2User user) {
        return user;
    }

    //todo 회의록 Controller에 이동 필요
    @Operation(
            summary = "회의록을 요약합니다.",
            description = "회의록을 요약합니다."
    )
    @ApiResponse(
            responseCode = "200",
            description = "회의록이 요약 되었습니다."
    )
    @PostMapping("/summary")
    public ResponseEntity<ClovaDto.ResponseDto> getOutProject(@RequestBody ClovaDto.RequestDto requestDto) {
        return ResponseEntity.ok().body(clovaSummary.get(requestDto));
    }

    @Operation(
            summary = "프로젝트 멤버를 가져옵니다",
            description = "프로젝트 멤버를 가져옵니다"
    )
    @ApiResponse(
            responseCode = "200",
            description = "프로젝트 멤버를 가져왔습니다."
    )
    @PostMapping("/{projectId}/members")
    public ResponseEntity<ProjectDto.ProjectMembersDto> getProjectMembers(@PathVariable Long projectId) {
        return ResponseEntity.ok().body(projectService.getProjectMembers(projectId));
    }
}
