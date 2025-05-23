package com.blockbank.blockbank.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class BlockbankUserDetails implements UserDetails {
    private final String username;
    private final String ethAddress;
    private final String userId;
    private final Collection<? extends GrantedAuthority> authorities;

    public BlockbankUserDetails(String username, String ethAddress, String userId,
                                Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.ethAddress = ethAddress;
        this.userId = userId;
        this.authorities = authorities;
    }

    public String getEthAddress() {
        return ethAddress;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}
