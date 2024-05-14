package com.example.myrok.domain;

import com.example.myrok.type.MemberProjectType;
import com.example.myrok.type.Role;
import jakarta.persistence.*;
import jdk.jfr.Description;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Table(name = "tb_member")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "m_id")
    private Long id;

    private String username;

    @Description("소셜로그인 인증 후 받는 로그인정보")
    @Column(name = "social_id")
    private String socialId;

    @Description("랜덤 패스워드 부야")
    private String password;

    @Description("탈퇴한 회원, true의 경우 탈퇴한 회원")
    @Column(name = "is_deleted")
    @Builder.Default
    private Boolean deleted = false;

    @Column(name = "img_url")
    @Description("이미지 url")
    private String imgUrl;

    @OneToMany(mappedBy = "member")
    private List<MemberProject> memberProjects;

    @Override //권한 반환
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(("user")));
    }

//    @Override
//    public String getUsername() {
//        return getName();
//    }


    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }


//    public List<MemberProject> getMemberProjects() {
//        return getMemberProjects().stream()
//                .map(MemberProject::getMemberProject)
//                .toList();
//    }

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        Set<GrantedAuthority> roles = new HashSet<>();
//        for (String role : memberProjects.split(",")) {
//            roles.add(new SimpleGrantedAuthority(role));
//        }
//        return roles;
//    }

}
