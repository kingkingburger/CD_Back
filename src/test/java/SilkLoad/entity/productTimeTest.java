package SilkLoad.entity;

import SilkLoad.entity.ProductEnum.ProductTime;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

@Slf4j
class productTimeTest {



    @Test
    void enumTest() {

        ProductTime x = ProductTime.ONE_DAY;
        Assertions.assertThat(x).isEqualTo(ProductTime.ONE_DAY);
        System.out.println(x.getDescription());

    }

    @Test
    void LocalTimeTest() {

        LocalDateTime now = LocalDateTime.now();

        if (now.isBefore(now.plusDays(2))) {
            log.info("시간 테스트 통과={}", now.plusDays(2));
        }



    }


}