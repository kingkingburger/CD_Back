package SilkLoad.dto;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class NaverRequestVariableDto {

    String query;
    Integer display;
    Integer start;
    String sort;

}
