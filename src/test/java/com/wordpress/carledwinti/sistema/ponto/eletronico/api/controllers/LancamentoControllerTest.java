package com.wordpress.carledwinti.sistema.ponto.eletronico.api.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wordpress.carledwinti.sistema.ponto.eletronico.api.dtos.LancamentoDto;
import com.wordpress.carledwinti.sistema.ponto.eletronico.api.entities.Funcionario;
import com.wordpress.carledwinti.sistema.ponto.eletronico.api.entities.Lancamento;
import com.wordpress.carledwinti.sistema.ponto.eletronico.api.enums.LancamentoEnum;
import com.wordpress.carledwinti.sistema.ponto.eletronico.api.services.FuncionarioService;
import com.wordpress.carledwinti.sistema.ponto.eletronico.api.services.LancamentoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class LancamentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FuncionarioService funcionarioService;

    @MockBean
    private LancamentoService lancamentoService;

    private static final String URL_LANCAMENTOS = "/lancamentos";
    private static final Long ID_FUNCIONARIO_MOCK = 1L;
    private static final Long ID_LANCAMENTO_MOCK = 1L;
    private static final String TIPO_INICIO_TRABALHO = LancamentoEnum.INICIO_TRABALHO.name();
    private static final Date DATA = new Date();

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Test
    @WithMockUser
    public void saveLancamentoTest() throws Exception {

        Lancamento lancamento = getDataLancamentoMock();

        BDDMockito.given(funcionarioService.findById(Mockito.anyLong())).willReturn(Optional.of(new Funcionario()));
        BDDMockito.given(lancamentoService.save(Mockito.any(Lancamento.class))).willReturn(Optional.of(lancamento));

        mockMvc.perform(MockMvcRequestBuilders
                            .post(URL_LANCAMENTOS)
                            .content(getJsonRequestPost())
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(ID_LANCAMENTO_MOCK))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.tipo").value(TIPO_INICIO_TRABALHO))
               // .andExpect(MockMvcResultMatchers.jsonPath("$.data.data").value(DATA))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.funcionarioId").value(ID_FUNCIONARIO_MOCK))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").isEmpty());
    }

    @Test
    @WithMockUser
    public void saveLancamentoInvalidFuncionarioTest() throws Exception {

        Lancamento lancamento = getDataLancamentoMock();

        BDDMockito.given(funcionarioService.findById(Mockito.anyLong())).willReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders
                .post(URL_LANCAMENTOS)
                .content(getJsonRequestPost())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").value("Funcionario informed not found"));
    }

    @Test
    @WithMockUser(username = "admin@admin.com", roles = {"ADMIN"})
    public void removeLancamentoTest() throws Exception {

        BDDMockito.given(lancamentoService.findById(Mockito.anyLong())).willReturn(Optional.of(new Lancamento()));

        mockMvc.perform(MockMvcRequestBuilders
                .delete(URL_LANCAMENTOS + "/" + ID_LANCAMENTO_MOCK)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    private String getJsonRequestPost() throws JsonProcessingException {

        LancamentoDto lancamentoDto = new LancamentoDto();
        lancamentoDto.setId(null);
        lancamentoDto.setData(simpleDateFormat.format(DATA));
        lancamentoDto.setTipo(TIPO_INICIO_TRABALHO);
        lancamentoDto.setFuncionarioId(ID_FUNCIONARIO_MOCK);

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(lancamentoDto);
    }

    private Lancamento getDataLancamentoMock() {

        Lancamento lancamento = new Lancamento();
        lancamento.setId(ID_LANCAMENTO_MOCK);
        lancamento.setData(DATA);
        lancamento.setLancamentoEnum(LancamentoEnum.valueOf(TIPO_INICIO_TRABALHO));
        lancamento.setFuncionario(new Funcionario());
        lancamento.getFuncionario().setId(ID_FUNCIONARIO_MOCK);

        return lancamento;
    }

}
