package com.example.demo.repository;

import com.example.demo.dto.MemberRequestDto;
import com.example.demo.dto.MemberResponseDto;
import com.example.demo.entity.Member_table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member_table, Long>{


    Optional<Member_table> findByMemberLoginid(String memberLoginid);

    boolean existsByMemberLoginid(String Loginid);

}
