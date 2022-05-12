package SilkLoad.controller.Product;


import SilkLoad.SessionConst;
import SilkLoad.dto.ProductFormDto;
import SilkLoad.dto.ProductRecordDto;
import SilkLoad.entity.Product;
import SilkLoad.entity.ProductTime;
import SilkLoad.service.ProductService;
import SilkLoad.entity.Members;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @ModelAttribute("productTimes")
    public ProductTime[] productType() {
        return ProductTime.values();
    }

    @GetMapping("/addProduct")
    public String addProduct(@ModelAttribute("productData") ProductFormDto productData) {
        return "addProductForm";
    }

    @PostMapping("/addProduct")
    public String saveProduct(@Valid @ModelAttribute("productData") ProductFormDto productData,
                              BindingResult bindingResult,
                              HttpServletRequest request) throws IOException {

        if (bindingResult.hasErrors()) {
            return "addProductForm";
        }

        HttpSession session = request.getSession();
        Members loginMember = (Members) session.getAttribute(SessionConst.LOGIN_MEMBER);

        productService.save(productData, loginMember);

        return "redirect:/";
    }


    /**
     * product의 상세 페이지
     *
     * @param id    query string으로 보내옴
     * @param model
     * @return id값으로 찾아온 product 1개를 model에 담아서 보내줌
     */
    @GetMapping("/Product")
    public String addProduct(@RequestParam Long id,
                             Model model) {

        List<ProductRecordDto> allProduct = productService.findAllProduct();
        Product byId_product = productService.findById_Product(id);
        ProductRecordDto productRecordDto = productService.getProductSaleDto(byId_product);

        model.addAttribute("product", productRecordDto);
        model.addAttribute("allProduct", allProduct);


        return "detailProduct";
    }


    /**
     * @param filename 실제 파일명
     * @return 파일 시스템에서 찾은 파일 반환
     * @throws MalformedURLException
     */
    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws
            MalformedURLException {
        return new UrlResource("file:" + productService.getFullPath(filename));
    }


}
