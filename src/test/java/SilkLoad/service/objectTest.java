package SilkLoad.service;

import SilkLoad.dto.LoginFormDto;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class objectTest {

    @Test
    void testObject() {

        HashMap<Long, LoginFormDto> objectObjectHashMap = new HashMap<>();
        LoginFormDto loginFormDto = new LoginFormDto();
        loginFormDto.setLoginId("준호");
        objectObjectHashMap.put(1L, loginFormDto);
        objectObjectHashMap.forEach( (x, y) -> {

            y.setLoginId("민호");

        });

        log.info("테스트 map {}", objectObjectHashMap.get(1L));


    }

    @Test
    void testMethod(LoginFormDto  loginFormDto ){

        loginFormDto.setLoginId("asd");
    }


}
