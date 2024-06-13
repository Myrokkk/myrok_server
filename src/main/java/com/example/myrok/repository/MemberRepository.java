package com.example.myrok.repository;

import com.example.myrok.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query("SELECT m.name FROM Member m WHERE m.id =:id")
    String findNameById(@Param("id")Long id);

    Member findUserByEmail(String email);
}
