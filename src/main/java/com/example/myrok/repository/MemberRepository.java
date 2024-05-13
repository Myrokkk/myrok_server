package com.example.myrok.repository;

import com.example.myrok.domain.Member;
import com.example.myrok.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findAllByIdIn(List<Long> memberList);

    //Member getWithRoles(String username);

    Member findByName(String username);
}
