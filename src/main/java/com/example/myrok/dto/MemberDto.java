package com.example.myrok.dto;

import com.example.myrok.domain.Member;
import com.example.myrok.domain.MemberProject;
import com.example.myrok.type.Role;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
public class MemberDto{

    @Data
    @NoArgsConstructor
    @Builder
    @AllArgsConstructor
    @ToString
    public static class MemberNameDto{
        private Long memberId;
        private String name;
    }
}
