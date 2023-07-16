package com.sparta.atchaclonecoding.domain.member.controller;

import com.sparta.atchaclonecoding.domain.member.dto.ChangePwRequestDto;
import com.sparta.atchaclonecoding.domain.member.dto.EmailRequestDto;
import com.sparta.atchaclonecoding.domain.member.dto.LoginRequestDto;
import com.sparta.atchaclonecoding.domain.member.dto.SignupRequestDto;
import com.sparta.atchaclonecoding.domain.member.email.EmailService;
import com.sparta.atchaclonecoding.domain.member.entity.Member;
import com.sparta.atchaclonecoding.domain.member.service.MemberService;
import com.sparta.atchaclonecoding.security.userDetails.UserDetailsImpl;
import com.sparta.atchaclonecoding.util.Message;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@DisplayName("[POST] Member Controller 테스트")
class MemberPostControllerTest {
    @Mock
    private MemberService memberService;
    @Mock
    private EmailService emailService;
    @Mock
    private UserDetailsImpl userDetails;

    private MemberController memberController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        memberController = new MemberController(memberService, emailService);
    }

    Member member = Member.builder().id(1L).email("example@email.com").password("password1!").nickname("닉네임").build();

    @DisplayName("[POST] 회원가입 테스트")
    @Test
    void signup() {
        // Given
        Message message = new Message(200, "회원가입 성공", null);
        SignupRequestDto requestDto = SignupRequestDto.builder().email("example@email.com").password("password1!").nickname("닉네임").build();
        when(memberService.signup(any(SignupRequestDto.class))).thenReturn(ResponseEntity.ok(message));
        // When
        ResponseEntity<Message> response = memberController.signup(requestDto);
        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(message.getMessage(), response.getBody().getMessage());
    }

    @DisplayName("[POST] 로그인 테스트")
    @Test
    void login() {
        // Given
        Message message = new Message(200, "로그인 성공", null);
        HttpServletResponse httpServletResponse = new MockHttpServletResponse();
        LoginRequestDto requestDto = LoginRequestDto.builder().email("example@email.com").password("password1!").build();
        when(memberService.login(any(LoginRequestDto.class), any(HttpServletResponse.class))).thenReturn(ResponseEntity.ok(message));
        // When
        ResponseEntity<Message> response = memberController.login(requestDto, httpServletResponse);
        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(message.getMessage(), response.getBody().getMessage());
    }

    @DisplayName("[POST] 로그아웃 테스트")
    @Test
    void logout() {
        // Given
        Message message = new Message(200, "로그아웃 성공", null);
        HttpServletRequest httpServletRequest = new MockHttpServletRequest();
        when(memberService.logout(any(), any(HttpServletRequest.class))).thenReturn(ResponseEntity.ok(message));
        // When
        ResponseEntity<Message> response = memberController.logout(userDetails, httpServletRequest);
        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(message.getMessage(), response.getBody().getMessage());
    }

    @DisplayName("[POST] 이메일 전송 테스트")
    @Test
    void sendEmailToFindPassword() throws Exception {
        // Given
        Message message = new Message(200, "이메일을 성공적으로 보냈습니다.", null);
        when(emailService.sendSimpleMessage(any(EmailRequestDto.class))).thenReturn(ResponseEntity.ok(message));
        // When
        ResponseEntity<Message> response = memberController.sendEmailToFindPassword(new EmailRequestDto());
        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(message.getMessage(), response.getBody().getMessage());
    }

    @DisplayName("[POST] 비밀번호 변경 테스트")
    @Test
    void confirmEmailToFindPassword() {
        // Given
        Message message = new Message(200, "비밀번호 변경 성공", null);
        when(memberService.confirmEmailToFindPassword(anyString(), any(ChangePwRequestDto.class))).thenReturn(ResponseEntity.ok(message));
        // When
        ResponseEntity<Message> response = memberController.confirmEmailToFindPassword("token", new ChangePwRequestDto());
        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(message.getMessage(), response.getBody().getMessage());
    }
}