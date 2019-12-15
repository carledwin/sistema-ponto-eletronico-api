package com.wordpress.carledwinti.sistema.ponto.eletronico.api.security.services;

import com.wordpress.carledwinti.sistema.ponto.eletronico.api.entities.Funcionario;
import com.wordpress.carledwinti.sistema.ponto.eletronico.api.security.JwtUserFactory;
import com.wordpress.carledwinti.sistema.ponto.eletronico.api.services.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private FuncionarioService funcionarioService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Funcionario> optionalFuncionario = funcionarioService.findByEmail(username);

        if (optionalFuncionario.isPresent()){
            return JwtUserFactory.create(optionalFuncionario.get());
        }

        throw new UsernameNotFoundException("Email-Funcionario não encontrado");
    }
}
