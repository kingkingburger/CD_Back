package SilkLoad.entity;


import SilkLoad.entity.ChatRoomEnum.ChatRoomType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CHAT_ROOM_ID")
    Long id;

    String name;

    @OneToOne
    @JoinColumn(name= "PRODUCT_ID")
    Product product;

    @OneToOne
    @JoinColumn(name= "MEMBER_ID")
    Members membersBuyer;

    @Enumerated(EnumType.STRING)
    ChatRoomType chatRoomType;

    LocalDateTime createDateTime;


}
