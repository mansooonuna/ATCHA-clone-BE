package com.sparta.atchaclonecoding.domain.member.service;

import com.sparta.atchaclonecoding.domain.member.dto.ChangePwRequestDto;
import com.sparta.atchaclonecoding.domain.member.dto.EmailRequestDto;
import com.sparta.atchaclonecoding.domain.member.dto.LoginRequestDto;
import com.sparta.atchaclonecoding.domain.member.dto.ProfileRequestDto;
import com.sparta.atchaclonecoding.domain.member.dto.SignupRequestDto;
import com.sparta.atchaclonecoding.domain.member.email.ConfirmationToken;
import com.sparta.atchaclonecoding.domain.member.email.ConfirmationTokenService;
import com.sparta.atchaclonecoding.domain.member.entity.Member;
import com.sparta.atchaclonecoding.domain.member.repository.MemberRepository;
import com.sparta.atchaclonecoding.exception.CustomException;
import com.sparta.atchaclonecoding.redis.util.RedisUtil;
import com.sparta.atchaclonecoding.security.jwt.JwtUtil;
import com.sparta.atchaclonecoding.security.jwt.TokenDto;
import com.sparta.atchaclonecoding.security.jwt.refreshToken.RefreshToken;
import com.sparta.atchaclonecoding.security.jwt.refreshToken.RefreshTokenRepository;
import com.sparta.atchaclonecoding.util.Message;
import com.sparta.atchaclonecoding.util.S3Uploader;
import com.sparta.atchaclonecoding.util.StatusEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.sparta.atchaclonecoding.exception.ErrorCode.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;
    private final ConfirmationTokenService confirmationTokenService;
    private final S3Uploader s3Uploader;

    //회원가입
    public ResponseEntity<Message> signup(SignupRequestDto requestDto) {
        String email = requestDto.getEmail();
        String password = passwordEncoder.encode(requestDto.getPassword());
        String nickname = requestDto.getNickname();

        Optional<Member> findMemberByEmail = memberRepository.findByEmail(email);
        if (findMemberByEmail.isPresent()) {
            log.info("회원가입 중복된 이메일 사용");
            throw new CustomException(DUPLICATE_IDENTIFIER);
        }

        Optional<Member> findMemberByNickname = memberRepository.findByNickname(nickname);
        if (findMemberByNickname.isPresent()) {
            log.info("회원가입 중복된 닉네임 사용");
            throw new CustomException(DUPLICATE_NICKNAME);
        }

        Member member = new Member(email, password, nickname);
        memberRepository.save(member);
        Message message = Message.setSuccess(StatusEnum.OK, "회원가입 성공");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    //로그인
    public ResponseEntity<Message> login(LoginRequestDto requestDto, HttpServletResponse response) {
        String email = requestDto.getEmail();
        String password = requestDto.getPassword();

        Member findMember = memberRepository.findByEmail(email).orElseThrow(
                () -> new CustomException(USER_NOT_FOUND));

        if (!passwordEncoder.matches(password, findMember.getPassword())) {
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

    // 로그아웃
    @Transactional
    public ResponseEntity<Message> logout(Member member, HttpServletRequest request) {
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByEmail(member.getEmail());

        String accessToken = request.getHeader("ACCESS_KEY").substring(7);
        if (refreshToken.isPresent()) {
            Long tokenTime = jwtUtil.getExpirationTime(accessToken);
            redisUtil.setBlackList(accessToken, "access_token", tokenTime);
            refreshTokenRepository.deleteByEmail(member.getEmail());
            Message message = Message.setSuccess(StatusEnum.OK, "로그아웃 성공", member.getEmail());
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        throw new CustomException(USER_NOT_FOUND);
    }

    // 마이페이지 조회
    @Transactional
    public ResponseEntity<Message> getMypage(Member member) {
        Member findMember = memberRepository.findByEmail(member.getEmail()).orElseThrow(
                () -> new CustomException(USER_NOT_FOUND)
        );
        Message message = Message.setSuccess(StatusEnum.OK, "요청 성공", findMember);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    //이메일 검증 후 비밀번호 변경
    public ResponseEntity<Message> confirmEmailToFindPassword(String token, ChangePwRequestDto requestDto) {
        ConfirmationToken findConfirmationToken = confirmationTokenService.findByIdAndExpired(token);
        System.out.println(requestDto.getPassword());
        Member findMember = memberRepository.findByEmail(findConfirmationToken.getEmail()).orElseThrow(
                () -> new CustomException(USER_NOT_FOUND));
        String password = passwordEncoder.encode(requestDto.getPassword());

        findConfirmationToken.useToken();

        findMember.changePassword(password);

        Message message = Message.setSuccess(StatusEnum.OK, "비밀번호 변경 성공");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<Message> profileUpdate(MultipartFile imageFile,
                                                 ProfileRequestDto profileRequestDto,
                                                 Member member) throws IOException {
        Member findMember = memberRepository.findByEmail(member.getEmail()).orElseThrow(
            () -> new CustomException(USER_NOT_FOUND)
        );
        findMember.setNickname(profileRequestDto.getNickname());
        if(!imageFile.isEmpty()){
            s3Uploader.delete(findMember.getImage());
            String storedFileName  = s3Uploader.uploadFile(imageFile);
            findMember.setImage(storedFileName);
        }
        memberRepository.save(findMember);
        Message message = Message.setSuccess(StatusEnum.OK, "수정 성공", findMember);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    // 헤더 셋팅 - 리프레시 토큰 미적용
    private void setHeader(HttpServletResponse response, TokenDto tokenDto) {
        response.addHeader(JwtUtil.ACCESS_KEY, tokenDto.getAccessToken());
//        response.addHeader(JwtUtil.REFRESH_KEY, tokenDto.getRefreshToken());
    }
}
