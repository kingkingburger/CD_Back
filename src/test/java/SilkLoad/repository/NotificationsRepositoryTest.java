package SilkLoad.repository;

import SilkLoad.entity.Notifications;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;

import java.awt.print.Pageable;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NotificationsRepositoryTest {

    @Autowired
    NotificationsRepository notificationsRepository;


    @Test
    public void findByMember_Id() {

        PageRequest of = PageRequest.of(1, 2, Sort.by("createdDate").descending() );
        Slice<Notifications> byReceiver_id = notificationsRepository.findByReceiver_Id (1L, of);
        List<Notifications> content = byReceiver_id.getContent();
        content.forEach( (x) -> {
            System.out.println(x.getCreatedDate());
        } );

    }
}