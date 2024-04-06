package com.example.myrok.domain;

import com.example.myrok.type.MemberProjectType;
import com.example.myrok.type.Role;
import jakarta.persistence.*;
import jdk.jfr.Description;
import lombok.*;

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

    @Description("멤버별 참조하는 프로젝트(팀) 참조")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "p_id")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "record_id")
    private Record record;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private MemberProjectType memberProjectType = MemberProjectType.NON_PROJECT_MEMBER;

    public void changeProject(Project project){
        this.project = project;
    }
    public void changeMemberProjectType(MemberProjectType memberProjectType){
        this.memberProjectType = memberProjectType;
    }

}
