package SilkLoad.repository;

import SilkLoad.entity.Notifications;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationsRepository extends JpaRepository<Notifications, Long> {

    Slice<Notifications> findByReceiver_Id (Long id, Pageable pageable);


}
