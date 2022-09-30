package SilkLoad.entity;

import SilkLoad.entity.NotificationsEnum.NotificationsType;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Notifications {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Notifications_ID")
    private Long id;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private Boolean isRead;

    @Column(nullable = false)
    private LocalDateTime createdDate;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID", nullable = false) //Product 테이블에 있는 것을 매핑 , nullable을 false로 해서 내부 join으로 변경
    private Members receiver;

    @Builder
    public Notifications(  Members receiver, String message, String url) {

        this.message = message;
        this.url = url;
        this.isRead = false;
        this.createdDate = LocalDateTime.now();
        this.receiver = receiver;
    }

}
