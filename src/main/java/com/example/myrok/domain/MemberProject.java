package com.example.myrok.domain;

import com.example.myrok.type.MemberProjectType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_member_project")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberProject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mp_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "m_id")
    private Member member;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "p_id")
    private Project project;
    @Getter
    @Column(name = "member_project_type")
    @Enumerated(EnumType.STRING)
    private MemberProjectType memberProjectType;

    public void changeProject(Project project) {
        this.project = project;
    }

    public void changeMemberProjectType(MemberProjectType memberProjectType) {
        this.memberProjectType = memberProjectType;
    }


    public String getProjectName() {
        return project.getProjectName();
    }
}

//    public MemberProject getMemberProject() {
//
//        return getmemberProject();
//    }

