package com.example.myrok.repository;

import com.example.myrok.domain.Member;
import com.example.myrok.domain.Tag;
import com.example.myrok.service.openAi.ClovaSummary;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findAllByIdIn(List<Long> memberList);

    @EntityGraph(attributePaths = {"memberRoleList"})
    @Query("select m from Member m where m.id = :id")
    Member getWithRoles(@Param("id") Long id);

    boolean existsByLoginId(String loginId);

    Member findByLoginId(String loginId);

    // 소셜 로그인으로 반환되는 값 중 email을 통해 이미 생성된 사용자인지 처음 가입되는 사용자인지 판단하기 위한 메서드
    Optional<User> findByEmail(String email);

}
