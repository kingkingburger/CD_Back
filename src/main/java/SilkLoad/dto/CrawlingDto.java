package SilkLoad.dto;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class CrawlingDto {

    private Long id; // PK

    private String category;
    private String name;
    private String price;
    private String link;
    private String img_link;

    @Builder
    public CrawlingDto(String name,
                        String price, String link, String img_link,
                       String category) {
        this.category=category;
        this.name=name;
        this.price=price;
        this.link=link;
        this.img_link=img_link;
    }
}
