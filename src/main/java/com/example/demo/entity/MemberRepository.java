package com.example.demo.entity;

import com.example.demo.dto.MemberRequestDto;
import com.example.demo.dto.MemberResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member_table, Long>{

    Optional<MemberResponseDto> findByMemberLoginid(String memberLoginid);

    boolean existsByMemberLoginid(String Loginid);

}
