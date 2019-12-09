package com.wordpress.carledwinti.sistema.ponto.eletronico.api.services;

import com.wordpress.carledwinti.sistema.ponto.eletronico.api.entities.Lancamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

public interface LancamentoService {

    Page<Lancamento> findByFuncionarioId(Long funcionarioId, PageRequest pageRequest);

    Optional<Lancamento> findById(Long id);

    Optional<Lancamento> save(Lancamento lancamento);

    void remove(Long id);
}
