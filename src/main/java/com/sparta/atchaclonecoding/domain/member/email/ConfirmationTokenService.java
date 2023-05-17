package com.sparta.atchaclonecoding.domain.member.email;

import com.sparta.atchaclonecoding.exception.CustomException;
import com.sparta.atchaclonecoding.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
