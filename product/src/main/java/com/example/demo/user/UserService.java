package com.example.demo.user;

import com.example.demo.core.error.exception.Exception500;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import com.example.demo.user.UserRequest;
import com.example.demo.core.error.exception.Exception400;
import com.example.demo.core.error.exception.Exception401;
import com.example.demo.core.security.CustomUserDetails;
import com.example.demo.core.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public void join(UserRequest.JoinDTO joinDTO) {
        // 동일한 이메일 존재 하는 지 확인
        checkEmail(joinDTO.getEmail());

        String encodedPassword = passwordEncoder.encode(joinDTO.getPassword());

        joinDTO.setPassword(encodedPassword);

        try {
            userRepository.save(joinDTO.toEntity());

        }catch (Exception e){
            throw new Exception500(e.getMessage());
        }
    }

    @Transactional
    public String login(UserRequest.JoinDTO requestDto) {
        try {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                    = new UsernamePasswordAuthenticationToken(requestDto.getEmail(), requestDto.getPassword());

            // anonymousUser = 비인증
            Authentication authentication = authenticationManager.authenticate(
                    usernamePasswordAuthenticationToken
            );
            // ** 인증 완료 값을 받아 온다.
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            // ** 토큰 발급.
            return JwtTokenProvider.create(customUserDetails.getUser());

        } catch (Exception e) {
            throw new Exception401("인증 되지 않음.");
        }
    }

    public void checkEmail(String email) {
        // 동일한 이메일이 있는지 확인.
        Optional<User> users = userRepository.findByEmail(email);
        if (users.isPresent()) {
            throw new Exception400("이미 존재 하는 이메일 입니다. : " + email);
        }
    }
}