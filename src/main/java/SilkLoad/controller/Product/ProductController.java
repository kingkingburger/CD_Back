package SilkLoad.controller.Product;


import SilkLoad.SessionConst;
import SilkLoad.dto.ProductFormDto;
import SilkLoad.entity.ProductEnum.ProductTime;
import SilkLoad.service.ProductService;
import SilkLoad.entity.Members;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.net.MalformedURLException;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @ModelAttribute("productTimes")
    public ProductTime[] productType() {
        return ProductTime.values();
    }

    @GetMapping("/add")
    public String addProduct(@ModelAttribute("productData") ProductFormDto productData) {
        return "addProductForm";
    }

    @PostMapping("/add")
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
