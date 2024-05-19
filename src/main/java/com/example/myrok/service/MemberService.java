package com.example.myrok.service;

import com.example.myrok.domain.Member;
import com.example.myrok.dto.JoinRequest;
import com.example.myrok.dto.LoginRequest;
import jakarta.transaction.Transactional;

@Transactional
public interface MemberService {
    //멤버가 보유한 프로젝트가 있는지 확가
    void checkMemberHaveProject(Long memberId);

    //member가 프로젝트에 참여
    Long participateProject(Long memberId, String inviteCode);

    Long getOutFromProject(Long memberId, Long projectId);

    public boolean checkLoginIdDuplicate(String loginId);

    public void join(JoinRequest joinRequest);

    public void securityJoin(JoinRequest joinRequest);

    public Member login(LoginRequest loginRequest);

    public Member getLoginMemberById(Long memberId);

    public Member getLoginMemberByLoginId(String loginId);
}
