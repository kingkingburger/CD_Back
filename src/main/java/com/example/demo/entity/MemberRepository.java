package com.example.demo.entity;

import com.example.demo.dto.MemberRequestDto;
import com.example.demo.dto.MemberResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member_table, Long>{

    Optional<MemberResponseDto> findByMemberLoginid(String memberLoginid);

}
