package com.wordpress.carledwinti.sistema.ponto.eletronico.api.repositories;


import com.wordpress.carledwinti.sistema.ponto.eletronico.api.entities.Empresa;
import com.wordpress.carledwinti.sistema.ponto.eletronico.api.entities.Funcionario;
import com.wordpress.carledwinti.sistema.ponto.eletronico.api.enums.PerfilEnum;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class FuncionarioRepositoryTest {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    private static final String EMAIL = "funcionario.test@carledwinti.com";
    private static final String INVALID_EMAIL = "funcionario.test.invalid@carledwinti.com";
    private static final String CPF = "77125349005";
    private static final String INVALID_CPF = "12345678901";
    private static final String CNPJ = "32746367000145";
    private static final String RAZAO_SOCIAL = "Empresa Test";
    private static final PerfilEnum PERFIL = PerfilEnum.ROLE_USER;
    private static final String NOME = "Funcionario Test";
    private static final String SENHA = "Funcionario@123";


    @Before
    public void setUp(){
        Empresa empresa = this.empresaRepository.save(obterDadosEmpresaMock());
        this.funcionarioRepository.save(obterDadosFuncionarioMock(empresa));
    }

    @After
    public final void tearDown(){
        this.empresaRepository.deleteAll();
    }

    private Funcionario obterDadosFuncionarioMock(Empresa empresa){
        Funcionario funcionario = new Funcionario();
        funcionario.setCpf(CPF);
        funcionario.setNome(NOME);
        funcionario.setPerfilEnum(PERFIL);
        funcionario.setSenha(SENHA);
        funcionario.setEmail(EMAIL);
        funcionario.setEmpresa(empresa);
        return funcionario;
    }

    private Empresa obterDadosEmpresaMock(){
        Empresa empresa = new Empresa();
        empresa.setCnpj(CNPJ);
        empresa.setRazaoSocial(RAZAO_SOCIAL);
        return empresa;
    }

    @Test
    public void findFuncionarioByEmail(){
        Funcionario funcionario = this.funcionarioRepository.findByEmail(EMAIL);
        Assert.assertEquals(EMAIL, funcionario.getEmail());
    }

    @Test
    public void findFuncionarioByCpf(){
        Funcionario funcionario = this.funcionarioRepository.findByCpf(CPF);
        Assert.assertEquals(CPF, funcionario.getCpf());
    }

    @Test
    public void findFuncionarioByEmailAndCpf(){
        Funcionario funcionario = this.funcionarioRepository.findByCpfOrEmail(CPF, EMAIL);
        Assert.assertEquals(EMAIL, funcionario.getEmail());
        Assert.assertEquals(CPF, funcionario.getCpf());
    }

    @Test
    public void findFuncionarioByEmailAndInvalidCpf(){
        Funcionario funcionario = this.funcionarioRepository.findByCpfOrEmail(INVALID_CPF, EMAIL);
        Assert.assertNotNull(funcionario);
    }

    @Test
    public void findFuncionarioByEmailAndCpfInvalidEmail(){
        Funcionario funcionario = this.funcionarioRepository.findByCpfOrEmail(CPF, INVALID_EMAIL);
        Assert.assertNotNull(funcionario);
    }

    @Test
    public void findFuncionarioByEmailAndCpfInvalidEmailAndInvalidCpf(){
        Funcionario funcionario = this.funcionarioRepository.findByCpfOrEmail(INVALID_CPF, INVALID_EMAIL);
        Assert.assertNull(funcionario);
    }
}
