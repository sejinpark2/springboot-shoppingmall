package com.example.demo.user;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor

@Entity

@Table(name="user_tb")
public class User{

    // ** 해당 필드를 PK로 지정
    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false, unique = true)
    private String email;

    @Column(length = 256, nullable = true)
    private String password;

    @Column(length = 11)
    private String phoneNumber;

    @Column(length = 30)
    @Convert(converter = StringArrayConverter.class)
    private List<String> roles = new ArrayList<>();


    // * 주로 생성자의 인자가 많거나, 인자를 선택적으로 지정 해야 하는 경우에 사용.
    @Builder
    public User(Long id, String email, String password, String phoneNumber, List<String> roles) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.roles = roles;
    }
}

