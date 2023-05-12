package com.sparta.atchaclonecoding.security.userDetails;

public class UserDetailsImpl {

    private final Member member;
    private final String email;

    public UserDetailsImpl(Member member, String email) {
        this.member = member;
        this.email = email;
    }

    public Member getMember() {
        return member;
    }

    @Override
    public String getEmail() {
        return this.email;
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

