package SilkLoad.service;

import SilkLoad.dto.CrawlingDto;
import SilkLoad.dto.NaverProductDto;
import SilkLoad.dto.NaverRequestVariableDto;
import SilkLoad.entity.Crawling;
import SilkLoad.entity.NaverProduct;
import SilkLoad.repository.NaverProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NaverProductService {

    private final NaverProductRepository naverProductRepository;

    @PostConstruct
    public void naverProductInit() throws  Exception  {

//        naverProductRepository.deleteAll();
//
//
//        NaverRequestVariableDto naverRequestVariableDto = NaverRequestVariableDto.builder()
//                .query("중고 여성의류")
//                .display(9)
//                .start(1)
//                .sort("sim")
//                .build();
//
//        List<NaverProductDto> womenClothingDtoList = naverShopSearchAPI(naverRequestVariableDto);
//        List<NaverProduct> naverProductList = new ArrayList<>();
//        changeNaverProductDtoList(womenClothingDtoList, naverProductList, naverRequestVariableDto.getQuery().split("중고 ")[1]);
//        naverProductRepository.saveAll( (Iterable) naverProductList );
//
//        naverRequestVariableDto.setQuery("중고 남성의류");
//        List<NaverProductDto> menClothingDtoList = naverShopSearchAPI(naverRequestVariableDto);
//        List<NaverProduct> menClothingList = new ArrayList<>();
//        changeNaverProductDtoList(menClothingDtoList, menClothingList, naverRequestVariableDto.getQuery().split("중고 ")[1]);
//        naverProductRepository.saveAll( (Iterable) menClothingList );
//
//
//        naverRequestVariableDto.setQuery("중고 신발");
//        List<NaverProductDto> shoesDtoList = naverShopSearchAPI(naverRequestVariableDto);
//        List<NaverProduct> shoesList = new ArrayList<>();
//        changeNaverProductDtoList(shoesDtoList, shoesList, naverRequestVariableDto.getQuery().split("중고 ")[1]);
//        naverProductRepository.saveAll( (Iterable) shoesList );
//
//        naverRequestVariableDto.setQuery("중고 스포츠/레저");
//        List<NaverProductDto> sportsDtoList = naverShopSearchAPI(naverRequestVariableDto);
//        List<NaverProduct> sportsList = new ArrayList<>();
//        changeNaverProductDtoList(sportsDtoList, sportsList, naverRequestVariableDto.getQuery().split("중고 ")[1]);
//        naverProductRepository.saveAll( (Iterable) sportsList );
//
//        naverRequestVariableDto.setQuery("중고 차량/오토바이");
//        List<NaverProductDto> vehicleDtoList = naverShopSearchAPI(naverRequestVariableDto);
//        List<NaverProduct> vehicleList = new ArrayList<>();
//        changeNaverProductDtoList(vehicleDtoList, vehicleList, naverRequestVariableDto.getQuery().split("중고 ")[1]);
//        naverProductRepository.saveAll( (Iterable) vehicleList );
//
//        naverRequestVariableDto.setQuery("중고 스타굿즈");
//        List<NaverProductDto> starGoodsDtoList = naverShopSearchAPI(naverRequestVariableDto);
//        List<NaverProduct> starGoodsList = new ArrayList<>();
//        changeNaverProductDtoList(starGoodsDtoList, starGoodsList, naverRequestVariableDto.getQuery().split("중고 ")[1]);
//        naverProductRepository.saveAll( (Iterable) starGoodsList );
//
//        naverRequestVariableDto.setQuery("중고 키덜트");
//        List<NaverProductDto> kidultDtoList = naverShopSearchAPI(naverRequestVariableDto);
//        List<NaverProduct> kidultList = new ArrayList<>();
//        changeNaverProductDtoList(kidultDtoList, kidultList, naverRequestVariableDto.getQuery().split("중고 ")[1]);
//        naverProductRepository.saveAll( (Iterable) kidultList );
//
//        naverRequestVariableDto.setQuery("중고 예술");
//        List<NaverProductDto> artDtoList = naverShopSearchAPI(naverRequestVariableDto);
//        List<NaverProduct> artList = new ArrayList<>();
//        changeNaverProductDtoList(artDtoList, artList, naverRequestVariableDto.getQuery().split("중고 ")[1]);
//        naverProductRepository.saveAll( (Iterable) artList );
//
//        naverRequestVariableDto.setQuery("중고 문구책");
//        List<NaverProductDto> bookDtoList = naverShopSearchAPI(naverRequestVariableDto);
//        List<NaverProduct> bookList = new ArrayList<>();
//        changeNaverProductDtoList(bookDtoList, bookList, naverRequestVariableDto.getQuery().split("중고 ")[1]);
//        naverProductRepository.saveAll( (Iterable) bookList );
//
//        // 429 bad request 방지, 초당 10건 이상의 요청을 보내면 내이버에서 거부
//        Thread.sleep(1000);
//
//        naverRequestVariableDto.setQuery("중고 가구");
//        List<NaverProductDto> furnitureDtoList = naverShopSearchAPI(naverRequestVariableDto);
//        List<NaverProduct> furnitureList = new ArrayList<>();
//        changeNaverProductDtoList(furnitureDtoList, furnitureList, naverRequestVariableDto.getQuery().split("중고 ")[1]);
//        naverProductRepository.saveAll( (Iterable) furnitureList );
//
//        naverRequestVariableDto.setQuery("중고 가공식품");
//        List<NaverProductDto> processedFoodDtoList = naverShopSearchAPI(naverRequestVariableDto);
//        List<NaverProduct> processedFoodList = new ArrayList<>();
//        changeNaverProductDtoList(processedFoodDtoList, processedFoodList, naverRequestVariableDto.getQuery().split("중고 ")[1]);
//        naverProductRepository.saveAll( (Iterable) processedFoodList );
//
//        naverRequestVariableDto.setQuery("중고 유아동");
//        List<NaverProductDto> infantChildDtoList = naverShopSearchAPI(naverRequestVariableDto);
//        List<NaverProduct> infantChildList = new ArrayList<>();
//        changeNaverProductDtoList(infantChildDtoList, infantChildList,naverRequestVariableDto.getQuery().split("중고 ")[1]);
//        naverProductRepository.saveAll( (Iterable) infantChildList );
//
//        naverRequestVariableDto.setQuery("중고 반려동물");
//        List<NaverProductDto> petDtoList = naverShopSearchAPI(naverRequestVariableDto);
//        List<NaverProduct> petList = new ArrayList<>();
//        changeNaverProductDtoList(petDtoList, petList, naverRequestVariableDto.getQuery().split("중고 ")[1]);
//        naverProductRepository.saveAll( (Iterable) petList );


    }

    public void changeNaverProductDtoList(List<NaverProductDto> naverProductDtoList,
                                           List<NaverProduct> naverProductList,
                                           String category) {
        naverProductDtoList.forEach( (naverProductDto -> {
            naverProductList.add(changeNaverProduct(naverProductDto, category));
            }));
    }

    public NaverProduct changeNaverProduct(NaverProductDto naverProductDto, String category ) {

        return NaverProduct.builder()
                .title(naverProductDto.getTitle())
                .image(naverProductDto.getImage())
                .link(naverProductDto.getLink())
                .lprice(naverProductDto.getLprice())
                .category(category)
                .build();
    }

    public List<NaverProductDto> naverShopSearchAPI(NaverRequestVariableDto naverVariable) {
        //참고 url: https://ssong915.tistory.com/36
        String url = "https://openapi.naver.com/";

        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .path("v1/search/shop.json")
                .queryParam("query", naverVariable.getQuery())
                .queryParam("display", naverVariable.getDisplay())
                .queryParam("start", naverVariable.getStart())
                .queryParam("sort", naverVariable.getSort())
                .encode()
                .build()
                .toUri();

        RestTemplate restTemplate = new RestTemplate();
        RequestEntity<Void> req = RequestEntity
                .get(uri)
                .header("X-Naver-Client-Id", "SsFhVJgkJKnUClEiI5uw")
                .header("X-Naver-Client-Secret", "DM1095XQ8O")
                .build();
        /*
        강준호
        client-id: COuB5FOeyoiu2FsY3LXZ
        password: qxx86y7sg4
         */

        ResponseEntity<String> result = restTemplate.exchange(req, String.class);
        List<NaverProductDto> naverProductDtos = fromJSONtoNaverProduct(result.getBody());

        return naverProductDtos;

    }
    private List<NaverProductDto> fromJSONtoNaverProduct(String result) {
        // 문자열 정보를 JSONObject로 바꾸기
        JSONObject rjson = new JSONObject(result);
        // JSONObject에서 items 배열 꺼내기
        // JSON 배열이기 때문에 보통 배열이랑 다르게 활용해야한다.
        JSONArray naverProducts = rjson.getJSONArray("items");
        List<NaverProductDto> naverProductDtoList = new ArrayList<>();
        for (int i = 0; i < naverProducts.length(); i++) {
            JSONObject naverProductsJson = (JSONObject) naverProducts.get(i);
            NaverProductDto itemDto = new NaverProductDto(naverProductsJson);
            naverProductDtoList.add(itemDto);
        }
        return naverProductDtoList;
    }
    public Page<NaverProductDto> SearchContainingTitle(String title, Pageable pageable) {

        return naverProductRepository.findByTitleContainingIgnoreCase(title, pageable)
                .map(this::changeNaverProductDto);

    }

    public List<NaverProductDto> findByCategory(String category ) {

        List<NaverProduct> byCategoryNaverProductList = naverProductRepository.findByCategory(category);
        return changeNaverProductDtoList(byCategoryNaverProductList);
    }

    private List<NaverProductDto> changeNaverProductDtoList(List<NaverProduct> naverProductList) {

        List<NaverProductDto> naverProductDtoList = new ArrayList<>();
        naverProductList.forEach( (naverProduct -> {

            naverProductDtoList.add(changeNaverProductDto(naverProduct));

        }) );
        return naverProductDtoList;

    }

    private NaverProductDto changeNaverProductDto(NaverProduct naverProduct) {
        return NaverProductDto.builder()
                .title(naverProduct.getTitle())
                .image(naverProduct.getImage())
                .link(naverProduct.getLink())
                .lprice(naverProduct.getLprice())
                .build();
    }

}
