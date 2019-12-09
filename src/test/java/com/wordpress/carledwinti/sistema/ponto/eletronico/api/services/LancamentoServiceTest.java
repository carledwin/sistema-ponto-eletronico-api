package com.wordpress.carledwinti.sistema.ponto.eletronico.api.services;

import com.wordpress.carledwinti.sistema.ponto.eletronico.api.entities.Lancamento;
import com.wordpress.carledwinti.sistema.ponto.eletronico.api.repositories.LancamentoRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class LancamentoServiceTest {

    @MockBean
    private LancamentoRepository lancamentoRepository;

    @Autowired
    private LancamentoService lancamentoService;

    @Before
    public void setUp(){
        BDDMockito.given(lancamentoRepository.findByFuncionarioId(Mockito.anyLong(), Mockito.any(PageRequest.class))).willReturn(new PageImpl<Lancamento>(new ArrayList<>()));
        BDDMockito.given(lancamentoRepository.findOne(Mockito.anyLong())).willReturn(new Lancamento());
        BDDMockito.given(lancamentoRepository.save(Mockito.any(Lancamento.class))).willReturn(new Lancamento());
    }

    @Test
    public void findByFuncionarioIdPageTest(){
        Page<Lancamento> pageLancamento = lancamentoService.findByFuncionarioId(1L, new PageRequest(0, 10));
        Assert.assertNotNull(pageLancamento);
    }

    @Test
    public void findByIdTest(){
        Optional<Lancamento> optionalLancamento = lancamentoService.findById(1L);
        Assert.assertNotNull(optionalLancamento.get());
    }

    @Test
    public void saveTest(){
        Optional<Lancamento> optionalLancamento = lancamentoService.save(new Lancamento());
        Assert.assertNotNull(optionalLancamento.get());
    }
}
