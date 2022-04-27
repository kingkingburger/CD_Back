package com.example.demo.controller.Member;

import com.example.demo.dto.MemberFormDto;
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
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/add")
    public String addForm(@ModelAttribute("memberFormDto") MemberFormDto form) {

        return "addMemberForm";

    }

    @PostMapping("/add")
    public String save(@Valid @ModelAttribute MemberFormDto form,
                       BindingResult bindingResult
    ) {

        log.info("memberForm: {}", form);

        if(bindingResult.hasErrors()) {
            return "addMemberForm";
        }

        memberService.save(form);

        return "redirect:/";
    }

}
