package SilkLoad.controller.Member;

import SilkLoad.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class TransactionController {

    private final OrderService orderService;

    // => 거래 성립은 판매자가 끝내면 끝, 주도권은 판매자
    @PostMapping("/transaction/{orderId}")
    public String completeTransaction(@PathVariable Long orderId, HttpServletRequest request) {

        orderService.completeOrder(orderId);

        //이전 페이지 url
        String referer = request.getHeader("Referer");

        return "redirect:" + referer;
     }


}
