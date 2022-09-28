package SilkLoad.controller;

import SilkLoad.SessionConst;
import SilkLoad.controller.Alarm.NotificationController;
import SilkLoad.dto.MemberFormDto;
import SilkLoad.entity.Members;
import SilkLoad.entity.UserRoleEnum.Role;
import SilkLoad.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class NotificationControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    NotificationController notificationController;

    MockHttpSession session;

    @Test
    @DisplayName("SSE에 연결 시작")
    /**
     * 테스트 실패..... session 가져오기 실패
     */
    @Transactional
    public void subscribe() throws Exception {

        Members 강준호 = Members.builder()
                .name("강준호6")
                .role(Role.GUEST)
                .email("naver.com7")
                .password("1234")
                .build();
        강준호.setId(1L);

        Members save = memberRepository.save(강준호);

        session = new MockHttpSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, save);
        mockMvc.perform(get("/subscribe")
                .session(session));

    }




}
