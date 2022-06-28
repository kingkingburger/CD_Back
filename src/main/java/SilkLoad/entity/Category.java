package SilkLoad.entity;


import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@NamedEntityGraph(name = "CategoryWithProduct", attributeNodes = @NamedAttributeNode("productList"))
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CATEGORY_ID")
    private Long id;
    private String first;
    private String second;
    private String third;

//    @ToString.Exclude
    @OneToMany(mappedBy = "category")
    private List<Product> productList = new ArrayList<Product>();

    @Builder
    public Category(String first, String second, String third) {

        this.first = first;
        this.second = second;
        this.third = third;
        this.productList = new ArrayList<Product>();
    }

}
