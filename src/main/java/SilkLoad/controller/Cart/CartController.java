package SilkLoad.controller.Cart;


import SilkLoad.dto.CartDto;
import SilkLoad.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/product/order")
public class CartController {

    private final CartService cartService;

    @PostMapping("/inCart")

    public String UserCart(@ModelAttribute("CartDto") CartDto cartDto) {

        log.info("cart : {}",cartDto);

        if (cartService.save(cartDto)) {
            return "redirect:/";
        }

        //실패한 경우, error인 경우 추후 코드 추가할 예정
        return "redirect:/";
    }

    @PostMapping("/deleteCart")
    public String deleteCart(@ModelAttribute("CartDto") CartDto cartDto) {

        cartService.deleteProductInCart(cartDto.getProductid());

        //실패한 경우, error인 경우 추후 코드 추가할 예정
        return "redirect:/members/myPage/wishlist";
    }

}
