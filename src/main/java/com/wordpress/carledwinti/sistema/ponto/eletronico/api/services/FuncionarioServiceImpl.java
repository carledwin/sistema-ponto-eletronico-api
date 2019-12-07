package com.wordpress.carledwinti.sistema.ponto.eletronico.api.services;

import com.wordpress.carledwinti.sistema.ponto.eletronico.api.entities.Funcionario;
import com.wordpress.carledwinti.sistema.ponto.eletronico.api.repositories.FuncionarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FuncionarioServiceImpl implements FuncionarioService {

    private static final Logger LOG = LoggerFactory.getLogger(FuncionarioServiceImpl.class);
    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Override
    public Funcionario save(Funcionario funcionario) {

        LOG.info("Save funcionario: {}", funcionario);
        return funcionarioRepository.save(funcionario);
    }

    @Override
    public Funcionario findByCpf(String cpf) {

        LOG.info("Find funcionario by cpf: {}", cpf);
        return funcionarioRepository.findByCpf(cpf);
    }

    @Override
    public Funcionario findByEmail(String email) {

        LOG.info("Find funcionario by email: {}", email);
        return funcionarioRepository.findByEmail(email);
    }

    @Override
    public Funcionario findById(Long id) {

        LOG.info("Find funcionario by id: {}", id);

        return funcionarioRepository.findOne(id);
    }
}
