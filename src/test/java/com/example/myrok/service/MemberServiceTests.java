package com.example.myrok.service;

import com.example.myrok.domain.MemberProject;
import com.example.myrok.domain.Member;
import com.example.myrok.domain.Project;
import com.example.myrok.dto.MemberProjectResponse;
import com.example.myrok.repository.MemberProjectRepository;
import com.example.myrok.repository.MemberRepository;
import com.example.myrok.repository.ProjectRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.example.myrok.domain.QMemberProject.memberProject;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

public class MemberServiceTests {

    private MemberService memberService;

    private MemberProjectRepository memberProjectRepository;
    private ProjectRepository projectRepository;

    private MemberRepository memberRepository;

    // 테스트에서 사용할 테스트 데이터 생성 메서드들
    private Member createMember(String email, String name) {
        Member member = new Member();
        member.setEmail(email);
        member.setName(name);
        return memberRepository.save(member);
    }

    private Project createProject(String name) {
        Project project = new Project();
        project.setProjectName(name);
        return projectRepository.save(project);
    }


    @Nested
    @DisplayName("사용자가 속한 프로젝트들의 정보 조회시")
    class FindAllParticipatedMemberProjectInfo {


        @Test
        @DisplayName("조회에 성공한다.")
        void success() {
            // given
            final Member KDY = createMember("example@example.com", "KDY");
            final Project PROJECT_1 = createProject("PROJECT_1");
            final Project PROJECT_2 = createProject("PROJECT_2");
            final Project PROJECT_3 = createProject("PROJECT_3");

            // 세 개의 팀 장소에 속하도록 설정
            MemberProject memberProject1 = new MemberProject();
            memberProject1.setMember(KDY);
            memberProject1.setProject(PROJECT_1);
            memberProjectRepository.save(memberProject1);

            MemberProject memberProject2 = new MemberProject();
            memberProject2.setMember(KDY);
            memberProject2.setProject(PROJECT_2);
            memberProjectRepository.save(memberProject2);

            MemberProject memberProject3 = new MemberProject();
            memberProject3.setMember(KDY);
            memberProject3.setProject(PROJECT_3);
            memberProjectRepository.save(memberProject3);

            // when
            final MemberProjectResponse response = memberService.getParticipatedMemberProject(KDY.getEmail());

            //then
            final List<MemberProjectResponse> memberProjectResponses = response.memberProject();

            assertSoftly(softly -> {
                softly.assertThat(memberProjectResponses).hasSize(3);
                softly.assertThat(memberProjectResponses.get(0)).isEqualTo(memberProject1.getId());
                softly.assertThat(memberProjectResponses.get(Integer.parseInt("PROJECT_1"))).isEqualTo(memberProject1.getProjectName());
                softly.assertThat(memberProjectResponses.get(1)).isEqualTo(memberProject2.getId());
                softly.assertThat(memberProjectResponses.get(Integer.parseInt("PROJECT_2"))).isEqualTo(memberProject2.getProjectName());
                softly.assertThat(memberProjectResponses.get(2)).isEqualTo(memberProject3.getId());
                softly.assertThat(memberProjectResponses.get(Integer.parseInt("PROJECT_3"))).isEqualTo(memberProject3.getProjectName());
            });

        }

        public record MemberProjectsResponse(List<MemberProjectResponse> Projects) {
            public static MemberProjectsResponse of(List<MemberProject> allByMemberId) {
                return new MemberProjectsResponse(allByMemberId.stream()
                        .map(MemberProjectResponse::of)
                        .toList());
            }
        }
    }
}
