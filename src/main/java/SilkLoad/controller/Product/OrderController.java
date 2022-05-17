package SilkLoad.controller.Product;

import SilkLoad.SessionConst;
import SilkLoad.dto.MemberFormDto;
import SilkLoad.dto.OrderFormDto;
import SilkLoad.entity.Members;
import SilkLoad.service.OrderService;
import SilkLoad.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/product/order")
public class OrderController {

    private final OrderService orderService;
    private final ProductService productService;

    @PostMapping("/buyNow")
    public String orderSave(@ModelAttribute("orderFormDto")OrderFormDto orderFormDto) {

        log.info("orderFormDto ={}",orderFormDto);

        if ( orderService.save(orderFormDto)) {

            productService.changeTypeToWaiting(orderFormDto.getProductId());

            return "redirect:/";
        }

        //실패한 경우, error인 경우 추후 코드 추가할 예정
        return "redirect:/";

    }


}
