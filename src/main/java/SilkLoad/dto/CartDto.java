package SilkLoad.dto;


import SilkLoad.entity.Members;
import SilkLoad.entity.Product;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class CartDto {
    @NotNull
    private Long id; // PK
    @NotNull
    private Long memberid;  //구매자의 id
    @NotNull
    private Long productid; //물품의 id

}
