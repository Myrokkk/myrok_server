package com.example.myrok.domain;

import com.example.myrok.type.MemberProjectType;
import com.example.myrok.type.Role;
import jakarta.persistence.*;
import jdk.jfr.Description;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Table(name = "tb_member")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
//@ToString(exclude = "memberRoleList")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "m_id")
    private Long id;

    private String name;

    @Description("소셜로그인 인증 후 받는 로그인정보")
    @Column(name = "login_id")
    private String loginId;

    @Description("랜덤 패스워드 부여")
    private String password;

    @Description("탈퇴한 회원, true의 경우 탈퇴한 회원")
    @Column(name = "is_deleted")
    @Builder.Default
    private Boolean deleted = false;

    @Column(name = "img_url")
    @Description("이미지 url")
    private String imgUrl;

    @Builder.Default
    @OneToMany(mappedBy = "member")
    private List<MemberProject> memberProjects  = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    // provider : google이 들어감
    private String provider;

    // providerId : 구굴 로그인 한 유저의 고유 ID가 들어감
    private String providerId;
}
