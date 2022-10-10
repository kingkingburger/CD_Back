package SilkLoad.repository;

import SilkLoad.entity.Notifications;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationsRepository extends JpaRepository<Notifications, Long> {

    Slice<Notifications> findByReceiver_Id (Long id, Pageable pageable);

    List<Notifications> findByReceiver_Id (Long id);



}
