package SilkLoad.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CHAT_MESSAGE_ID")
    Long Id;

    @OneToOne
    @JoinColumn(name="MEMBER_ID")
    Members members;

    @ManyToOne
    @JoinColumn(name="CHAT_ROOM_ID")
    ChatRoom chatRoom;

    String message;

    LocalDateTime localDateTime;

}
