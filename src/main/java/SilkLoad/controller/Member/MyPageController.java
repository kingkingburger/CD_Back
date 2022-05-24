package SilkLoad.controller.Member;

import SilkLoad.SessionConst;
import SilkLoad.dto.MemberFormDto;
import SilkLoad.dto.ProductFormDto;
import SilkLoad.dto.ProductRecordDto;
import SilkLoad.entity.Members;
import SilkLoad.service.CartService;
import SilkLoad.service.MemberService;
import SilkLoad.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/members/myPage")
public class MyPageController {

    private final MemberService memberService;
    private final OrderService orderService;
    private final CartService cartService;

    @GetMapping("/profile")
    public String MyPage(@ModelAttribute("memberFormDto")MemberFormDto memberFormDto) {

        return "myPage/memberProfile";
    }

    @PostMapping("/profile")
    public String MyPageModify(@ModelAttribute("memberFormDto")MemberFormDto memberFormDto, BindingResult bindingResult) {

        Members members = memberService.updatePassword(memberFormDto);
        log.info("members={}",members);
        if ( members == null ) {
            bindingResult.rejectValue("loginId","nonExistence");
        }

        return "myPage/memberProfile";
    }

    @GetMapping("/wishlist")
    public String Wishlist(Model model, HttpServletRequest request) {

        HttpSession session = request.getSession();
        Object memberObject = session.getAttribute(SessionConst.LOGIN_MEMBER);
        Members sessionMember = (Members) memberObject;
        String loginId = sessionMember.getLoginId();

        // cartid 가져오기
        List<ProductRecordDto> sellerProduct = cartService.getSellerProduct(loginId);

        for (ProductRecordDto productRecordDto : sellerProduct) {
            System.out.println("productRecordDto = " + productRecordDto);
        }
        return "myPage/memberWishlist";
    }

    @GetMapping("/orders")
    public String Orders(Model model, HttpServletRequest request) {

        HttpSession session = request.getSession();
        Object memberObject = session.getAttribute(SessionConst.LOGIN_MEMBER);
        Members sessionMember = (Members) memberObject;

        List<ProductRecordDto> byIdProductDtoList = orderService.findByIdProductDtoList(sessionMember.getLoginId());
        //log.info("물품리스트={}",byIdProductDtoList.getClass() );
        model.addAttribute("productRecordDtoList", byIdProductDtoList);

        return "myPage/memberOrders";

    }


}
