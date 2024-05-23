package com.example.myrok.service;

import com.example.myrok.domain.Member;
import com.example.myrok.domain.MemberProject;
import com.example.myrok.domain.Project;
import com.example.myrok.dto.*;
import com.example.myrok.exception.CustomException;
import com.example.myrok.repository.MemberProjectRepository;
import com.example.myrok.repository.MemberRepository;
import com.example.myrok.repository.ProjectRepository;
import com.example.myrok.type.ErrorCode;
import com.example.myrok.type.MemberProjectType;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Log4j2
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final ProjectRepository projectRepository;
    private final MemberProjectRepository memberProjectRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member user = memberRepository.findUserByEmail(username);
        return new CustomOauth2UserDetails(user);
    }


    public boolean checkLoginIdDuplicate(String loginId){
        return memberRepository.existsByLoginId(loginId);
    }


    public void join(JoinRequest joinRequest) {
        memberRepository.save(joinRequest.toEntity());
    }

    public void securityJoin(JoinRequest joinRequest){
        if(memberRepository.existsByLoginId(joinRequest.getLoginId())){
            return;
        }

        joinRequest.setPassword(bCryptPasswordEncoder.encode(joinRequest.getPassword()));

        memberRepository.save(joinRequest.toEntity());
    }

    public Member login(LoginRequest loginRequest) {
        Member findMember = memberRepository.findByLoginId(loginRequest.getLoginId());

        if(findMember == null){
            return null;
        }

        if (!bCryptPasswordEncoder.matches(loginRequest.getPassword(), findMember.getPassword())) {
            return null;
        }

        return findMember;
    }

    public Member getLoginMemberById(Long memberId){
        if(memberId == null) return null;

        Optional<Member> findMember = memberRepository.findById(memberId);
        return findMember.orElse(null);

    }

    public Member getLoginMemberByLoginId(String loginId){
        if(loginId == null) return null;

        return memberRepository.findByLoginId(loginId);

    }

    public Member getMemberInformation(String email) {
        return memberRepository.findUserByEmail(email);
    }

    public MemberProjectResponse getParticipatedMemberProject(String email) {
        final Member member = memberRepository.findUserByEmail(email);

        final List<MemberProject> allByMemberId = memberProjectRepository.findAllByMember(member);

        return MemberProjectResponse.of((MemberProject) allByMemberId);
    }

}

