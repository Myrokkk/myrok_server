package com.example.myrok.service;

import com.example.myrok.domain.Member;
import com.example.myrok.domain.MemberProject;
import com.example.myrok.domain.Project;
import com.example.myrok.exception.CustomException;
import com.example.myrok.repository.MemberProjectRepository;
import com.example.myrok.repository.MemberRepository;
import com.example.myrok.repository.ProjectRepository;
import com.example.myrok.type.ErrorCode;
import com.example.myrok.type.MemberProjectType;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Log4j2
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final ProjectRepository projectRepository;
    private final MemberProjectRepository memberProjectRepository;

    @Override
    public void checkMemberHaveProject(Long memberId){
        Member member = memberRepository.findById(memberId).orElseThrow(NoSuchElementException::new);
        Optional<MemberProject> memberProject = memberProjectRepository.findByMemberAndMemberProjectType(member, MemberProjectType.PROJECT_MEMBER);
        if(memberProject.isPresent())
            throw new CustomException(ErrorCode.MEMBER_IN_PROJECT, HttpStatus.NOT_ACCEPTABLE);
    }


    @Override
    public Long participateProject(Long memberId, String inviteCode) {
        Optional<Project> project = projectRepository.findByInviteCode(inviteCode);
        if(project.isEmpty()){
            throw new CustomException(ErrorCode.WRONG_INVITE_CODE, HttpStatus.BAD_REQUEST);
        }
        MemberProject memberProject = MemberProject.builder()
                .member(memberRepository.findById(memberId).orElseThrow(NoSuchElementException::new))
                .project(project.get())
                .memberProjectType(MemberProjectType.PROJECT_MEMBER)
                .build();
        return memberProjectRepository.save(memberProject).getId();
    }

    @Override
    public Long getOutFromProject(Long memberId, Long projectId) {
        MemberProject memberProject = memberProjectRepository.findByMemberIdAndProjectIdAndMemberProjectType(memberId, projectId, MemberProjectType.PROJECT_MEMBER).orElseThrow(new CustomException(ErrorCode.MEMBER_NOT_ACCEPTABLE, HttpStatus.NOT_ACCEPTABLE));
        memberProject.changeMemberProjectType(MemberProjectType.NON_PROJECT_MEMBER);
        return memberProjectRepository.save(memberProject).getId();
    }
}
