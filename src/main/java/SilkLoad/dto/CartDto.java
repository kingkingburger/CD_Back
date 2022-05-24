package SilkLoad.dto;


import SilkLoad.entity.Members;
import SilkLoad.entity.Product;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Data
@ToString
@NoArgsConstructor
public class CartDto {

    private Long id; // PK

    private Members memberid;  //구매자의 id

    private Product productid; //물품의 id


}
