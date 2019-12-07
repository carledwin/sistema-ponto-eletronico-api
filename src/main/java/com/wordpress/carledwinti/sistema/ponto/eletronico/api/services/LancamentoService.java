package com.wordpress.carledwinti.sistema.ponto.eletronico.api.services;

import com.wordpress.carledwinti.sistema.ponto.eletronico.api.entities.Lancamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface LancamentoService {

    Page<Lancamento> findByFuncionarioId(Long funcionarioId, PageRequest pageRequest);

    Lancamento findById(Long id);

    Lancamento save(Lancamento lancamento);

    void remove(Long id);
}
