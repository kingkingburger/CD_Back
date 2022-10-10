package SilkLoad.dto;

import lombok.*;
import org.json.JSONObject;


@Builder
@Getter
@AllArgsConstructor
public class NaverProductDto {

    private String title;
    private String link;
    private String image;
    private Integer lprice;

    public NaverProductDto(JSONObject itemJson) {
        this.title = itemJson.getString("title");
        this.link = itemJson.getString("link");
        this.image = itemJson.getString("image");
        this.lprice = itemJson.getInt("lprice");
    }
}
