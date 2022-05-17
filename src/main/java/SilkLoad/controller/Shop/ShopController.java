package SilkLoad.controller.Shop;

import SilkLoad.SessionConst;
import SilkLoad.dto.MemberFormDto;
import SilkLoad.dto.OrderFormDto;
import SilkLoad.dto.ProductRecordDto;
import SilkLoad.entity.Members;
import SilkLoad.entity.ProductEnum.ProductType;
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
public class ShopController {

    private final ProductService productService;

    @GetMapping
    public String homeLogin(/*@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Members loginMember,*/
                            Model model
                            /*,@ModelAttribute("member") MemberFormDto member*/
                            ) {

        //세션에 데이터가 없어도 model을 넣어준다.
        List<ProductRecordDto> allProduct = productService.findAllProduct();
        model.addAttribute("allProduct", allProduct);
        model.addAttribute("sale", ProductType.sale);

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

        ProductRecordDto byId_productRecordDto = productService.findById_ProductRecordDto(id);
/*      Product byId_product = productService.findById_Product(id);
        ProductRecordDto productRecordDto = productService.getProductRecordDto(byId_product);*/
        //productType.sale이 판매 중이 아니라면 error 페이저로 보내기
        if (byId_productRecordDto.getProductType() != ProductType.sale) {
            return "error";
        }

        model.addAttribute("product", byId_productRecordDto);
        model.addAttribute("allProduct", allProduct);

        return "detailProduct";
    }

}
