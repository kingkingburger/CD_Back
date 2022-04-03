package com.example.demo.entity;

import com.example.demo.dto.MemberRequestDto;
import com.example.demo.dto.MemberResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member_table, Long>{

    MemberResponseDto findByEmailAndPasswd(final String email, final String passwd);

}
