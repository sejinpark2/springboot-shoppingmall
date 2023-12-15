package com.example.demo.kakao;

import com.example.demo.kakao.KakaoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RequiredArgsConstructor
@Controller

public class KakaoController {

    private final KakaoService kakaoService;

    @GetMapping("/oauth/kakao")
    public String login(@RequestParam(value = "code", required = false) String code, HttpServletRequest req) throws JsonProcessingException {
        if (code == null) {
            return "redirect:" + kakaoService.getKakaoLogin();
        }
        try {
            // 수정
            String accessToken = kakaoService.getKakaoToken(code, req.getSession());
            KakaoUserInforDto userInformation = kakaoService.getKakaoInfo(accessToken);
            // 경로 수정
            return "redirect:/choseJoin.html";
        } catch (Exception e) {
            return "error_page";
        }
    }

    @PostMapping("/kakao/join")
    public ResponseEntity<String> join(HttpServletRequest req) {
        try {
            kakaoService.join(req.getSession());
            return ResponseEntity.ok("회원 가입이 완료되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원 가입 중 오류가 발생했습니다.");
        }
    }

    @PostMapping("/kakao/logout")
    public ResponseEntity<String> logout(HttpServletRequest req) {
        try {
            kakaoService.logout(req.getSession());
            return ResponseEntity.ok("로그아웃되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}



