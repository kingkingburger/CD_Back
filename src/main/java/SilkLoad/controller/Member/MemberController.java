package SilkLoad.controller.Member;

import SilkLoad.dto.MemberFormDto;
import SilkLoad.service.MemberService;
import com.sun.tools.jconsole.JConsoleContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.sql.SQLIntegrityConstraintViolationException;

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

        try{
            memberService.save(form);
        }catch (DataIntegrityViolationException e){
            bindingResult.reject("memberAddFail", "이미 존재하는 아이디입니다.");
            return "addMemberForm";
        }

        return "redirect:/";
    }

}
