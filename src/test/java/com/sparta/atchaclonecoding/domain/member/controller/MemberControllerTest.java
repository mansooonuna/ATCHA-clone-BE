package com.sparta.atchaclonecoding.domain.member.controller;

import com.sparta.atchaclonecoding.domain.member.dto.ProfileRequestDto;
import com.sparta.atchaclonecoding.domain.member.email.EmailService;
import com.sparta.atchaclonecoding.domain.member.entity.Member;
import com.sparta.atchaclonecoding.domain.member.service.MemberService;
import com.sparta.atchaclonecoding.security.userDetails.UserDetailsImpl;
import com.sparta.atchaclonecoding.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(memberController).build();
    }

    Member member = Member.builder().id(1L).email("example@email.com").password("password1!").nickname("닉네임").build();

    @DisplayName("[GET] 마이페이지 조회 테스트")
    @Test
    void getMypage() throws Exception {
        // Given
        UserDetailsImpl userDetails = new UserDetailsImpl(member, member.getEmail());
        Message message = new Message(200, "요청 성공", null);
        when(memberService.getMypage(any(Member.class))).thenReturn(ResponseEntity.ok(message));
        // When
        mockMvc.perform(MockMvcRequestBuilders.get("/atcha/members/mypage")
                        .principal(userDetails))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(message.getMessage()));
        // Then
        verify(memberService, times(1)).getMypage(member);
    }


    @DisplayName("[PUT] 프로필 사진 수정 테스트")
    @Test
    void profileUpdate() throws IOException {
        // Given
        UserDetailsImpl userDetails = new UserDetailsImpl(member, member.getEmail());
        Message message = new Message(200, "수정 성공", null);
        MockMultipartFile imageFile = new MockMultipartFile("imageFile", "image.jpg", MediaType.IMAGE_JPEG_VALUE, "test image".getBytes());
        when(memberService.profileUpdate(any(MultipartFile.class), any(ProfileRequestDto.class), any(Member.class))).thenReturn(ResponseEntity.ok(message));
        // When
        ResponseEntity<Message> response = memberController.profileUpdate(imageFile, new ProfileRequestDto(), userDetails);
        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(message, response.getBody());
    }

    @DisplayName("[PUT] 닉네임 수정 테스트")
    @Test
    void nickUpdate() {
        // Given
        UserDetailsImpl userDetails = new UserDetailsImpl(member, member.getEmail());
        Message message = new Message(200, "수정 성공", null);
        when(memberService.nickUpdate(any(ProfileRequestDto.class), any(Member.class))).thenReturn(ResponseEntity.ok(message));
        // When
        ResponseEntity<Message> response = memberController.nickUpdate(new ProfileRequestDto(), userDetails);
        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(message, response.getBody());
    }
}