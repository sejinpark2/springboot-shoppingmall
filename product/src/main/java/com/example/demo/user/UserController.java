package com.example.demo.user;

import com.example.demo.core.security.JwtTokenProvider;
import com.example.demo.core.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody @Valid UserRequest.JoinDTO requestDTO, Error error) {

        userService.join(requestDTO);

        return ResponseEntity.ok( ApiUtils.success(null) );
    }

    @PostMapping("/check")
    public ResponseEntity<?> check(@RequestBody @Valid UserRequest.JoinDTO requestDTO, Error error) {
        userService.checkEmail(requestDTO.getEmail());
        return ResponseEntity.ok( ApiUtils.success(null) );
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserRequest.JoinDTO requestDTO, Error error, HttpServletResponse response) {
        String jwt = userService.login(requestDTO);
        System.out.println(jwt);

        // JWT 토큰에서 "Bearer " 제거
        if (jwt.startsWith("Bearer ")) {
            jwt = jwt.substring(7);
        }

        // JWT를 쿠키에 저장
        Cookie cookie = new Cookie("jwt", jwt);
        cookie.setPath("/"); // 쿠키의 유효 범위 설정
        // cookie.setHttpOnly(true); // JavaScript에서 쿠키에 접근하지 못하도록 설정
        response.addCookie(cookie);


        return ResponseEntity.ok().header(JwtTokenProvider.HEADER, jwt)
                .body(ApiUtils.success(null));
    }
}

