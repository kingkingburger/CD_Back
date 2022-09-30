package SilkLoad.controller.Alarm;

import SilkLoad.SessionConst;
import SilkLoad.controller.Member.MyPageController;
import SilkLoad.entity.Members;
import SilkLoad.service.NotificationsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@Slf4j
public class NotificationController {

    private final NotificationsService notificationService;

    @GetMapping(value = "/subscribe", produces = "text/event-stream")
    @ResponseStatus(HttpStatus.OK)
    //@AuthenticationPrincipal 사용 보류
    public SseEmitter subscribe(
                                @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId,
                                HttpServletRequest request
                                ) {

        Members sessionMembers = MyPageController.getSessionMembers(request);
        log.info( "테스트 세션 ={}" ,sessionMembers.getName());
        SseEmitter sseEmitter = notificationService.subscribe(sessionMembers.getId(), lastEventId);

        return sseEmitter;

    }

}
