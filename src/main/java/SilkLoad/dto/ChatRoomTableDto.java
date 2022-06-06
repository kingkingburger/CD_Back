package SilkLoad.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ChatRoomTableDto {

    Long id;
    String name;
    String productName;
    String memberBuyer;

}
