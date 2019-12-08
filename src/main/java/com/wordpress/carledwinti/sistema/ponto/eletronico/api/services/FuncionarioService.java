package com.wordpress.carledwinti.sistema.ponto.eletronico.api.services;

import com.wordpress.carledwinti.sistema.ponto.eletronico.api.entities.Funcionario;

import java.util.Optional;

public interface FuncionarioService {

    Optional<Funcionario> save(Funcionario funcionario);

    Optional<Funcionario> findByCpf(String cpf);

    Optional<Funcionario> findByEmail(String email);

    Optional<Funcionario> findById(Long id);
}
