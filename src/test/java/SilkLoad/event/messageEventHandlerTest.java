package SilkLoad.event;

import SilkLoad.entity.Members;
import SilkLoad.entity.Product;
import SilkLoad.repository.MemberRepository;
import SilkLoad.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class messageEventHandlerTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    @Test
    @Transactional
    public void messageSenderTest() {

        //given
        Product product = productRepository.findById(1L).get();

        //when then
        applicationEventPublisher.publishEvent(product);



    }

}