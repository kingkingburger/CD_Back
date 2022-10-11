package SilkLoad.controller.Alarm;

import SilkLoad.SessionConst;
import SilkLoad.controller.Member.MyPageController;
import SilkLoad.dto.MemberSessionDto;
import SilkLoad.entity.Members;
import SilkLoad.service.NotificationsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationsService notificationService;

    @GetMapping(value = "/subscribe", produces = "text/event-stream")
    @ResponseStatus(HttpStatus.OK)
    //@AuthenticationPrincipal 사용 보류
    public SseEmitter subscribe(
                                @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId,
                                HttpServletRequest request
                                ) {

        MemberSessionDto sessionMembers = MyPageController.getSessionMembers(request);
        SseEmitter sseEmitter = notificationService.subscribe(sessionMembers.getId(), lastEventId);
        return sseEmitter;

    }
    @GetMapping(value = "/read")
    public void read(HttpServletRequest request) {

        MemberSessionDto sessionMembers = MyPageController.getSessionMembers(request);
        notificationService.readCheck( sessionMembers.getId().toString() );
        

    }





}
