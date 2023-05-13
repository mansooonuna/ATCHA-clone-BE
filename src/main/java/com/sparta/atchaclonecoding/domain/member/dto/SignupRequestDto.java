package com.sparta.atchaclonecoding.domain.member.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignupRequestDto {

    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\\\.[A-Za-z]{2,}$")
    private String email;
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\\\d|.*[!@#$%^&*()\\-+=~])(?=.*[A-Za-z\\\\d!@#$%^&*()\\-+=~]).{10,}$", message = "영문, 숫자, 특문 중 2개 조합 10자 이상")
    private String password;
    @Size(min = 2, message = "2글자 이상")
    private String nickname;
}


