package com.wordpress.carledwinti.sistema.ponto.eletronico.api.services;

import com.wordpress.carledwinti.sistema.ponto.eletronico.api.entities.Funcionario;

import java.util.Optional;

public interface FuncionarioService {

    Funcionario save(Funcionario funcionario);

    Funcionario findByCpf(String cpf);

    Funcionario findByEmail(String email);

    Funcionario findById(Long id);
}
