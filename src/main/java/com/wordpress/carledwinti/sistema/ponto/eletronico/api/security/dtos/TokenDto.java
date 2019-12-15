package com.wordpress.carledwinti.sistema.ponto.eletronico.api.security.dtos;

public class TokenDto {

    private String token;

    public String getToken() {
        return token;
    }

    public TokenDto(String token){
        this.token = token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
