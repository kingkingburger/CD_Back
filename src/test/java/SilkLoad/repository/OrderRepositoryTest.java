package SilkLoad.repository;

import SilkLoad.dto.HomeProductDto;
import SilkLoad.entity.Orders;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import javax.transaction.Transactional;
import java.awt.print.Pageable;
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

    @Test
    @Transactional
    void findHomeProduct() {

        PageRequest pageable = PageRequest.of(0, 4);
        Page<HomeProductDto> homeProductDtoBySaleOrder = orderRepository.findHomeProductDtoBySaleOrder(pageable);
        System.out.println(homeProductDtoBySaleOrder.getContent());

    }
    @Test
    @Transactional
    void findHomeProductSearch() {
        PageRequest pageable = PageRequest.of(0, 4);
        Page<HomeProductDto> homeProductDtoBySaleOrder = orderRepository.findHomeProductDtoSearch( "테스트",pageable);
        System.out.println(homeProductDtoBySaleOrder.getContent().size());
    }
}