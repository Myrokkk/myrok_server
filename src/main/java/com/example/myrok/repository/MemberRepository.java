package com.example.myrok.repository;

import com.example.myrok.domain.Member;
import com.example.myrok.domain.Tag;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findAllByIdIn(List<Long> memberList);

    @EntityGraph(attributePaths = {"memberRoleList"})
    @Query("select m from Member m where m.id = :id")
    Member getWithRoles(@Param("id") String id);


    Member findByName(String username);
}
