package SilkLoad.controller.Product;

import SilkLoad.dto.OrderBuyAuctionDto;
import SilkLoad.dto.OrderBuyNowDto;
import SilkLoad.entity.Orders;
import SilkLoad.service.NotificationsService;
import SilkLoad.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/product/order")
public class OrderController {

    private final OrderService orderService;
    private final NotificationsService notificationsService;

    @PostMapping("/buyNow")
    public String saveBuyNow(@ModelAttribute("orderBuyNowDto") OrderBuyNowDto orderBuyNowDto) {

        log.info("orderBuyNowDto ={}", orderBuyNowDto);
        Orders orders = orderService.saveBuyNow(orderBuyNowDto);

        if ( orders != null) {
            log.info("order 저장 성공");

            return "redirect:/";
        }

        //실패한 경우, error인 경우 추후 코드 추가할 예정
        return "redirect:/";

    }

    @PostMapping("/buyAuction")
    public String SaveAuction(@ModelAttribute("orderBuyAuctionDto") OrderBuyAuctionDto orderBuyAuctionDto, HttpServletRequest request) {

        Orders order = orderService.saveBuyAuction(orderBuyAuctionDto);

        if ( order == null) {
            log.info("order null 에러");
        } else {
            log.info("order = {}",order);
        }

        String referer = request.getHeader("Referer");

        return "redirect:" + referer;

    }

    // => 거래 성립은 판매자가 끝내면 끝, 주도권은 판매자
    @PostMapping("/transaction/complete/{orderId}")
    public String completeTransaction(@PathVariable Long orderId, HttpServletRequest request) {

        orderService.completeOrder(orderId);

        //이전 페이지 url
        String referer = request.getHeader("Referer");

        return "redirect:" + referer;
    }

    @PostMapping("/transaction/trading/{orderId}")
    public String tradingTransaction(@PathVariable Long orderId, HttpServletRequest request) {

        Orders orders = orderService.tradingOrder(orderId);
        if (orders == null) {
            log.info("Orders null error");
        }

        //이전 페이지 url
        String referer = request.getHeader("Referer");

        return "redirect:" + referer;
    }

    @PostMapping("/transaction/cancel/{orderId}")
    public String cancelTransaction(@PathVariable Long orderId, HttpServletRequest request) {

        orderService.cancelOrder(orderId);

        //이전 페이지 url
        String referer = request.getHeader("Referer");

        return "redirect:" + referer;
    }



}
