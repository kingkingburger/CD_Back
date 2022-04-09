package com.example.demo.controller;

import com.example.demo.dto.MemberRequestDto;
import com.example.demo.dto.MemberResponseDto;
import com.example.demo.dto.UserDataResponseDto;
import com.example.demo.exception.CustomException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.model.MemberService;
import com.example.demo.model.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.naming.Binding;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/test")
    public String test() {
        throw new CustomException(ErrorCode.POSTS_NOT_FOUND);
    }

    @GetMapping("/b")
    public List<MemberResponseDto> findAll() {
        return memberService.findAll();
    }
}
