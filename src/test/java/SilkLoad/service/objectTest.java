package SilkLoad.service;

import SilkLoad.dto.LoginFormDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class objectTest {

    @Test
    void testObject() {

        LoginFormDto loginFormDto = new LoginFormDto();

        testMethod(loginFormDto);

        assertThat(loginFormDto.getLoginId()).isEqualTo("asd");


    }

    void testMethod(LoginFormDto  loginFormDto ){

        loginFormDto.setLoginId("asd");
    }


}
