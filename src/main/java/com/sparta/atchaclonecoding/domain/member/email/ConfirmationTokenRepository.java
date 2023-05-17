package com.sparta.atchaclonecoding.domain.member.email;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken,String> {
    Optional<ConfirmationToken> findByIdAndExpired(String confirmationTokenId, boolean expired);
}