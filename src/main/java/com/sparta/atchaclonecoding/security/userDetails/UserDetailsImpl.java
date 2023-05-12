package com.sparta.atchaclonecoding.security.userDetails;

import lombok.Builder;

public class UserDetailsImpl {

    private final Member member;
    private final String userEmail;

    public UserDetailsImpl(Member member, String userEmail) {
        this.member = member;
        this.userEmail = userEmail;
    }

    public Member getMember() {
        return member;
    }

    @Override
    public String getUsername() {
        return this.userEmail;
    }

    public String getNickname() {
        return this.member.getNickname();
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
}
