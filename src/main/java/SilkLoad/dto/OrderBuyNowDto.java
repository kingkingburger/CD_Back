package SilkLoad.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
@Builder
public class OrderBuyNowDto {

    @NotNull
    private Long memberId;

    @NotNull
    private Long productId;



}
