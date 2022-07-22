package SilkLoad.controller.Shop;

import SilkLoad.dto.*;
import SilkLoad.entity.ProductEnum.ProductTime;
import SilkLoad.entity.ProductEnum.ProductType;
import SilkLoad.service.*;
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
    private final ProductSearchService productSearchService;
    private final NaverProductService naverProductService;
    private final PagedProductService pagedProductService;
    private final OrderService orderService;
    private final CrawlingService crawlingService;

    @GetMapping
    public String shop(Model model,
                       @RequestParam(required = false, name = "first") String first,
                       @RequestParam(required = false, name = "second") String second,
                       @RequestParam(required = false, name = "keyword")String keyword,
                       @PageableDefault(size = 9) Pageable pageable) {
        //카테고리들
        model.addAttribute("first", first);
        model.addAttribute("second",second);


        //전체 페이지 수
        int totalPages = pagedProductService.paged_product(pageable).getTotalPages();
        //현제 페이지
        int presentPage = pagedProductService.paged_product(pageable).getNumber();


        List<ProductRecordDto> content;
        //first가 있는지 없는지에 따라 content가 변한다.

        if(keyword != null)
            content = productSearchService.SearchToProductname(keyword, pageable).getContent();
        else
            content = productService.pagedByfirstsecondcategoryProduct(first,second, pageable).getContent();


        model.addAttribute("allProduct", content);
        //전체 페이지 수 모델로 보내기
        model.addAttribute("totalPages",totalPages);
        //현제 페이지  모델로 보내기
        model.addAttribute("presentPage",presentPage);
        //판매중인 상태 보내기
        model.addAttribute("sale", ProductType.sale);


        //--------------------크롤링 데이터 보내는 부분----------------------
        Page<CrawlingDto> crawlingdata = crawlingService.getcrawlingdatafirstandsecond(pageable, first, second);
        model.addAttribute("crawlingdata",crawlingdata);

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
                                @RequestParam(required = false, name = "first") String first,
                                @RequestParam(required = false, name = "second") String second,
                                @RequestParam(required = false, name = "third") String third,
                                @PageableDefault(size = 9) Pageable pageable,
                                Model model) {

        ProductRecordDto byId_productRecordDto = productService.findById_ProductRecordDto(id);

        //productType.sale이 판매 중이 아니라면 error 페이저로 보내기
        if (byId_productRecordDto.getProductType() != ProductType.sale) {
            return "error";
        }

        Long maxAuctionPrice = orderService.findByMaxAuctionPrice(id);

        NaverRequestVariableDto naverRequestVariableDto = NaverRequestVariableDto.builder()
                .query(third)
                .display(9)
                .start(1)
                .sort("sim")
                .build();


        List<NaverProductDto> naverProductDtos = naverProductService.naverShopSearchAPI(naverRequestVariableDto);

        model.addAttribute("naverProductList", naverProductDtos);
        //--------------------크롤링 데이터 보내는 부분----------------------
        Page<CrawlingDto> crawlingdata = crawlingService.getcrawlingdataFirstSecondThird(pageable, first, second, third);
        model.addAttribute("crawlingdata",crawlingdata);
        //----------------------------------------------------------------


        model.addAttribute("maxAuctionPrice", maxAuctionPrice);
        model.addAttribute("productTime", ProductTime.values());
        model.addAttribute("product", byId_productRecordDto);


        return "detailProduct";
    }

}
