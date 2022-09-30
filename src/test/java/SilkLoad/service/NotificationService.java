package SilkLoad.service;

import SilkLoad.dto.NotificationsRequestDto;
import SilkLoad.entity.Members;
import SilkLoad.entity.NotificationsEnum.NotificationsType;
import SilkLoad.entity.Product;
import SilkLoad.entity.UserRoleEnum.Role;
import SilkLoad.repository.MemberRepository;
import SilkLoad.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

@SpringBootTest
public class NotificationService {
    @Autowired
    NotificationsService notificationService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ProductRepository productRepository;

    @Test
    @DisplayName("알림 구독을 진행한다.")
    public void subscribe() throws Exception {
        //given

        //when, then
        assertThatCode( () -> {
            notificationService.subscribe( 1L, "");
        } ).doesNotThrowAnyException();
    }

    @Test
    @Transactional
    @DisplayName("알림 메세지를 전송한다.")
    public void send() {

        Members 강준호 = Members.builder()
                .name("강준호23424544566")
                .role(Role.GUEST)
                .email("naver.com23415646")
                .password("1234")
                .build();

        Members save = memberRepository.save(강준호);

        //given
        notificationService.subscribe(save.getId(), "");
        //when, then
        assertThatCode( () -> {
            notificationService.send( save, "준호","과자",NotificationsType.buyNow );
        }).doesNotThrowAnyException();
    }

    void exceptionTest() throws Exception {
        throw new Exception();
    }

    @Test
    @Transactional
    public void requestDto() {

        Members members = memberRepository.findById(1L).get();
        Product product = productRepository.findById(1L).get();

        NotificationsRequestDto.create(members,product);

    }


}
