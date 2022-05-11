package SilkLoad.controller.Member;

import SilkLoad.dto.ProductFormDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MyPageController {

    @GetMapping("/MyPage")
    public String MyPage(@ModelAttribute("members") ProductFormDto productData) {

        return "MyPage";
    }

    @GetMapping("/account-wishlist")
    public String Wishlist(@ModelAttribute("productData") ProductFormDto productData) {

        return "account-wishlist";
    }
    @GetMapping("/account-orders")
    public String Orders(@ModelAttribute("productData") ProductFormDto productData) {
        return "account-orders";
    }
}
