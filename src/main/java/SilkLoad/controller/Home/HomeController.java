package SilkLoad.controller.Home;


import SilkLoad.SessionConst;
import SilkLoad.config.auth.dto.SessionUser;
import SilkLoad.dto.CrawlingDto;
import SilkLoad.dto.NaverProductDto;
import SilkLoad.dto.NaverRequestVariableDto;
import SilkLoad.dto.ProductRecordDto;
import SilkLoad.entity.Product;
import SilkLoad.entity.ProductEnum.ProductType;
import SilkLoad.entity.User;
import SilkLoad.repository.MemberRepository;
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
import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final PagedProductService pagedProductService;
    private final CrawlingService crawlingService;
    private final NaverProductService naverProductService;
    private final LoginService loginService;


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
//------------------------번개 장터-------------------------
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

        //-------------------------네이버 쇼핑몰--------------------------------------------------------

        NaverRequestVariableDto naverRequestVariableDto = NaverRequestVariableDto.builder()
                .query("중고 여성의류")
                .display(8)
                .start(1)
                .sort("sim")
                .build();

        //네이버 쇼핑 물품 보내는 부분
        List<NaverProductDto> womenClothingList = naverProductService.naverShopSearchAPI(naverRequestVariableDto);
        model.addAttribute("womenClothingList",womenClothingList);

        naverRequestVariableDto.setQuery("중고 남성의류");
        List<NaverProductDto> menClothingList = naverProductService.naverShopSearchAPI(naverRequestVariableDto);
        model.addAttribute("menClothingList",menClothingList);

        naverRequestVariableDto.setQuery("중고 신발");
        List<NaverProductDto> shoesList = naverProductService.naverShopSearchAPI(naverRequestVariableDto);
        model.addAttribute("shoesList", shoesList);

        naverRequestVariableDto.setQuery("중고 스포츠/레저");
        List<NaverProductDto> sportsList = naverProductService.naverShopSearchAPI(naverRequestVariableDto);
        model.addAttribute("sportsList", sportsList);

        naverRequestVariableDto.setQuery("차량/오토바이");
        List<NaverProductDto> vehicleList = naverProductService.naverShopSearchAPI(naverRequestVariableDto);
        model.addAttribute("vehicleList", vehicleList);

        naverRequestVariableDto.setQuery("중고 스타굿즈");
        List<NaverProductDto> starGoodsList = naverProductService.naverShopSearchAPI(naverRequestVariableDto);
        model.addAttribute("starGoodsList", starGoodsList);

        naverRequestVariableDto.setQuery("중고 키덜트");
        List<NaverProductDto> kidultList = naverProductService.naverShopSearchAPI(naverRequestVariableDto);
        model.addAttribute("kidultList", kidultList);

        //ouath2 성공시 데이터 들어오는 곳?
        HttpSession session = request.getSession();
        SessionUser user = (SessionUser) session.getAttribute("user");
//        log.info("user.getName = {}", user.getName());
//        log.info("user.gete = {}", user.getEmail());
//        log.info("user.gept = {}", user.getPicture());
        if (user != null) {
            //쿠키 이름: jsessionid, 값: uuid, uuid를 통해 session 속성에 접근, Member 객체를 email 기준으로 가지고옴
            log.info("user.getName = {}", user.getName());
            //기존 로그인 방식이랑 유사하게 하기위해 session에 email 기반의 Members 객체 데이터를 넣어둔다.
            session.setAttribute(SessionConst.LOGIN_MEMBER, loginService.loginWithOauth2(user.getEmail()));
            model.addAttribute("userName", user.getName());
        }

        return "index";
    }

}
