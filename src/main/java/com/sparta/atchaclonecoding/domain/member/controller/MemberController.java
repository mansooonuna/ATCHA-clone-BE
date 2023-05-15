package com.sparta.atchaclonecoding.domain.member.controller;

import com.sparta.atchaclonecoding.domain.member.dto.ChangePwRequestDto;
import com.sparta.atchaclonecoding.domain.member.dto.EmailRequestDto;
import com.sparta.atchaclonecoding.domain.member.dto.LoginRequestDto;
import com.sparta.atchaclonecoding.domain.member.dto.ProfileRequestDto;
import com.sparta.atchaclonecoding.domain.member.dto.SignupRequestDto;
import com.sparta.atchaclonecoding.domain.member.email.ConfirmationTokenService;
import com.sparta.atchaclonecoding.domain.member.email.EmailService;
import com.sparta.atchaclonecoding.domain.member.service.MemberService;
import com.sparta.atchaclonecoding.security.userDetails.UserDetailsImpl;
import com.sparta.atchaclonecoding.util.Message;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/atcha/members")
@Tag(name = "MemberController", description = "유저 관련 API")
public class MemberController {

    private final MemberService memberService;
    private final EmailService emailService;

    @Operation(summary = "회원가입 메서드", description = "회원가입 메서드입니다.")
    @PostMapping("/signup")
    public ResponseEntity<Message> signup(@Valid @RequestBody SignupRequestDto requestDto) {
        return memberService.signup(requestDto);
    }

    @Operation(summary = "로그인 메서드", description = "로그인 메서드입니다.")
    @PostMapping("/login")
    public ResponseEntity<Message> login(@RequestBody LoginRequestDto requestDto, HttpServletResponse response) {
        return memberService.login(requestDto, response);
    }

    @Operation(summary = "로그아웃 메서드", description = "로그아웃 메서드입니다.")
    @PostMapping("/logout")
    public ResponseEntity<Message> logout(@AuthenticationPrincipal UserDetailsImpl userDetails, HttpServletRequest request) {
        return memberService.logout(userDetails.getMember(), request);
    }

    @Operation(summary = "마이페이지 조회", description = "마이페이지 조회하는 메서드입니다.")
    @GetMapping("/mypage")
    public ResponseEntity<Message> getMypage(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return memberService.getMypage(userDetails.getMember());
    }

    @Operation(summary = "이메일 전송", description = "비밀번호 찾기를 위한 이메일 전송 메서드입니다.")
    @PostMapping("/find-password")
    public String sendEmailToFindPassword(@RequestBody EmailRequestDto requestDto) throws Exception {
        return emailService.sendSimpleMessage(requestDto);
    }

    @Operation(summary = "비밀번호 변경", description = "이메일 확인 후 비밀번호 변경 메서드입니다.")
    @PostMapping("/confirm-email")
    public ResponseEntity<Message> confirmEmailToFindPassword(@Valid @RequestParam String token, @Valid @RequestBody ChangePwRequestDto requestDto){
        return memberService.confirmEmailToFindPassword(token, requestDto);

    @PutMapping(value = "/mypage",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE,MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Message> profileUpdate(@RequestPart("imageFile")MultipartFile image,
                                                 @RequestPart ProfileRequestDto profileRequestDto,
                                                 @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        return memberService.profileUpdate(image, profileRequestDto, userDetails.getMember());
    }
}
