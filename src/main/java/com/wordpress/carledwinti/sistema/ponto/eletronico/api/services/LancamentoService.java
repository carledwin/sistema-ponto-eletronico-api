package com.wordpress.carledwinti.sistema.ponto.eletronico.api.services;

import com.wordpress.carledwinti.sistema.ponto.eletronico.api.entities.Lancamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

public interface LancamentoService {

    List<Lancamento> findByFuncionarioId(Long funcionarioId);

    Page<Lancamento> findByFuncionarioId(Long funcionarioId, PageRequest pageRequest);

    Optional<Lancamento> findByFuncionarioIdDataCriacaoDesc(Long funcionarioId);

    Optional<Lancamento> findById(Long id);

    Optional<Lancamento> save(Lancamento lancamento);

    void remove(Long id);
}
