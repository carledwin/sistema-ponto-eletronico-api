package com.wordpress.carledwinti.sistema.ponto.eletronico.api.security;

import com.wordpress.carledwinti.sistema.ponto.eletronico.api.entities.Funcionario;
import com.wordpress.carledwinti.sistema.ponto.eletronico.api.enums.PerfilEnum;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class JwtUserFactory {

    public static JwtUser create(Funcionario funcionario){
        return new JwtUser(funcionario.getId(), funcionario.getEmail(), funcionario.getSenha(), mapToGrantedAuthorities(funcionario.getPerfilEnum()));
    }

    private static Collection<? extends GrantedAuthority> mapToGrantedAuthorities(PerfilEnum perfilEnum) {

        List<GrantedAuthority> listGrantedAuthorities = new ArrayList<>();
        listGrantedAuthorities.add(new SimpleGrantedAuthority(perfilEnum.toString()));

        return listGrantedAuthorities;
    }

}
