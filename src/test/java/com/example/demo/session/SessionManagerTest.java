package com.example.demo.session;

import com.example.demo.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

class SessionManagerTest {

    SessionManager sessionManager = new SessionManager();
    @Test
    void createdSession() {

        MockHttpServletResponse response = new MockHttpServletResponse();
        //세션 생성
        Member params = Member.builder()
                .memberLoginid("won")
                .memberPassword("1234")
                .memberName("minho")
                .memberPhone("01035862056")
                .build();

        sessionManager.createdSession(params, response);

        //요청에 응답 쿠키 저장(웹 브라우저가 쿠키를 만들어서 전송)
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setCookies(response.getCookies());

        //세션조회
        Object result = sessionManager.getSession(request);
        Assertions.assertThat(result).isEqualTo(params);

        //세션만료
        sessionManager.expire(request);
        Object expire = sessionManager.getSession(request);
        Assertions.assertThat(expire).isNull();
    }


}