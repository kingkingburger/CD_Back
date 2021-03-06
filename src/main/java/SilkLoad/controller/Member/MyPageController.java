package SilkLoad.controller.Member;

import SilkLoad.SessionConst;
import SilkLoad.dto.*;
import SilkLoad.entity.ChatRoom;
import SilkLoad.entity.ChatRoomEnum.ChatRoomType;
import SilkLoad.entity.Members;
import SilkLoad.entity.OrderEnum.OrderType;
import SilkLoad.entity.ProductEnum.ProductTime;
import SilkLoad.entity.ProductEnum.ProductType;
import SilkLoad.repository.ChatRoomRepository;
import SilkLoad.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
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
    private final ChatService chatService;

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
    public String Wishlist(@PageableDefault(size=6) Pageable pageable, Model model, HttpServletRequest request) {

        HttpSession session = request.getSession();
        Object memberObject = session.getAttribute(SessionConst.LOGIN_MEMBER);
        Members sessionMember = (Members) memberObject;
        String loginId = sessionMember.getLoginId();

        // cartid ????????????
        Page<ProductRecordDto> sellerProduct = cartService.getSellerProduct(loginId, pageable);

        model.addAttribute("Products", sellerProduct);
        //???????????? ??? ??????
        List<ProductRecordDto> content = sellerProduct.getContent();

        //?????? ????????? ???
        int totalPages = sellerProduct.getTotalPages();

        //?????? ?????????
        int presentPage = sellerProduct.getNumber();

        //???????????? ????????? ????????? ?????????
        model.addAttribute("allProduct", content);

        //?????? ????????? ??? ????????? ?????????
        model.addAttribute("totalPages",totalPages);

        //?????? ?????????  ????????? ?????????
        model.addAttribute("presentPage",presentPage);

        model.addAttribute("sale", ProductType.sale);
        return "myPage/memberWishlist";
    }

    @GetMapping("/saleOrders")
    public String saleOrders(@PageableDefault(size=6)Pageable pageable,
                             Model model,
                             HttpServletRequest request) {

        Members sessionMember = getSessionMembers(request);

        Page<OrderHistoryDto> saleOrders = orderService.findMemberSaleOrder(sessionMember.getId(), pageable);

        calculationDeadLine(saleOrders);
        //?????? ????????? ???
        int totalPages = saleOrders.getTotalPages();
        //?????? ?????????
        int presentPage = saleOrders.getNumber();
        //?????? ????????? ??? ????????? ?????????
        model.addAttribute("totalPages",totalPages);
        //?????? ?????????  ????????? ?????????
        model.addAttribute("presentPage",presentPage);

        model.addAttribute("saleOrders", saleOrders);
        model.addAttribute("orderType", OrderType.values() );

        return "/myPage/memberSaleOrders";
    }

    @GetMapping("/purchaseOrders")
    public String purchaseOrders(@PageableDefault(size=6)Pageable pageable, Model model, HttpServletRequest request) {

        Members sessionMembers = getSessionMembers(request);
        Page<OrderHistoryDto> purchaseOrders = orderService.findMemberPurchaseOrder(sessionMembers.getId(), pageable);

        calculationDeadLine(purchaseOrders);
        
        int totalPages = purchaseOrders.getTotalPages(); //?????? ????????? ???
        int presentPage = purchaseOrders.getNumber(); //?????? ?????????
        model.addAttribute("totalPages",totalPages); //?????? ?????????  ????????? ?????????
        model.addAttribute("presentPage",presentPage); //?????? ?????????  ????????? ?????????

        model.addAttribute("purchaseOrders", purchaseOrders);
        model.addAttribute("orderType", OrderType.values());

        return "/myPage/memberPurchaseOrders";

    }

    @GetMapping("/myChatRoomList")
    public String myChatRoomList(Model model ,
                                 HttpServletRequest request,
                                 @PageableDefault(size=6)Pageable pageable){
        Members sessionMembers = getSessionMembers(request);

        Page<ChatRoomTableDto> memberChatRoomList = chatService.getMemberChatRoomList(sessionMembers.getId(), pageable);
        model.addAttribute("memberChatRoomList", memberChatRoomList);

        int totalPages = memberChatRoomList.getTotalPages(); //?????? ????????? ???
        int presentPage = memberChatRoomList.getNumber(); //?????? ?????????
        model.addAttribute("totalPages",totalPages); //?????? ?????????  ????????? ?????????
        model.addAttribute("presentPage",presentPage); //?????? ?????????  ????????? ?????????

        return "/myPage/memberChatRoomList";
    }

    @GetMapping("/room/{roomId}")
    public String myRoom(@PathVariable("roomId") Long roomId,
                         Model model,
                         HttpServletRequest request) throws IOException {
        Members sessionMembers = getSessionMembers(request);
        Long memberId = sessionMembers.getId();
        if (!chatService.checkRoomPermission(roomId, memberId) ) {
            return "redirect:/members/myPage/myChatRoomList";
        }

        ChatRoomDto chatRoomDto = chatService.getChatRoom(roomId);

        List<ChatMessageDto> chatMessageList = chatService.getChatMessageList(roomId, memberId);
        log.info("chatmessageList: {}", chatMessageList);

        model.addAttribute("chatRoomDto", chatRoomDto);
        model.addAttribute( "chatMessageList", chatMessageList);

        return "/myPage/memberChatRoom";

    }

    @PostMapping("/room/out/{roomId}")
    public String myRoomOut(@PathVariable("roomId") Long roomId,
                            HttpServletRequest request,
                            RedirectAttributes redirectAttributes
    ) {

        Members sessionMembers = getSessionMembers(request);

        if ( chatService.exitRoom(roomId, sessionMembers.getId()) ) {

            return "redirect:/members/myPage/myChatRoomList";
        }

        redirectAttributes.addAttribute("roomId", roomId);

        return "redirect:/members/myPage/room/{roomId}";
    }

    private void calculationDeadLine(Page<OrderHistoryDto> tradeOrders) {
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
