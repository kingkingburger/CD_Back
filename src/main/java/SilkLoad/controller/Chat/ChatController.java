package SilkLoad.controller.Chat;


import SilkLoad.dto.ChatMessageDto;
import SilkLoad.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate messageTemplate;
    private final ChatService chatService;
    //Client가 SEND할 수 있는 경로
    //stompConfig에서 설정한 applicationDestinationPrefixes와 @MessageMapping 경로가 병합됨
    //"/pub/chat/enter"
    //message 매개변수는 자동으로 바인딩 됨, messageMapping 어노테이션에 의해,
    //message 매개변수에는 @payload가 생략된 듯?. 아닐지도
    @MessageMapping(value = "/chat/enter")
    public void enter(@Payload ChatMessageDto message) {

        message.setCreateMessageTime(LocalDateTime.now());
        message.setMessage(message.getWriter() +  "님이 채팅방에 참여하였습니다.");
        messageTemplate.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);

    }


    @MessageMapping(value = "/chat/message")
    public void message( ChatMessageDto message ) {

        message.setCreateMessageTime(LocalDateTime.now());
        chatService.saveChatMessage(message);
        messageTemplate.convertAndSend("/sub/chat/room/" + message.getRoomId() , message);

    }


}
