package com.example.myrok.controller;


import com.example.myrok.domain.Member;
<<<<<<< HEAD
import com.example.myrok.dto.MemberProjectResponse;
=======
>>>>>>> b37d6d3 (feat: 본인 정보 조회)
import com.example.myrok.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
<<<<<<< HEAD
=======
@RequestMapping("/api/me")
>>>>>>> b37d6d3 (feat: 본인 정보 조회)
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

<<<<<<< HEAD
    @GetMapping("/api/me")
=======
    @GetMapping
>>>>>>> b37d6d3 (feat: 본인 정보 조회)
    public ResponseEntity<Member> getMyInformation(@PathVariable String email) {

        return ResponseEntity.ok().body(memberService.getMemberInformation(email));
    }

<<<<<<< HEAD
    @GetMapping("myrok/me/project")
    public ResponseEntity<MemberProjectResponse> getParticipatedMemberProject(@PathVariable String email) {

        return ResponseEntity.ok(memberService.getParticipatedMemberProject(email));
    }
=======

>>>>>>> b37d6d3 (feat: 본인 정보 조회)
}