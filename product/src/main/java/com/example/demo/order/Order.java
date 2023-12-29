package com.example.demo.order;


import com.example.demo.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "order_tb", indexes = {
        @Index(name = "order_user_id_idx", columnList = "user_id")
})
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDateTime createDate; // 구매 날짜

    @Builder
    public Order(Long id, User user) {
        this.id = id;
        this.user = user;
    }
}
