package com.example.myrok.security;

import com.example.myrok.domain.Member;
import com.example.myrok.dto.MemberDto;
import com.example.myrok.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Log4j2
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("----------------loadUserByUsername----------------");

//        Member member = memberRepository.getWithRoles(username);
//
//        if(member == null){
//            throw new UsernameNotFoundException("Not Found");
//        }

//        MemberDto memberDto = new MemberDto(
//                member.getId(),
//                member.getName(),
//                member.getSocialId(),
//                member.getPassword(),
//                member.getDeleted(),
//                member.getImgUrl(),
//                member.getMemberProjects()
////                member.getMemberProjects()
////                        .stream()
////                        .map(memberProjects -> memberProjects.id()).collect(Collectors.toList())
//                       );
//
//        log.info(memberDto);
//
//        return memberDto;
        return null;
    }
}