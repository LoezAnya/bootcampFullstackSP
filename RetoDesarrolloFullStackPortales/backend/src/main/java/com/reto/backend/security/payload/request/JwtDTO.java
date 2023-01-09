package com.reto.backend.security.payload.request;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtDTO {
    private String token;
    private String bearer="Bearer";
    private String username;
    private String name;
    private Collection<? extends GrantedAuthority> authorities;

    public JwtDTO(String token, String username, Collection<? extends GrantedAuthority> authorities) {
        this.token = token;
        this.username = username;
        this.authorities = authorities;
    }

    

    public JwtDTO(String token, String bearer, String username, String name,
            Collection<? extends GrantedAuthority> authorities) {
        this.token = token;
        this.bearer = bearer;
        this.username = username;
        this.name = name;
        this.authorities = authorities;
    }

    



    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getBearer() {
        return bearer;
    }

    public void setBearer(String bearer) {
        this.bearer = bearer;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }



    public String getName() {
        return name;
    }



    public void setName(String name) {
        this.name = name;
    }
}
