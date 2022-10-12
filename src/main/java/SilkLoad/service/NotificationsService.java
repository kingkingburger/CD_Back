package SilkLoad.service;

import SilkLoad.dto.NotificationsResponseDto;
import SilkLoad.entity.Members;
import SilkLoad.entity.NotificationsEnum.NotificationsType;
import SilkLoad.entity.Notifications;
import SilkLoad.repository.EmitterRepositoryImpl;
import SilkLoad.repository.MemberRepository;
import SilkLoad.repository.NotificationsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Not;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationsService {

    private final EmitterRepositoryImpl emitterRepository;
    private final NotificationsRepository notificationsRepository;

    private Long DEFAULT_TIMEOUT = 60L * 1000L * 60L;

    @Transactional
    public void readCheck(String memberId) {

        Map<String, Object> allEventCacheStartWithByMemberId = emitterRepository.findAllEventCacheStartWithByMemberId(memberId);
        allEventCacheStartWithByMemberId.forEach( ( id,  value ) -> {

            Notifications notifications = (Notifications) value;
            notifications.setIsRead(true);
            allEventCacheStartWithByMemberId.put(id, notifications);

            }
        );
        List<Notifications> byReceiver_id = notificationsRepository.findByReceiver_Id(Long.valueOf(memberId));
        byReceiver_id.forEach( (notifications) -> {
            notifications.setIsRead(true);
        } );
        notificationsRepository.saveAll(byReceiver_id);

    }

    public SseEmitter subscribe(Long memberId, String lastEventId) {

        String emitterId = makeTimeIncludeId(memberId);
        SseEmitter emitter = emitterRepository.save(emitterId, new SseEmitter(DEFAULT_TIMEOUT));
        //만료된 경우 자동으로 repository에서 삭제하는 콜백 등록
        emitter.onCompletion( () -> emitterRepository.deleteById(emitterId) );
        emitter.onTimeout(() -> emitterRepository.deleteById(emitterId));

        //505 에러를 방지하기 위한 더미 이벤트 전송
        //등록 후 SseEmitter 유효시간동안 어느 데이터도 전송되지 않는 다면 503 에러를 발생시키므로 이것에 대한 방지로 더이 이벤트 발생
        String eventId = makeTimeIncludeId(memberId);
        sendNotification(emitter, eventId, emitterId, "EventStream Created. [userId=" + memberId + "]");

        // 미수신 event 목록 확인 후 전송
        if (hasLostData(lastEventId)) {
            sendLostData(lastEventId, memberId, emitterId, emitter);
        } else {
            PageRequest pageable = PageRequest.of(0, 15, Sort.by("createdDate").descending());
            List<Notifications> content = notificationsRepository.findByReceiver_Id(memberId, pageable).getContent();
            content.forEach( (notifications -> {
                sendNotification(emitter, eventId, emitterId, NotificationsResponseDto.create(notifications));
            }) );
        }
        //sse 연결
        return emitter;
    }

    private boolean hasLostData(String lastEventId) {

        return !lastEventId.isEmpty();
    }

    @Transactional
    public void send(Members receiver,
                     String sender,
                     String productName,
                     NotificationsType notificationsType
                     ) {

        String message = createMessage(sender ,productName, notificationsType);
        String url = createUrl(notificationsType);

        Notifications notifications = createNotifications(receiver, message, url);
        Notifications savedNotifications = notificationsRepository.save(notifications);

        String receiverId = String.valueOf(receiver.getId());
        String eventId = receiverId + "_" + System.currentTimeMillis();
        Map<String, SseEmitter> emitters = emitterRepository.findAllEmitterStartWithByMemberId(receiverId);
        emitters.forEach(
                (key, emitter) -> {
                    emitterRepository.saveEventCache(key, savedNotifications);
                    sendNotification(emitter, eventId, key, NotificationsResponseDto.create(savedNotifications)  );
                }
        );

    }
    public String createMessage(String sender, String productName, NotificationsType notificationsType) {
        //memberId = 구매자
        String message = "";


        if (notificationsType == NotificationsType.buyNow ||
                notificationsType == NotificationsType.buyAuction) {
            message = sender + "님이 " + productName + "을(를) " +  notificationsType.getDescription() + "를 요쳥합니다.";
        } else if (notificationsType == NotificationsType.completion ) {
            message = sender + "님의 " + productName + " 주문이 " + notificationsType.getDescription() + " 되었습니다." ;
        } else if (notificationsType == NotificationsType.trade) {
            message = sender + "님의 " + productName + " 주문이 " + notificationsType.getDescription() + " 되었습니다.";
        } else if (notificationsType == NotificationsType.cancle) {
            message = sender + "님의 " + productName + " 주문이" + notificationsType.getDescription() + " 되었습니다.";
        }

        return message;
    }

    public String createUrl(NotificationsType notificationsType) {

        String url = "";

        if (notificationsType == NotificationsType.buyNow ||
                notificationsType == NotificationsType.buyAuction) {
            url = "/members/myPage/saleOrders";
        } else if (notificationsType == NotificationsType.completion ||
                    notificationsType == NotificationsType.trade ||
                    notificationsType == NotificationsType.cancle) {
            url = "/members/myPage/purchaseOrders";
        }
        return url;
    }


    private Notifications createNotifications(Members receiver,String message, String url) {
        Notifications notifications =Notifications .builder()
                .receiver(receiver)
                .message(message)
                .url(url)
                .build();
        return notifications;
    }


    private String makeTimeIncludeId(Long memberId) {
        return memberId + "_" + System.currentTimeMillis();
    }

    private void sendNotification(SseEmitter emitter, String eventId, String emitterId, Object data) {
        try {
            emitter.send(SseEmitter.event()
                            .id(eventId)
                            .data(data, MediaType.APPLICATION_JSON));

        } catch (IOException exception) {
            emitterRepository.deleteById(emitterId);
        }
    }

    private void sendLostData(String lastEventId,Long memberId, String emitterId, SseEmitter emitter) {

        Map<String, Object> eventCaches = emitterRepository.findAllEventCacheStartWithByMemberId(String.valueOf(memberId));
        eventCaches.entrySet().stream()
                .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0 )
                .forEach(entry -> sendNotification(emitter, entry.getKey(), emitterId, entry.getValue()));
    }


}
