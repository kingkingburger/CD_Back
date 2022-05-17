package SilkLoad.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
public class OrderFormDto {

    @NotNull
    private Long memberId;

    @NotNull
    private Long productId;

}
