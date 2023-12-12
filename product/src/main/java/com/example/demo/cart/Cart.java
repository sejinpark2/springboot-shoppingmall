package com.example.demo.cart;

import com.example.demo.option.Option;
import com.example.demo.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.procedure.spi.ParameterRegistrationImplementor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "cart_tb",
        indexes = {
            @Index(name = "cart_user_id_idx" , columnList = "user_id"),
            @Index(name = "cart_option_id_idx" , columnList = "option_id")
        },
        uniqueConstraints = {
            @UniqueConstraint(name = "uk_cart_option_user", columnNames = {"user_id", "Option_id"})
        })
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ** user별로 카트에 묶임
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    private Option option;

    @Column(nullable = false)
    private Long quantity;

    @Column(nullable = false)
    private Long price;

    @Builder
    public Cart(Long id, User user, Option option, Long quantity, Long price) {
        this.id = id;
        this.user = user;
        this.option = option;
        this.quantity = quantity;
        this.price = price;
    }

    public void update(Long quantity, Long price) {
        this.quantity = quantity;
        this.price = price;
    }
}
