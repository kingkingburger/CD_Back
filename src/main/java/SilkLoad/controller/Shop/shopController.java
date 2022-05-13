package SilkLoad.controller.Shop;

import SilkLoad.SessionConst;
import SilkLoad.dto.MemberFormDto;
import SilkLoad.dto.ProductRecordDto;
import SilkLoad.entity.Members;
import SilkLoad.entity.Product;
import SilkLoad.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/shop")
public class shopController {

    private final ProductService productService;


    @GetMapping
    public String homeLogin(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Members loginMember,
                            Model model,
                            @ModelAttribute("member") MemberFormDto member) {

        //세션에 데이터가 없어도 model을 넣어준다.
        List<ProductRecordDto> allProduct = productService.findAllProduct();
        model.addAttribute("allproduct", allProduct);

        //세션에 회원 데이터가 없으면
        if (loginMember == null) {

            return "shop";
        }

        //세션이 유지되면 로그인으로 이동
        model.addAttribute("member", loginMember);
        return "shop";
    }

    /**
     * product의 상세 페이지
     *
     * @param id    query string으로 보내옴
     * @param model
     * @return id값으로 찾아온 product 1개를 model에 담아서 보내줌
     */
    @GetMapping("/detailProduct")
    public String addProduct(@RequestParam Long id,
                             Model model) {

        List<ProductRecordDto> allProduct = productService.findAllProduct();
        Product byId_product = productService.findById_Product(id);
        ProductRecordDto productRecordDto = productService.getProductRecordDto(byId_product);

        model.addAttribute("product", productRecordDto);
        model.addAttribute("allProduct", allProduct);

        return "detailProduct";
    }

}
