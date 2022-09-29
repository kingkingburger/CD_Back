package SilkLoad.repository;

import SilkLoad.entity.Members;
import SilkLoad.entity.Notifications;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class EmitterRepositoryImplTest {

    private EmitterRepository emitterRepository = new EmitterRepositoryImpl();
    private Long DEFAULT_TIMEOUT = 60L * 1000L * 60L;

    @Test
    @DisplayName("새로운 Emitter를 추가한다.")
    public void save() {

        //given
        Long memberId = 1L;
        String emitterId = memberId + "_" + System.currentTimeMillis();
        SseEmitter sseEmitter = new SseEmitter(DEFAULT_TIMEOUT);

        //when, then
        assertThatCode(() -> {
            emitterRepository.save(emitterId, sseEmitter);
        }).doesNotThrowAnyException();

    }

    @Test
    @DisplayName("이벤트 캐시에 저장")
    public void saveEventCache() {
        Long memberId = 1L;
        String eventCacheId = memberId + "_" + System.currentTimeMillis();
        Notifications 이벤트_발생 = Notifications.builder()
                .url("www.....")
                .receiver(new Members())
                .build();


        assertThatCode(() -> {
            emitterRepository.saveEventCache(eventCacheId, 이벤트_발생);
        }).doesNotThrowAnyException();

    }

    @Test
    @DisplayName("어떤 회원이 접속한 모든 Emitter를 찾는다.")
    public void findAllEmitterStartWithByMemberId() throws InterruptedException {


        Long memberId1 = 1L;
        String emitterId1 = memberId1 + "_" + System.currentTimeMillis();
        SseEmitter sseEmitter1 = new SseEmitter(DEFAULT_TIMEOUT);
        emitterRepository.save(emitterId1, sseEmitter1);

        Thread.sleep(100);
        String emitterId2 = memberId1 + "_" + System.currentTimeMillis();
        SseEmitter sseEmitter2 = new SseEmitter(DEFAULT_TIMEOUT);
        emitterRepository.save(emitterId2, sseEmitter2);

        Map<String, SseEmitter> allEmitterStartWithByMemberId = emitterRepository.findAllEmitterStartWithByMemberId(String.valueOf(memberId1));

        assertThat(allEmitterStartWithByMemberId.size()).as("사이즈는 2이어야 한다.")
                .isEqualTo(2);

    }

    @Test
    @DisplayName("어떤 회원이 접속한 모든 이벤트 캐시를 찾는다.")
    public void findAllEventCacheStartWithByMemberId() throws InterruptedException {

        Long memberId = 1L;
        String eventCacheId1 = memberId + "_" + System.currentTimeMillis();
        Notifications 이벤트_발생1 = Notifications.builder()
                .url("www.....")
                .receiver(new Members())
                .build();
        emitterRepository.saveEventCache(eventCacheId1, 이벤트_발생1);

        Thread.sleep(200);
        String eventCacheId2 = memberId + "_" + System.currentTimeMillis();
        Notifications 이벤트_발생2 = Notifications.builder()
                .url("www.....")
                .receiver(new Members())
                .build();
        emitterRepository.saveEventCache(eventCacheId2, 이벤트_발생2);

        Map<String, Object> allEventCacheStartWithByMemberId = emitterRepository.findAllEventCacheStartWithByMemberId(String.valueOf(memberId));

        assertThat(allEventCacheStartWithByMemberId.size())
                .isEqualTo(2);

    }

    @Test
    @DisplayName("ID를 통해 Emitter를 Repository에서 제거한다.")
    public void deleteById() {

        Long memberId1 = 1L;
        String emitterId1 = memberId1 + "_" + System.currentTimeMillis();
        SseEmitter sseEmitter1 = new SseEmitter(DEFAULT_TIMEOUT);
        emitterRepository.save(emitterId1, sseEmitter1);

        emitterRepository.deleteById(String.valueOf(emitterId1));

        assertThat(0).isEqualTo(emitterRepository.findAllEmitterStartWithByMemberId(String.valueOf(memberId1)).size());

    }

    @Test
    @DisplayName("저장된 모든 Emitter를 제거한다.")
    public void deleteAllEmitterStartWithId() throws InterruptedException {

        Long memberId1 = 1L;
        String emitterId1 = memberId1 + "_" + System.currentTimeMillis();
        SseEmitter sseEmitter1 = new SseEmitter(DEFAULT_TIMEOUT);
        emitterRepository.save(emitterId1, sseEmitter1);

        Thread.sleep(100);

        String emitterId2 = memberId1 + "_" + System.currentTimeMillis();
        SseEmitter sseEmitter2 = new SseEmitter(DEFAULT_TIMEOUT);
        emitterRepository.save(emitterId2, sseEmitter2);

        emitterRepository.deleteAllEmitterStartWithId(String.valueOf(memberId1));

        assertThat(emitterRepository.findAllEmitterStartWithByMemberId(String.valueOf(memberId1)).size()).isEqualTo(0);

    }

    @Test
    @DisplayName("수신한 이벤트를 캐시에 저장한다.")
    public void deleteAllEventCacheStartWithId() throws InterruptedException {

        Long memberId = 1L;
        String eventCacheId1 = memberId + "_" + System.currentTimeMillis();
        Notifications 이벤트_발생1 = Notifications.builder()
                .url("www.....")
                .receiver(new Members())
                .build();
        emitterRepository.saveEventCache(eventCacheId1, 이벤트_발생1);

        Thread.sleep(200);

        String eventCacheId2 = memberId + "_" + System.currentTimeMillis();
        Notifications 이벤트_발생2 = Notifications.builder()
                .url("www.....")
                .receiver(new Members())
                .build();

        emitterRepository.saveEventCache(eventCacheId2, 이벤트_발생2);

        emitterRepository.deleteAllEventCacheStartWithId(String.valueOf(memberId));

        assertThat(emitterRepository.findAllEventCacheStartWithByMemberId(String.valueOf(memberId)).size()).isEqualTo(0);


    }


}
