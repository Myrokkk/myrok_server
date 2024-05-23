package com.example.myrok.service;

import com.example.myrok.domain.MemberProject;
import com.example.myrok.domain.Member;
import com.example.myrok.repository.MemberProjectRepository;
import com.example.myrok.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

public class MemberServiceTests {

    private MemberService memberService;

    private MemberProjectRepository memberProjectRepository;

    private MemberRepository memberRepository;

    // 테스트에서 사용할 테스트 데이터 생성 메서드들
    private Member createMember(String email, String name) {
        Member member = new Member();
        member.setEmail(email);
        member.setName(name);
        return memberRepository.save(member);
    }

    private MemberProject createMemberProject(String name) {
        MemberProject memberProject = new MemberProject();
        memberProject.Project.setName(name);
        return memberProjectRepository.save(teamPlace);
    }

    // 나머지 테스트 데이터 생성 메서드들 추가

    @Nested
    @DisplayName("사용자가 속한 프로젝트들의 정보 조회시")
    class FindAllParticipatedMemberProjectInfo {


        @Test
        @DisplayName("조회에 성공한다.")
        void success() {
            // given
            final Member KDY = createMember("example@example.com", "KDY");
            final MemberProject PROJECT_1 = createMemberProject("PROJECT_1");
            final MemberProject PROJECT_2 = createMemberProject("PROJECT_2");
            final MemberProject PROJECT_3 = createMemberProject("PROJECT_3");

            // ENDEL이 세 개의 팀 장소에 속하도록 설정
            MemberProject memberProject1 = new MemberProject();
            memberProject1.setMember(KDY);
            memberProject1.setTeamPlace(PROJECT_1);
            memberProjectRepository.save(memberProject1);

            MemberProject memberProject2 = new MemberProject();
            memberProject2.setMember(KDY);
            memberProject2.setTeamPlace(PROJECT_2);
            memberProjectRepository.save(memberProject2);

            MemberProject memberProject3 = new MemberProject();
            memberProject3.setMember(KDY);
            memberProject3.setTeamPlace(PROJECT_3);
            memberProjectRepository.save(memberProject3);

            // when
            final TeamPlacesResponse response = memberService.getParticipatedTeamPlaces(new MemberEmailDto(ENDEL.getEmail().getValue()));

            //then
            final List<TeamPlaceResponse> teamPlaceResponses = response.teamPlaces();
            assertSoftly(softly -> {
                softly.assertThat(teamPlaceResponses).hasSize(3);
                softly.assertThat(teamPlaceResponses.get(0).id()).isEqualTo(ENGLISH_TEAM_PLACE.getId());
                softly.assertThat(teamPlaceResponses.get(0).displayName()).isEqualTo(ENGLISH_TEAM_PLACE.getName().getValue());
                softly.assertThat(teamPlaceResponses.get(1).id()).isEqualTo(JAPANESE_TEAM_PLACE.getId());
                softly.assertThat(teamPlaceResponses.get(1).displayName()).isEqualTo(JAPANESE_TEAM_PLACE.getName().getValue());
                softly.assertThat(teamPlaceResponses.get(2).id()).isEqualTo(STATICS_TEAM_PLACE.getId());
                softly.assertThat(teamPlaceResponses.get(2).displayName()).isEqualTo(STATICS_TEAM_PLACE.getName().getValue());
            });
        }
    }
}
