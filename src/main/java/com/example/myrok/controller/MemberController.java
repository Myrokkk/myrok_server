package com.example.myrok.controller;


import com.example.myrok.domain.Member;
import com.example.myrok.dto.MemberProjectResponse;
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
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/myrok/me/{email}")
    public ResponseEntity<Member> getMyInformation(@PathVariable String email) {

        return ResponseEntity.ok().body(memberService.getMemberInformation(email));
    }

    @GetMapping("/myrok/me/project/{email}")
    public ResponseEntity<Member> getMyProject(@PathVariable String email) {
        return ResponseEntity.ok().body(memberService.getMemberInformation(email));
    }
}