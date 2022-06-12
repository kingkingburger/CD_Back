package SilkLoad.controller.Home;


import SilkLoad.dto.CrawlingDto;
import SilkLoad.dto.ProductRecordDto;
import SilkLoad.entity.Product;
import SilkLoad.entity.ProductEnum.ProductType;
import SilkLoad.repository.ProductRepository;
import SilkLoad.service.CrawlingService;
import SilkLoad.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final CrawlingService crawlingService;

    /**
     * @param model product들을 담기위한 매개변수
     * @return model List형태로 view에 반환
     * model에 반환되는 객체들은 entitiy로 반환 절대 금지 => jSON 생성 무한 루프 (양방향 관계)
     * Dto로 반환해야 한다.
     */
    @GetMapping("/")
    public String home(Model model,
                       HttpServletRequest request,
                       @PageableDefault(size=8) Pageable pageable) {//@PageableDefault로 기본 8개를 가지고 오게했다.
        List<ProductRecordDto> content = productService.paged_product(pageable).getContent();

        model.addAttribute("Products", content);
        model.addAttribute("sale", ProductType.sale);

        Page<CrawlingDto> women_close = crawlingService.getcrawlingdata(pageable, "여성의류");
        Page<CrawlingDto> men_close = crawlingService.getcrawlingdata(pageable,"남성의류");
        Page<CrawlingDto> shose = crawlingService.getcrawlingdata(pageable, "패션");
        Page<CrawlingDto> sport = crawlingService.getcrawlingdata(pageable, "스포츠");
        Page<CrawlingDto> car = crawlingService.getcrawlingdata(pageable, "차량");
        Page<CrawlingDto> star = crawlingService.getcrawlingdata(pageable, "굿즈");
        Page<CrawlingDto> toy = crawlingService.getcrawlingdata(pageable, "키덜트");
        Page<CrawlingDto> art = crawlingService.getcrawlingdata(pageable, "예술");
        Page<CrawlingDto> book = crawlingService.getcrawlingdata(pageable, "도서");
        Page<CrawlingDto> family = crawlingService.getcrawlingdata(pageable, "가구");
        Page<CrawlingDto> life = crawlingService.getcrawlingdata(pageable, "생활");
        Page<CrawlingDto> kid = crawlingService.getcrawlingdata(pageable, "유아");
        Page<CrawlingDto> animal = crawlingService.getcrawlingdata(pageable, "동물");
        Page<CrawlingDto> etc = crawlingService.getcrawlingdata(pageable, "기타");

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
        model.addAttribute("etc",etc);

        return "index";
    }

}
