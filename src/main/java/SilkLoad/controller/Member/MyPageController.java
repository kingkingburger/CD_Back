package SilkLoad.controller.Member;

import SilkLoad.SessionConst;
import SilkLoad.dto.MemberFormDto;
import SilkLoad.dto.ProductRecordDto;
import SilkLoad.dto.TradeOrderDto;
import SilkLoad.entity.Members;
import SilkLoad.entity.OrderEnum.OrderType;
import SilkLoad.entity.ProductEnum.ProductTime;
import SilkLoad.entity.ProductEnum.ProductType;
import SilkLoad.service.CartService;
import SilkLoad.service.MemberService;
import SilkLoad.service.OrderService;
import SilkLoad.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/members/myPage")
public class MyPageController {

    private final MemberService memberService;
    private final OrderService orderService;
    private final ProductService productService;
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
    public String Wishlist(@PageableDefault(size=6)Pageable pageable, Model model, HttpServletRequest request) {

        HttpSession session = request.getSession();
        Object memberObject = session.getAttribute(SessionConst.LOGIN_MEMBER);
        Members sessionMember = (Members) memberObject;
        String loginId = sessionMember.getLoginId();

        // cartid 가져오기
        Page<ProductRecordDto> sellerProduct = cartService.getSellerProduct(loginId, pageable);

        model.addAttribute("Products", sellerProduct);
        //페이징화 된 객체
        List<ProductRecordDto> content = sellerProduct.getContent();

        //전체 페이지 수
        int totalPages = sellerProduct.getTotalPages();

        //현제 페이지
        int presentPage = sellerProduct.getNumber();

        //페이징된 물품들 모델로 보내기
        model.addAttribute("allProduct", content);

        //전체 페이지 수 모델로 보내기
        model.addAttribute("totalPages",totalPages);

        //현제 페이지  모델로 보내기
        model.addAttribute("presentPage",presentPage);

        model.addAttribute("sale", ProductType.sale);
        return "myPage/memberWishlist";
    }

    @GetMapping("/saleOrders")
    public String saleOrders(@PageableDefault(size=6)Pageable pageable, Model model, HttpServletRequest request) {

        Members sessionMember = getSessionMembers(request);

        List<TradeOrderDto> saleOrders =  orderService.findMemberSaleOrder(sessionMember.getId(), pageable);

        calculationDeadLine(saleOrders);

        model.addAttribute("saleOrders", saleOrders);
        model.addAttribute("orderType", OrderType.values() );


        return "/myPage/memberSaleOrders";
    }

    @GetMapping("/purchaseOrders")
    public String purchaseOrders(@PageableDefault(size=6)Pageable pageable, Model model, HttpServletRequest request) {

        Members sessionMembers = getSessionMembers(request);
        List<TradeOrderDto> purchaseOrders = orderService.findMemberPurchaseOrder(sessionMembers.getId(), pageable);
        calculationDeadLine(purchaseOrders);

        log.info("purchaseOrders => {}", purchaseOrders);

        model.addAttribute("purchaseOrders", purchaseOrders);
        model.addAttribute("orderType", OrderType.values() );

        return "/myPage/memberPurchaseOrders";

    }

    private void calculationDeadLine(List<TradeOrderDto> tradeOrders) {
        tradeOrders.forEach(tradeOrder -> {

            if (tradeOrder.getProductTime() != ProductTime.NONE) {
                LocalDateTime deadLine = productService.productDeadLine(tradeOrder.getProductDateTime(), tradeOrder.getProductTime());
                tradeOrder.setDeadLine( deadLine );
            }

        });
    }

    private Members getSessionMembers(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object memberObject = session.getAttribute(SessionConst.LOGIN_MEMBER);
        Members sessionMember = (Members) memberObject;
        return sessionMember;
    }

}
