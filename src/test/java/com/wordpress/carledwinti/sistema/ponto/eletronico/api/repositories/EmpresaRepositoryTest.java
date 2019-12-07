package com.wordpress.carledwinti.sistema.ponto.eletronico.api.repositories;

import com.wordpress.carledwinti.sistema.ponto.eletronico.api.entities.Empresa;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class EmpresaRepositoryTest {

    @Autowired
    private EmpresaRepository empresaRepository;

    private static final String CNPJ = "32746367000145";

    @Before
    public void setUp(){

        Empresa empresa = new Empresa();

        empresa.setRazaoSocial("Empresa Test");
        empresa.setCnpj(CNPJ);

        this.empresaRepository.save(empresa);
    }

    @After
    public final void tearDown(){
        this.empresaRepository.deleteAll();
    }

    @Test
    public void findByCnpjTest(){

        Empresa empresa = this.empresaRepository.findByCnpj(CNPJ);
        Assert.assertEquals(CNPJ, empresa.getCnpj());
    }

}
