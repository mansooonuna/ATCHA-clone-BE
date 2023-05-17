package com.sparta.atchaclonecoding.domain.member.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChangePwRequestDto {
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9`~!@#$%^&*()-_=+\\|[{]};:'\",<.>/?]).{10,}$", message = "영문, 숫자, 특문 중 2개 조합 10자 이상")
    private String password;
}
