package SilkLoad.controller.Product;

import SilkLoad.dto.NaverProductDto;
import SilkLoad.dto.NaverRequestVariableDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;


@Slf4j
@RequiredArgsConstructor
@RestController
public class NaverProductController {


    @PostMapping("/response-body-json-test")//{ "query": "준호", "display": 25, "start": 2, "sort" : "ㅁㄴㅁ"}
    public String  requestBodyJsonV1(@RequestBody NaverRequestVariableDto dto) {


        String url = "https://openapi.naver.com/";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.set("X-Naver-Client-Id", "COuB5FOeyoiu2FsY3LXZ");
        headers.set("X-Naver-Client-Secret", "qxx86y7sg4");

        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .path("v1/search/shop.json")
                .queryParam("query", dto.getQuery())
                .queryParam("display", dto.getDisplay())
                .queryParam("start", dto.getStart())
                .queryParam("sort", dto.getSort())
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
        log.info("result ={}", result.getBody());
        return result.getBody();

    }

}
