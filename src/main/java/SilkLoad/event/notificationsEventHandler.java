package SilkLoad.event;

import SilkLoad.dto.NotificationsRequestDto;
import SilkLoad.repository.NotificationsRepository;
import SilkLoad.service.NotificationsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;


@Component
@RequiredArgsConstructor
@Slf4j
//메시지 핸들러
public class notificationsEventHandler {

    private final NotificationsService notificationsService;

    @TransactionalEventListener( phase = TransactionPhase.AFTER_COMMIT)
    @Async
    public void handleNotificationsSend(NotificationsRequestDto notificationsRequestDto) {

        notificationsService.send( notificationsRequestDto.getReceiver(),
                notificationsRequestDto.getSender(),
                notificationsRequestDto.getProductName(),
                notificationsRequestDto.getNotificationsType());

    }



}
