package com.sparta.atchaclonecoding.security.userDetails;

import com.sparta.atchaclonecoding.domain.member.entity.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.security.auth.Subject;
import java.security.Principal;
import java.util.Collection;

public class UserDetailsImpl implements UserDetails, Principal {

    private final Member member;
    private final String email;

    public UserDetailsImpl(Member member, String email) {
        this.member = member;
        this.email = email;
    }

    public Member getMember() {
        return member;
    }

    public String getEmail() {
        return this.email;
    }

    public String getNickname() {
        return this.member.getNickname();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
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

    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean implies(Subject subject) {
        return Principal.super.implies(subject);
    }
}