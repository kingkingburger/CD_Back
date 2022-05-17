package SilkLoad.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
public class ProductImageRecordDto {

    private String uploadFileName;  //회원이 올린 파일 이름
    private String storeFileName;   //실제 저장 될 파일 이름

}
