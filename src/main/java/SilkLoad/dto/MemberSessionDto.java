package SilkLoad.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberSessionDto {

    Long id;
    String loginId;
    String name;


}
