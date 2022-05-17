package SilkLoad.entity;

import SilkLoad.dto.ProductFormDto;
import SilkLoad.dto.ProductRecordDto;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Members {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(nullable = false, unique = true)
    private String loginId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    private Integer ranks;

    private Integer numberPurchase;

    private LocalDate createDate = LocalDate.now();

//    @BatchSize(size = 100)
    @ToString.Exclude
    @OneToMany(mappedBy = "members", fetch = FetchType.LAZY)
    private List<Product> productList = new ArrayList<Product>();

    @Builder
    public Members(Long id, String loginId, String name, String password, Integer ranks, Integer numberPurchase) {

        this.id = id;
        this.loginId = loginId;
        this.name = name;
        this.password = password;
        this.ranks = ranks;
        this.numberPurchase = numberPurchase;
        this.createDate = LocalDate.now();
        this.productList = new ArrayList<Product>();

    }

}