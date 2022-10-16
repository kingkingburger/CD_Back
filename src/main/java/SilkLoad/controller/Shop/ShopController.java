package SilkLoad.controller.Shop;

import SilkLoad.dto.*;
import SilkLoad.entity.OrderEnum.OrderType;
import SilkLoad.entity.ProductEnum.ProductTime;
import SilkLoad.entity.ProductEnum.ProductType;
import SilkLoad.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("shop")
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
                       @PageableDefault(size=6)Pageable pageable

                       ) {
        model.addAttribute("first", first);
        model.addAttribute("second",second);
        log.info("keyword {},", keyword);
        Page<HomeProductDto> productData;
        //first가 있는지 없는지에 따라 content가 변한다.

        if(keyword != null && keyword != "")
            productData = productSearchService.SearchToHomeProductName(keyword, pageable);
        else
            productData = productSearchService.SearchToCategory(first,second, pageable);

        //--------------------크롤링 데이터 보내는 부분----------------------

        Page<CrawlingDto> crawlingdata;
        if(keyword != null && keyword != "")
            crawlingdata = crawlingService.SearchCategoryCrwalingProductformProductname(keyword, pageable);
        else {
            crawlingdata = crawlingService.getcrawlingdatafirstandsecond(pageable, first, second);
        }


//        --------------------네이버 데이터 보내는 부분----------------------

        Page<NaverProductDto> naverData;
        if(keyword != null && keyword != "") {
            naverData = naverProductService.SearchContainingTitle(keyword, pageable);
        }
        else
            naverData = null;


        int totalPagesArray[];
        //keyword 검색
        if (keyword != null && keyword != "") {
            totalPagesArray = new int[3];
            totalPagesArray[0] = productData.getTotalPages();
            totalPagesArray[1] = crawlingdata.getTotalPages();
            totalPagesArray[2] = naverData.getTotalPages();
        }
        //카테고리 검색
        else {
            totalPagesArray = new int[2];
            totalPagesArray[0] = productData.getTotalPages();
            totalPagesArray[1] = crawlingdata.getTotalPages();
        }

        Arrays.sort(totalPagesArray);
        int biggestPage;
        biggestPage = totalPagesArray[totalPagesArray.length -1];


        int totalPages = biggestPage;
        int presentPage = pageable.getPageNumber();

        log.info("totalPages {}", totalPages);
        log.info("presentPage {}", presentPage);

        model.addAttribute("crawlingdata",crawlingdata);
        model.addAttribute("naverData", naverData);
        model.addAttribute("keyword", keyword);
        model.addAttribute("productData", productData);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("presentPage", presentPage);
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

//        네이버 쇼핑 물품 보내는 부분
        List<NaverProductDto> naverProductDtos = naverProductService.naverShopSearchAPI(naverRequestVariableDto);
        model.addAttribute("naverProductList", naverProductDtos);


//        이미지 분석을 통한 추천 상품
        naverRequestVariableDto.setQuery(byId_productRecordDto.getPredictionImage());
        List<NaverProductDto> predictionNaverProductDtos = naverProductService.naverShopSearchAPI(naverRequestVariableDto);
        model.addAttribute("predictionNaverProductList",predictionNaverProductDtos);

        
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