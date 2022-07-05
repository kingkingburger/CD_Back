package SilkLoad.controller;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import SilkLoad.controller.Shop.ShopController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

/**
 * @WebMvcTest - JPA 기능은 동작하지 않는다.
 * - 여러 스프링 테스트 어노테이션 중, Web(Spring MVC)에만 집중할 수 있는 어노테이션
 * - @Controller, @ControllerAdvice 사용 가능
 * - 단, @Service, @Repository등은 사용할 수 없다.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class ShopControllerTest {
    /**
     * 웹 API 테스트할 때 사용
     * 스프링 MVC 테스트의 시작점
     * HTTP GET,POST 등에 대해 API 테스트 가능
     * */
    // 아래 BeforeEach 에서 mockMvc 객체를 초기화 하는 것과 같다.
    @Autowired
    public MockMvc mvc;

    @Test
    @DisplayName("Shop컨트롤러 테스트")
    void HelloControllerDtoTest() throws Exception {
        //given
        String name = "Test";
        //then
        mvc.perform(get("/shop")
                        .param("first", name)
                        .param("second", name)
                        .param("third", name)
                )
                .andExpect(status().isOk());
    }
}
