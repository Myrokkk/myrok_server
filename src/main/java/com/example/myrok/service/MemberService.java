package com.example.myrok.service;

import jakarta.transaction.Transactional;

@Transactional
public interface MemberService {
    //멤버가 보유한 프로젝트가 있는지 확가
    void checkMemberHaveProject(Long memberId);

    //member가 프로젝트에 참여
    Long participateProject(Long memberId, String inviteCode);

    Long getOutFromProject(Long memberId, Long projectId);
}
