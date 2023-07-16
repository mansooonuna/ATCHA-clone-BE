package com.sparta.atchaclonecoding.domain.member.controller;

import com.sparta.atchaclonecoding.domain.member.dto.SignupRequestDto;
import com.sparta.atchaclonecoding.domain.member.email.EmailService;
import com.sparta.atchaclonecoding.domain.member.service.MemberService;
import com.sparta.atchaclonecoding.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Member Controller 테스트")
class MemberControllerTest {
    @Mock
    MemberService memberService;
    @Mock
    EmailService emailService;
    @InjectMocks
    MemberController memberController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(memberController).build();
    }

    @DisplayName("[GET] 마이페이지 조회 테스트")
    @Test
    void getMypage() {
    }


    @DisplayName("[PUT] 프로필 사진 수정 테스트")
    @Test
    void profileUpdate() {
    }

    @DisplayName("[PUT] 닉네임 수정 테스트")
    @Test
    void nickUpdate() {
    }
}