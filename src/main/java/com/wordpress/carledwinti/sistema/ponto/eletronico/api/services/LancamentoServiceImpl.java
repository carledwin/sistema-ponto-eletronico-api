package com.wordpress.carledwinti.sistema.ponto.eletronico.api.services;

import com.wordpress.carledwinti.sistema.ponto.eletronico.api.entities.Lancamento;
import com.wordpress.carledwinti.sistema.ponto.eletronico.api.repositories.LancamentoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LancamentoServiceImpl implements LancamentoService {

    private static final Logger LOG = LoggerFactory.getLogger(LancamentoServiceImpl.class);

    @Autowired
    private LancamentoRepository lancamentoRepository;

    @Override
    public Page<Lancamento> findByFuncionarioId(Long funcionarioId, PageRequest pageRequest) {
        LOG.info("Find by funcionarioId: {}", funcionarioId);
        return lancamentoRepository.findByFuncionarioId(funcionarioId, pageRequest);
    }

    @Override
    public Optional<Lancamento> findById(Long id) {
        LOG.info("Find by Id: {}", id);
        return Optional.ofNullable(lancamentoRepository.findOne(id));
    }

    @Override
    public Optional<Lancamento> save(Lancamento lancamento) {
        LOG.info("Save lancamento: {}", lancamento);
        return Optional.ofNullable(lancamentoRepository.save(lancamento));
    }

    @Override
    public void remove(Long id) {
        LOG.info("Remove lancamento by Id: {}", id);
        lancamentoRepository.delete(id);
    }
}
