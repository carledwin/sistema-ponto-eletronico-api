package com.wordpress.carledwinti.sistema.ponto.eletronico.api.services;

import com.wordpress.carledwinti.sistema.ponto.eletronico.api.entities.Funcionario;
import com.wordpress.carledwinti.sistema.ponto.eletronico.api.repositories.FuncionarioRepository;
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

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class FuncionarioServiceTest {

    @MockBean
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private FuncionarioService funcionarioService;

    @Before
    public void setUp(){
        BDDMockito.given(funcionarioRepository.save(Mockito.any(Funcionario.class))).willReturn(new Funcionario());
        BDDMockito.given(funcionarioRepository.findByCpf(Mockito.anyString())).willReturn(new Funcionario());
        BDDMockito.given(funcionarioRepository.findByEmail(Mockito.anyString())).willReturn(new Funcionario());
        BDDMockito.given(funcionarioRepository.findOne(Mockito.anyLong())).willReturn(new Funcionario());
    }

    @Test
    public void saveFuncionarioTest(){

        Funcionario funcionario = funcionarioRepository.save(new Funcionario());
        Assert.assertNotNull(funcionario);
    }

    @Test
    public void findFuncionarioByIdTest(){

        Funcionario funcionario = funcionarioRepository.findOne(1l);
        Assert.assertNotNull(funcionario);
    }

    @Test
    public void findFuncionarioByEmail(){

        Funcionario funcionario = funcionarioRepository.findByEmail("funcionario.test@carledwinti.com");
        Assert.assertNotNull(funcionario);
    }

    @Test
    public void findFuncionarioByCpf(){

        Funcionario funcionario = funcionarioRepository.findByCpf("12345678901");
        Assert.assertNotNull(funcionario);
    }
}
