package com.wordpress.carledwinti.sistema.ponto.eletronico.api.services;

import com.wordpress.carledwinti.sistema.ponto.eletronico.api.entities.Empresa;
import com.wordpress.carledwinti.sistema.ponto.eletronico.api.repositories.EmpresaRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class EmpresaServiceTest {

    @MockBean
    private EmpresaRepository empresaRepository;

    @Autowired
    private EmpresaService empresaService;

    private static final String CNPJ = "32746367000145";

    @Before
    public void setUp(){
        BDDMockito.given(empresaRepository.findByCnpj(Mockito.anyString())).willReturn(new Empresa());
        BDDMockito.given(empresaRepository.save(Mockito.any(Empresa.class))).willReturn(new Empresa());
    }

    @Test
    public void findEmpresaByCnpj(){

       Empresa empresa = empresaRepository.findByCnpj(CNPJ);
       Assert.assertNotNull(empresa);
    }

    @Test
    public void saveEmpresa(){

        Empresa empresa = empresaRepository.save(new Empresa());
        Assert.assertNotNull(empresa);
    }
}
