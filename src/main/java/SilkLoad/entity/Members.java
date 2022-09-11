package SilkLoad.entity;

import SilkLoad.entity.UserRoleEnum.Role;
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
@BatchSize(size = 100)
public class Members {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(nullable = false, unique = true)
    private String loginId;

    @Column
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false) // OAuth2로 들어오는 정보
    private String email;

    @Column
    private String picture; // // OAuth2로 들어오는 정보

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role; // // OAuth2로 들어오는 정보

    private Integer ranks;

    private Integer numberPurchase;

    private LocalDate createDate = LocalDate.now();


    @OneToMany(mappedBy = "members", fetch = FetchType.EAGER)
    private List<Product> productList = new ArrayList<Product>();

    @Builder
    public Members(String name,
                   String email,
                   String picture,
                   Role role,
                   Integer ranks,
                   Integer numberPurchase) {
        this.loginId = email;
        this.password = email;
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
        this.ranks = ranks;
        this.numberPurchase = numberPurchase;
        this.createDate = LocalDate.now();
        this.productList = new ArrayList<Product>();
    }

    public Members update(String name, String picture) {
        this.name = name;
        this.picture = picture;
        return this;
    }
}