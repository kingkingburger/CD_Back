package SilkLoad.controller.Product;

import SilkLoad.dto.OrderBuyAuctionDto;
import SilkLoad.dto.OrderBuyNowDto;
import SilkLoad.entity.Orders;
import SilkLoad.service.OrderService;
import SilkLoad.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/product/order")
public class OrderController {

    private final OrderService orderService;
    private final ProductService productService;

    @PostMapping("/buyNow")
    public String saveBuyNow(@ModelAttribute("orderBuyNowDto") OrderBuyNowDto orderBuyNowDto) {

        log.info("orderBuyNowDto ={}", orderBuyNowDto);

        if ( orderService.saveBuyNowDto(orderBuyNowDto) != null) {
            log.info("order 저장 성공");
             if( productService.changeTypeToWaiting(orderBuyNowDto.getProductId()) != null)  {
                 log.info("TypeToWaiting 성공");

             }

            return "redirect:/";
        }

        //실패한 경우, error인 경우 추후 코드 추가할 예정
        return "redirect:/";

    }

    @PostMapping("/buyAuction")
    public String SaveAuction(@ModelAttribute("orderBuyAuctionDto")OrderBuyAuctionDto orderBuyAuctionDto, HttpServletRequest request) {

        Orders order = orderService.saveBuyAuctionDto(orderBuyAuctionDto);

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

        orderService.tradingOrder(orderId);

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
