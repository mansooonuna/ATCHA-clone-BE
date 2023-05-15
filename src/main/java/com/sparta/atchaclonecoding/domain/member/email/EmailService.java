package com.sparta.atchaclonecoding.domain.member.email;

import com.sparta.atchaclonecoding.domain.member.dto.EmailRequestDto;

public interface EmailService {
    String sendSimpleMessage(EmailRequestDto requestDto)throws Exception;
}
