package com.example.demo.user;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter
@NoArgsConstructor
@Entity
@Table(name="user_tb")
public class User{

    // ** 해당 필드를 PK로 지정
    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 이메일(아이디)
    @Column(length = 30, nullable = false, unique = true)
    private String email;

    // 비밀번호
    private String password;

    // 이름
    private String username;

    // 휴대폰 번호
    private String phoneNumber;

    // 주소
    private String address;

    @Column(length = 30)
    @Convert(converter = StringArrayConverter.class)
    private List<String> roles = new ArrayList<>();


    // * 주로 생성자의 인자가 많거나, 인자를 선택적으로 지정 해야 하는 경우에 사용.
    @Builder
    public User(Long id, String email, String password, String username, String phoneNumber,String address, List<String> roles) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.roles = roles;
    }
}

