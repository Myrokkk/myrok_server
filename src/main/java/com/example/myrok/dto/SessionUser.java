package com.example.myrok.dto;

import com.example.myrok.domain.Member;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    // SessionUser는 인증된 사용자 정보만 필요하므로 아래 필드만 선언한다.
    private String name;
    private String email;
    private String picture;

    public SessionUser(Member member) {
        this.name = member.getName();
        this.email = member.getEmail();
        this.picture = member.getPicture();
    }
}