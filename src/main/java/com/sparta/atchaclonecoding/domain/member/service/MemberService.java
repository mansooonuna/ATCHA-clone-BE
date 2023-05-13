package com.sparta.atchaclonecoding.domain.member.service;

import com.sparta.atchaclonecoding.domain.member.dto.LoginRequestDto;
import com.sparta.atchaclonecoding.domain.member.dto.SignupRequestDto;
import com.sparta.atchaclonecoding.domain.member.entity.Member;
import com.sparta.atchaclonecoding.domain.member.repository.MemberRepository;
import com.sparta.atchaclonecoding.exception.CustomException;
import com.sparta.atchaclonecoding.security.jwt.JwtUtil;
import com.sparta.atchaclonecoding.security.jwt.TokenDto;
import com.sparta.atchaclonecoding.security.jwt.refreshToken.RefreshToken;
import com.sparta.atchaclonecoding.security.jwt.refreshToken.RefreshTokenRepository;
import com.sparta.atchaclonecoding.util.Message;
import com.sparta.atchaclonecoding.util.StatusEnum;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

import static com.sparta.atchaclonecoding.exception.ErrorCode.*;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    //회원가입
    public ResponseEntity<Message> signup(SignupRequestDto requestDto){
        String email = requestDto.getEmail();
        String password = passwordEncoder.encode(requestDto.getPassword());
        String nickname = requestDto.getNickname();

        Optional<Member> findMemberByEmail = memberRepository.findByEmail(email);
        if (findMemberByEmail.isPresent()){
            throw new CustomException(DUPLICATE_IDENTIFIER);
        }

        Optional<Member> findMemberByNickname = memberRepository.findByNickname(nickname);
        if (findMemberByNickname.isPresent()){
            throw new CustomException(DUPLICATE_NICKNAME);
        }

        Member member = new Member(email, password, nickname);
        memberRepository.save(member);
        Message message = Message.setSuccess(StatusEnum.OK, "회원가입 성공");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    //로그인
    public ResponseEntity<Message> login(LoginRequestDto requestDto, HttpServletResponse response){
        String email = requestDto.getEmail();
        String password = requestDto.getPassword();

        Member findMember = memberRepository.findByEmail(email).orElseThrow(
                () -> new CustomException(USER_NOT_FOUND));

        if (!passwordEncoder.matches(password, findMember.getPassword())){
            throw new CustomException(INVALID_PASSWORD);
        }

        TokenDto tokenDto = jwtUtil.createAllToken(email);
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByEmail(findMember.getEmail());
        if (refreshToken.isPresent()) {
            refreshTokenRepository.save(refreshToken.get().updateToken(tokenDto.getRefreshToken()));
        } else {
            RefreshToken newToken = new RefreshToken(tokenDto.getRefreshToken(), findMember.getEmail());
            refreshTokenRepository.save(newToken);
        }

        setHeader(response, tokenDto);
        Message message = Message.setSuccess(StatusEnum.OK, "로그인 성공");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    private void setHeader(HttpServletResponse response, TokenDto tokenDto) {
        response.addHeader(JwtUtil.ACCESS_KEY, tokenDto.getAccessToken());
        response.addHeader(JwtUtil.REFRESH_KEY, tokenDto.getRefreshToken());
    }
}
