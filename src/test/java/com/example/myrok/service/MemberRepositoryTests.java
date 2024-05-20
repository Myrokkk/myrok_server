//package com.example.myrok.service;
//
//import com.example.myrok.domain.Member;
//import com.example.myrok.domain.MemberRole;
//import com.example.myrok.repository.MemberRepository;
//import lombok.extern.log4j.Log4j2;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//
//@SpringBootTest
//@Log4j2
//public class MemberRepositoryTests {
//
//    @Autowired
//    private MemberRepository memberRepository;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Test
//    public void testInsertMember(){
//
//        for (int i = 0; i < 3 ; i++) {
//
//            Member member = Member.builder()
//                    .id((long) i)
//                    .password(passwordEncoder.encode("1111"))
//                    .build();
//
//            member.addRole(MemberRole.USER);
//
//            if(i >= 1){
//                member.addRole(MemberRole.MANAGER);
//            }
//
//            if(i >= 2){
//                member.addRole(MemberRole.ADMIN);
//            }
//            memberRepository.save(member);
//        }
//    }
//
//    @Test
//    public void testRead() {
//
//        int x = 1;
//        Long id = x;
//
//        Member member = memberRepository.getWithRoles(id);
//
//        log.info("-----------------");
//        log.info(member);
//    }
//
//}