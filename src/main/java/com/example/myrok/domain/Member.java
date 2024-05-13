package com.example.myrok.domain;

import com.example.myrok.type.MemberProjectType;
import com.example.myrok.type.Role;
import jakarta.persistence.*;
import jdk.jfr.Description;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;

@Entity
@Table(name = "tb_member")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "m_id")
    private Long id;

    private String name;

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

    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private List<Role> memberRoleList = new ArrayList<>();

    public void addRole(Role memberRole){

        memberRoleList.add(memberRole);
    }

    public void clearRole() {
        memberRoleList.clear();
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
