package SilkLoad.controller.Shop;

import SilkLoad.dto.CrawlingDto;
import SilkLoad.dto.ProductRecordDto;
import SilkLoad.entity.ProductEnum.ProductTime;
import SilkLoad.entity.ProductEnum.ProductType;
import SilkLoad.service.CrawlingService;
import SilkLoad.service.OrderService;
import SilkLoad.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    private final OrderService orderService;
    private final CrawlingService crawlingService;

    @GetMapping
    public String shop(Model model,
                       @RequestParam("category") String category,
                       @RequestParam(required = false, name = "first") String first,
                       @PageableDefault(size = 9) Pageable pageable) {
        //카테고리들
        model.addAttribute("category", category);
        model.addAttribute("first",first);


        //전체 페이지 수
        int totalPages = productService.paged_product(pageable).getTotalPages();
        //현제 페이지
        int presentPage = productService.paged_product(pageable).getNumber();

        List<ProductRecordDto> content;
        //first가 있는지 없는지에 따라 content가 변한다.
        if(first != null)
            //페이징화 된 객체
            content = productService.pagedByfirstcategoryProduct(first, pageable).getContent();
        else
            //페이징화 된 객체
            content = productService.pagedBysecondcategoryProduct(category, pageable).getContent();


        model.addAttribute("allProduct", content);
        //전체 페이지 수 모델로 보내기
        model.addAttribute("totalPages",totalPages);
        //현제 페이지  모델로 보내기
        model.addAttribute("presentPage",presentPage);
        //판매중인 상태 보내기
        model.addAttribute("sale", ProductType.sale);

        log.info("카테고리는 = {}", category);
        log.info("first 카테고리는 = {}", first);

        //--------------------크롤링 데이터 보내는 부분----------------------
        Page<CrawlingDto> crawlingdata = crawlingService.getcrawlingdata(pageable, category);
        model.addAttribute("crawlingdata",crawlingdata);
        log.info("카테고리는 = {}", category);

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
    public String detailProduct(@RequestParam Long id,
                                @RequestParam(required = false, name = "category") String category,
                                @RequestParam(required = false, name = "first") String first,
                                Model model) {

        List<ProductRecordDto> allProduct = productService.findAllProduct();

        ProductRecordDto byId_productRecordDto = productService.findById_ProductRecordDto(id);

        Long maxAuctionPrice = orderService.findByMaxAuctionPrice(id);
/*      Product byId_product = productService.findById_Product(id);
        ProductRecordDto productRecordDto = productService.getProductRecordDto(byId_product);*/
        //productType.sale이 판매 중이 아니라면 error 페이저로 보내기
        if (byId_productRecordDto.getProductType() != ProductType.sale) {
            return "error";
        }

        model.addAttribute("maxAuctionPrice", maxAuctionPrice);
        model.addAttribute("productTime", ProductTime.values());
        model.addAttribute("product", byId_productRecordDto);
        model.addAttribute("allProduct", allProduct);

        return "detailProduct";
    }

}
