package SilkLoad.entity;

import SilkLoad.entity.UserRoleEnum.Role;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.BatchSize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@BatchSize(size = 100)

@Slf4j
public class Members implements UserDetails{


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


    //, fetch = FetchType.EAGER (원래 이거 였음)
    @OneToMany(mappedBy = "members")
    private List<Product> productList = new ArrayList<Product>();

    @OneToMany(mappedBy = "receiver")
    private List<Notifications> NotificationsList = new ArrayList<Notifications>();


    @Builder
    public Members(String name,
                   String password,
                   String email,
                   String picture,
                   Role role,
                   Integer ranks,
                   Integer numberPurchase) {
        this.loginId = email;
        this.password = password;
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
    
    //로그인 할 때 권한 부여("ROLE_GUEST")로
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> auth = new ArrayList<GrantedAuthority>();
        auth.add(new SimpleGrantedAuthority("ROLE_GUEST"));

        return auth;
    }

    @Override
    public String getUsername() {
        return this.loginId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}