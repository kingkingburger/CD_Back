package SilkLoad.entity;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Data
@AllArgsConstructor
public class Crawling {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // PK

    private String site_name;
    private String first;
    private String second;
    private String third;
    private String name;
    private String price;
    private String link;
    private String img_link;

}
