package com.example.demo.kakao;

import com.example.demo.user.User;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;

public class KakaoRequest {

    @Getter
    @Setter
    public static class KakaoJoinDTO {
        private String email;
        private String username;

        public User toEntity() {
            return User.builder()
                    .email(email)
                    .username(username)
                    .roles(Collections.singletonList("ROLE_USER"))
                    .isKakaoUser(true)
                    .build();
        }
    }

    @Getter
    @Setter
    public static class KakaoLoginDTO {
        private String email;

        public User toEntity() {
            return User.builder()
                    .email(email)
                    .isKakaoUser(true)
                    .build();
        }
    }
}
