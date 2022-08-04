package SilkLoad.controller.Home;


import SilkLoad.dto.CrawlingDto;
import SilkLoad.dto.ProductRecordDto;
import SilkLoad.entity.Product;
import SilkLoad.entity.ProductEnum.ProductType;
import SilkLoad.repository.ProductRepository;
import SilkLoad.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ProductService productService;
    private final PagedProductService pagedProductService;
    private final CrawlingService crawlingService;
//    private final OrderService orderService;

    /**
     * @param model product들을 담기위한 매개변수
     * @return model List형태로 view에 반환
     * model에 반환되는 객체들은 entitiy로 반환 절대 금지 => jSON 생성 무한 루프 (양방향 관계)
     * Dto로 반환해야 한다.
     */
    @GetMapping("/")
    public String home(Model model,
                       HttpServletRequest request,
                       @PageableDefault(size=8) Pageable pageable) {//@PageableDefault로 기본 8개를 가지고 오게했다crawling.
        List<ProductRecordDto> content = pagedProductService.paged_product(pageable).getContent();


        model.addAttribute("Products", content);
        model.addAttribute("sale", ProductType.sale);
//        model.addAttribute("order", orderService);

        Page<CrawlingDto> women_close = crawlingService.getcrawlingdatafirst(pageable, "여성의류");
        Page<CrawlingDto> men_close = crawlingService.getcrawlingdatafirst(pageable,"남성의류");
        Page<CrawlingDto> shose = crawlingService.getcrawlingdatafirst(pageable, "신발");
        Page<CrawlingDto> sport = crawlingService.getcrawlingdatafirst(pageable, "스포츠/레저");
        Page<CrawlingDto> car = crawlingService.getcrawlingdatafirst(pageable, "차량/오토바이");
        Page<CrawlingDto> star = crawlingService.getcrawlingdatafirst(pageable, "스타굿즈");
        Page<CrawlingDto> toy = crawlingService.getcrawlingdatafirst(pageable, "키덜트");
        Page<CrawlingDto> art = crawlingService.getcrawlingdatafirst(pageable, "예술/희귀/수집품");
        Page<CrawlingDto> book = crawlingService.getcrawlingdatafirst(pageable, "도서/티켓/문구");
        Page<CrawlingDto> family = crawlingService.getcrawlingdatafirst(pageable, "가구/인테리어");
        Page<CrawlingDto> life = crawlingService.getcrawlingdatafirst(pageable, "생활/가공식품");
        Page<CrawlingDto> kid = crawlingService.getcrawlingdatafirst(pageable, "유아동/출산");
        Page<CrawlingDto> animal = crawlingService.getcrawlingdatafirst(pageable, "반려동물용품");

        model.addAttribute("women_close",women_close);
        model.addAttribute("men_close",men_close);
        model.addAttribute("shose",shose);
        model.addAttribute("sport",sport);
        model.addAttribute("car",car);
        model.addAttribute("star",star);
        model.addAttribute("toy",toy);
        model.addAttribute("art",art);
        model.addAttribute("book",book);
        model.addAttribute("family",family);
        model.addAttribute("life",life);
        model.addAttribute("kid",kid);
        model.addAttribute("animal",animal);

        return "index";
    }

}
