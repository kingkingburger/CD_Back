package SilkLoad.example.service;

import SilkLoad.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OrdersTest {

    @Autowired
    private OrderRepository orderRepository;


    @Test
    void findByMaxAuctionPriceTest() {

        Long byMaxAuctionPrice = orderRepository.findByProductIdMaxAuctionPrice(1L);
        System.out.println(byMaxAuctionPrice);
    }

}
