package SilkLoad.controller.Member;

import SilkLoad.dto.MemberFormDto;
import SilkLoad.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "addMemberForm";
        }
        memberService.save(form);
        return "redirect:/";
    }

}
