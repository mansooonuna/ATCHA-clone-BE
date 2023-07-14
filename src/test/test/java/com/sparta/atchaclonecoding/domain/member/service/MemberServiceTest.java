package com.sparta.atchaclonecoding.domain.member.service;

import com.sparta.atchaclonecoding.domain.member.dto.LoginRequestDto;
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
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static com.sparta.atchaclonecoding.exception.ErrorCode.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

@DisplayName("Member Service 테스트")
@ExtendWith(MockitoExtension.class)
class MemberServiceTest {
    @Mock
    MemberRepository memberRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    RefreshTokenRepository refreshTokenRepository;
    @Mock
    JwtUtil jwtUtil;
    @Mock
    RedisUtil redisUtil;
    @Mock
    ConfirmationTokenService confirmationTokenService;
    @Mock
    S3Uploader s3Uploader;
    @Mock
    HttpServletResponse httpServletResponse;
    @Mock
    HttpServletRequest httpServletRequest;
    @InjectMocks
    MemberService memberService;

    Member member = Member.builder().id(1L).email("example@email.com").password("password1!").nickname("닉네임").build();

    @DisplayName("회원가입 테스트")
    @Nested
    class signupTest {
        @DisplayName("회원가입 성공 테스트")
        @Test
        void signup() {
            // Given
            SignupRequestDto requestDto = SignupRequestDto.builder().email("example@email.com").password("password1!").nickname("닉네임").build();
            // When
            ResponseEntity<Message> response = memberService.signup(requestDto);
            // Then
            assertEquals("회원가입 성공", response.getBody().getMessage());
            assertEquals(HttpStatus.OK, response.getStatusCode());
        }

        @DisplayName("회원가입 실패 테스트 - 중복된 이메일 사용")
        @Test
        void signup_duplicatedEmail() {
            // Given
            SignupRequestDto requestDto = SignupRequestDto.builder().email("example@email.com").password("password1!").nickname("닉네임").build();
            when(memberRepository.findByEmail(anyString())).thenReturn(Optional.of(new Member()));
            // When
            CustomException exception = assertThrows(CustomException.class, () -> memberService.signup(requestDto));
            // Then
            assertEquals(exception.getErrorCode(), DUPLICATE_IDENTIFIER);
        }

        @DisplayName("회원가입 실패 테스트 - 중복된 닉네임 사용")
        @Test
        void signup_duplicatedNickname() {
            // Given
            SignupRequestDto requestDto = SignupRequestDto.builder().email("example@email.com").password("password1!").nickname("닉네임").build();
            when(memberRepository.findByNickname(anyString())).thenReturn(Optional.of(new Member()));
            // When
            CustomException exception = assertThrows(CustomException.class, () -> memberService.signup(requestDto));
            // Then
            assertEquals(exception.getErrorCode(), DUPLICATE_NICKNAME);
        }
    }

    @DisplayName("로그인 테스트")
    @Nested
    class loginTest {
        @DisplayName("로그인 성공 테스트")
        @Test
        void login() {
            // Given
            LoginRequestDto loginRequestDto = LoginRequestDto.builder().email("example@email.com").password("password1!").build();
            when(memberRepository.findByEmail(anyString())).thenReturn(Optional.of(member));
            when(passwordEncoder.matches(loginRequestDto.getPassword(), member.getPassword())).thenReturn(true);

            TokenDto tokenDto = new TokenDto("access_token", "refresh_token");
            when(jwtUtil.createAllToken(loginRequestDto.getEmail())).thenReturn(tokenDto);
            // When
            ResponseEntity<Message> response = memberService.login(loginRequestDto, httpServletResponse);
            // Then
            assertEquals("로그인 성공", response.getBody().getMessage());
            assertEquals(HttpStatus.OK, response.getStatusCode());
        }

        @DisplayName("로그인 성공 테스트 - refresh token 존재하면 새로운 토큰으로 재발급한다.")
        @Test
        void login_alreadyHaveRefreshToken() {
            // Given
            LoginRequestDto loginRequestDto = LoginRequestDto.builder().email("example@email.com").password("password1!").build();
            when(memberRepository.findByEmail(anyString())).thenReturn(Optional.of(member));
            when(passwordEncoder.matches(loginRequestDto.getPassword(), member.getPassword())).thenReturn(true);

            TokenDto tokenDto = new TokenDto("access_token", "refresh_token");
            when(jwtUtil.createAllToken(loginRequestDto.getEmail())).thenReturn(tokenDto);
            when(refreshTokenRepository.findByEmail(loginRequestDto.getEmail())).thenReturn(Optional.of(new RefreshToken()));
            // When
            ResponseEntity<Message> response = memberService.login(loginRequestDto, httpServletResponse);
            // Then
            assertEquals("로그인 성공", response.getBody().getMessage());
            assertEquals(HttpStatus.OK, response.getStatusCode());
        }

        @DisplayName("로그인 실패 테스트 - 없는 회원")
        @Test
        void login_memberNotFound() {
            // Given
            LoginRequestDto loginRequestDto = LoginRequestDto.builder().email("example@email.com").password("password1!").build();
            when(memberRepository.findByEmail(loginRequestDto.getEmail())).thenReturn(Optional.empty());
            // When
            CustomException exception = assertThrows(CustomException.class, () -> memberService.login(loginRequestDto, httpServletResponse));
            // Then
            assertEquals(exception.getErrorCode(), USER_NOT_FOUND);
        }

        @DisplayName("로그인 실패 테스트 - 비밀번호 틀림")
        @Test
        void login_invalidPassword() {
            // Given
            LoginRequestDto loginRequestDto = LoginRequestDto.builder().email("example@email.com").password("password1!").build();
            when(memberRepository.findByEmail(anyString())).thenReturn(Optional.of(member));
            when(passwordEncoder.matches(loginRequestDto.getPassword(), member.getPassword())).thenReturn(false);
            // When
            CustomException exception = assertThrows(CustomException.class, () -> memberService.login(loginRequestDto, httpServletResponse));
            // Then
            assertEquals(exception.getErrorCode(), INVALID_PASSWORD);
        }
    }

    @DisplayName("로그아웃 테스트")
    @Nested
    class logoutTest {
        @DisplayName("로그아웃 성공 테스트")
        @Test
        void logout() {
            // Given
            when(refreshTokenRepository.findByEmail(member.getEmail())).thenReturn(Optional.of(new RefreshToken()));

            String accessToken = "Access_token_123123";
            when(httpServletRequest.getHeader("ACCESS_KEY")).thenReturn("Bearer " + accessToken);
            // When
            ResponseEntity<Message> response = memberService.logout(member, httpServletRequest);
            // Then
            assertEquals("로그아웃 성공", response.getBody().getMessage());
            assertEquals(HttpStatus.OK, response.getStatusCode());
        }

        @DisplayName("로그아웃 실패 테스트 - 없는 회원")
        @Test
        void logout_memberNotFound() {
            // Given
            String accessToken = "Access_token_123123";
            when(httpServletRequest.getHeader("ACCESS_KEY")).thenReturn("Bearer " + accessToken);
            // When
            CustomException exception = assertThrows(CustomException.class, () -> memberService.logout(member, httpServletRequest));
            // Then
            assertEquals(USER_NOT_FOUND, exception.getErrorCode());
        }
    }


    @DisplayName("마이페이지 조회 테스트")
    @Nested
    class myPageTest {
        @DisplayName("마이페이지 조회 성공 테스트")
        @Test
        void getMypage() {
            // Given
            when(memberRepository.findByEmail(member.getEmail())).thenReturn(Optional.of(member));
            // When
            ResponseEntity<Message> response = memberService.getMypage(member);
            // Then
            assertEquals("요청 성공", response.getBody().getMessage());
            assertEquals(HttpStatus.OK, response.getStatusCode());
        }

        @DisplayName("마이페이지 조회 실패 테스트 - 없는 회원")
        @Test
        void getMypage_memberNotFound() {
            // Given
            when(memberRepository.findByEmail(member.getEmail())).thenReturn(Optional.empty());
            // When
            CustomException exception = assertThrows(CustomException.class, () -> memberService.getMypage(member));
            // Then
            assertEquals(USER_NOT_FOUND, exception.getErrorCode());
        }
    }


    @DisplayName("이메일 검증 후 비밀번호 변경 테스트")
    @Nested
    class changePasswordTest {
        @DisplayName("비밀번호 변경 성공 테스트")
        @Test
        void confirmEmailToFindPassword() {
            // Given

            // When

            // Then
        }
    }



    @Test
    void profileUpdate() {
    }

    @Test
    void nickUpdate() {
    }
}