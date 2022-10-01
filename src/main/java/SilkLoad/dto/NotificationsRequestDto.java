package SilkLoad.dto;

import SilkLoad.entity.Members;
import SilkLoad.entity.NotificationsEnum.NotificationsType;
import SilkLoad.entity.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class NotificationsRequestDto {

    Members receiver;
    String Sender;
    String productName;
    NotificationsType notificationsType;

    /**
     *
     * @param receiver: receiver
     *
     * @param notificationsType
     * @return
     */
    public static NotificationsRequestDto create(Members receiver,String sender ,String productName, NotificationsType notificationsType) {
        return NotificationsRequestDto.builder()
                .receiver(receiver)
                .Sender(sender)
                .productName(productName)
                .notificationsType(notificationsType)
                .build();
    }

}
