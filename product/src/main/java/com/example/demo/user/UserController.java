package com.example.demo.user;

import com.example.demo.core.security.CustomUserDetails;
import com.example.demo.core.security.JwtTokenProvider;
import com.example.demo.core.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody @Valid UserRequest.JoinDTO requestDTO, Error error) {

        userService.join(requestDTO);

        return ResponseEntity.ok( ApiUtils.success(null) );
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserRequest.LoginDTO requestDTO, Error error, HttpServletResponse response) {
        String jwt = userService.login(requestDTO);

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

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        // 로그아웃 시에는 토큰을 무효화하고, 쿠키에서도 제거해야 합니다.
        Cookie cookie = new Cookie("jwt", null);
        cookie.setPath("/");
        cookie.setMaxAge(0); // 쿠키의 유효 기간을 0으로 설정하여 즉시 만료시킵니다.
        response.addCookie(cookie);

        // 추가적으로 필요한 로그아웃 처리를 수행합니다.
        // 세션 무효화, 로그아웃 이벤트 기록 등

        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUserInfo(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        if (customUserDetails != null) {
            User userInfo = customUserDetails.getUser();
            if (userInfo != null) {
                return ResponseEntity.ok(userInfo); // 현재 사용자 정보를 반환
            } else {
                // 사용자 정보가 없는 경우 적절한 HTTP 상태 코드와 메시지 반환
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
        } else {
            // 인증되지 않은 경우 적절한 HTTP 상태 코드와 메시지 반환
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not authenticated");
        }
    }
}

