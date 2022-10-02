package SilkLoad.repository;

import SilkLoad.entity.Orders;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderRepositoryTest {

    @Autowired
    OrderRepository orderRepository;

    @Test
    @Transactional
    void findByMemberBuyer_IdAAndProduct_Id() {

        boolean empty = orderRepository.findByMemberBuyer_IdAndProduct_Id(10000L, 1L).isEmpty();
        Assertions.assertThat(empty).isTrue();

    }
}