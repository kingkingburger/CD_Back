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
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CATEGORY_ID")
    private Long id;
    private String first;
    private String second;

    @ToString.Exclude
    @OneToMany(mappedBy = "category")
    private List<Product> productList = new ArrayList<Product>();

    @Builder
    public Category(String first, String second) {

        this.first = first;
        this.second = second;
        this.productList = new ArrayList<Product>();
    }

}
