package com.wordpress.carledwinti.sistema.ponto.eletronico.api.repositories;

import com.wordpress.carledwinti.sistema.ponto.eletronico.api.entities.Empresa;
import com.wordpress.carledwinti.sistema.ponto.eletronico.api.entities.Funcionario;
import com.wordpress.carledwinti.sistema.ponto.eletronico.api.entities.Lancamento;
import com.wordpress.carledwinti.sistema.ponto.eletronico.api.enums.LancamentoEnum;
import com.wordpress.carledwinti.sistema.ponto.eletronico.api.enums.PerfilEnum;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class LancamentoRepositoryTest {

    @Autowired
    private LancamentoRepository lancamentoRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    private Long funcionarioId;

    private static final String EMAIL = "funcionario.test@carledwinti.com";
    private static final String INVALID_EMAIL = "funcionario.test.invalid@carledwinti.com";
    private static final String CPF = "77125349005";
    private static final String INVALID_CPF = "12345678901";
    private static final String CNPJ = "32746367000145";
    private static final String RAZAO_SOCIAL = "Empresa Test";
    private static final PerfilEnum PERFIL = PerfilEnum.ROLE_FUNC;
    private static final String NOME = "Funcionario Test";
    private static final String SENHA = "Funcionario@123";

    @Before
    public void setUp(){

        Empresa empresa = this.empresaRepository.save(obterDadosEmpresaMock());

        Funcionario funcionario = this.funcionarioRepository.save(obterDadosFuncionarioMock(empresa));

        this.funcionarioId = funcionario.getId();

        Lancamento lancamento = this.lancamentoRepository.save(obterDadosLancamentoMock(funcionario));
        Lancamento lancamento2 = this.lancamentoRepository.save(obterDadosLancamentoMock(funcionario));
    }

    @After
    public void tearDown(){
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

    private Lancamento obterDadosLancamentoMock(Funcionario funcionario) {
        Lancamento lancamento = new Lancamento();
        lancamento.setData(new Date());
        lancamento.setLancamentoEnum(LancamentoEnum.INICIO_TRABALHO);
        lancamento.setFuncionario(funcionario);
        return lancamento;
    }

    @Test
    public void  findLancamentosByFuncionarioId(){
        List<Lancamento> lancamentos = this.lancamentoRepository.findByFuncionarioId(funcionarioId);
        Assert.assertEquals(2, lancamentos.size());
    }

    @Test
    public void  findLancamentosByFuncionarioIdPaginado(){
        PageRequest pageRequest = new PageRequest(0, 10);
        Page<Lancamento> lancamentos = this.lancamentoRepository.findByFuncionarioId(funcionarioId, pageRequest);

        Assert.assertEquals(2, lancamentos.getTotalElements());
    }
}
