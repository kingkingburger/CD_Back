package SilkLoad.controller.Cart;


import SilkLoad.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CartController {

    private final ProductService productService;

    @GetMapping("/Cart")
    public String UserCart() {
        return "shop-cart";
    }

}
