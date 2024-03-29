package SilkLoad.dto;

import SilkLoad.entity.Notifications;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class NotificationsResponseDto {

    String message;

    String url;

    Boolean isRead;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    LocalDateTime createdDateTime;


    public static NotificationsResponseDto create(Notifications notifications) {

        return NotificationsResponseDto.builder()
                .message(notifications.getMessage())
                .url(notifications.getUrl())
                .isRead(notifications.getIsRead())
                .createdDateTime(notifications.getCreatedDate())
                .build();

    }
}
