package com.example.myrok.controller;

import com.example.myrok.domain.Member;
import com.example.myrok.service.MemberService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

//test 추가
@ExtendWith(MockitoExtension.class)
public class MemberControllerTests {

    @Mock
    private MemberService memberService;

    @InjectMocks
    private MemberController memberController;

    @Test
    public void testGetMyInformation() {
        // 가짜 이메일
        String email = "example@example.com";

        // 가짜 Member 객체 생성
        Member fakeMember = new Member();
        fakeMember.setEmail(email);
        fakeMember.setName("Hong GilDong"); // 예시로 이름 설정
        fakeMember.setId(Long.valueOf("1"));
        fakeMember.setPicture("https://lh3.googleusercontent.com/…");

        // MemberService의 가짜 응답 설정
        when(memberService.getMemberInformation(email)).thenReturn(fakeMember);

        // 컨트롤러 호출
        ResponseEntity<Member> responseEntity = memberController.getMyInformation(email);

        // 응답 검증
        assertEquals(200, responseEntity.getStatusCodeValue()); // HTTP 상태 코드 확인
        assertEquals(fakeMember, responseEntity.getBody()); // 멤버 객체 확인
    }
}
