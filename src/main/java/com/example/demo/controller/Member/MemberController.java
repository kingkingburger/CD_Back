package com.example.demo.controller.Member;

import com.example.demo.dto.MemberRequestDto;
import com.example.demo.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;


    @GetMapping("/sign")
    public String addFrom(@ModelAttribute("member") MemberRequestDto member) {
        return "sign";
    }

    @PostMapping("/sign")
    public String save(@Valid @ModelAttribute("member") MemberRequestDto member, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            bindingResult.reject("아이디가 중복되었습니다.");
            return "sign";
        }

        if(memberService.save(member)){
            return "redirect:/";
        } else{
            bindingResult.reject("addFail","아이디가 중복되었습니다.");
            return "sign";
        }
    }

}
