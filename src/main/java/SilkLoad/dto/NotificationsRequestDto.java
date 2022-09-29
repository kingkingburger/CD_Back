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
    String sender;
    String productName;
    NotificationsType notificationsType;

    public static NotificationsRequestDto create(Members member, Product product) {
        return NotificationsRequestDto.builder()
                .receiver(product.getMembers())
                .sender(member.getName())
                .productName(product.getName())
                .notificationsType(NotificationsType.buyNow)
                .build();
    }

}
