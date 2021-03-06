package SilkLoad.service;

import SilkLoad.dto.NaverProductDto;
import SilkLoad.dto.NaverRequestVariableDto;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class NaverProductService {

    public List<NaverProductDto> naverShopSearchAPI(NaverRequestVariableDto naverVariable) {
        //참고 url: https://ssong915.tistory.com/36
        String url = "https://openapi.naver.com/";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        //네이버로 부터 발급받은 client id와 client secret
        headers.set("X-Naver-Client-Id", "COuB5FOeyoiu2FsY3LXZ");
        headers.set("X-Naver-Client-Secret", "qxx86y7sg4");

        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .path("v1/search/shop.json")
                .queryParam("query", naverVariable.getQuery())
                .queryParam("display", naverVariable.getDisplay())
                .queryParam("start", naverVariable.getStart())
                .queryParam("sort", naverVariable.getSort())
                .encode()
                .build()
                .toUri();
        log.info("uri : {}", uri);
        RestTemplate restTemplate = new RestTemplate();

        RequestEntity<Void> req = RequestEntity
                .get(uri)
                .header("X-Naver-Client-Id", "COuB5FOeyoiu2FsY3LXZ")
                .header("X-Naver-Client-Secret", "qxx86y7sg4")
                .build();

        ResponseEntity<String> result = restTemplate.exchange(req, String.class);
        List<NaverProductDto> naverProductDtos = fromJSONtoNaverProduct(result.getBody());

        log.info("result ={}", naverProductDtos);
        return naverProductDtos;

    }
    private List<NaverProductDto> fromJSONtoNaverProduct(String result) {
        // 문자열 정보를 JSONObject로 바꾸기
        JSONObject rjson = new JSONObject(result);
        System.out.println(rjson);
        // JSONObject에서 items 배열 꺼내기
        // JSON 배열이기 때문에 보통 배열이랑 다르게 활용해야한다.
        JSONArray naverProducts = rjson.getJSONArray("items");
        List<NaverProductDto> naverProductDtoList = new ArrayList<>();
        for (int i = 0; i < naverProducts.length(); i++) {
            JSONObject naverProductsJson = (JSONObject) naverProducts.get(i);
            NaverProductDto itemDto = new NaverProductDto(naverProductsJson);
            naverProductDtoList.add(itemDto);
//            // item 중에서 필요한 항목만 꺼내기
//            String title = itemJson.getString("title");
//            String image = itemJson.getString("image");
//            String link = itemJson.getString("link");
//            int lprice = itemJson.getInt("lprice");
        }
        return naverProductDtoList;
    }

}
