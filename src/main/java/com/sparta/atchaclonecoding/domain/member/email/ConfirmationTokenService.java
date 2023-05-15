package com.sparta.atchaclonecoding.domain.member.email;

import com.sparta.atchaclonecoding.domain.member.dto.EmailRequestDto;
import com.sparta.atchaclonecoding.exception.CustomException;
import com.sparta.atchaclonecoding.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ConfirmationTokenService {
    private final ConfirmationTokenRepository confirmationTokenRepository;

    public ConfirmationToken findByIdAndExpired(String confirmationTokenId){
        Optional<ConfirmationToken> confirmationToken = confirmationTokenRepository.findByIdAndExpired(confirmationTokenId, false);
        return confirmationToken.orElseThrow(()-> new CustomException(ErrorCode.USED_TOKEN));
    };

}
