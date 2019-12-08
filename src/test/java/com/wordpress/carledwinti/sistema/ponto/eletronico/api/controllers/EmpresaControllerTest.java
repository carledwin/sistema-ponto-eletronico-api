package com.wordpress.carledwinti.sistema.ponto.eletronico.api.controllers;

import com.wordpress.carledwinti.sistema.ponto.eletronico.api.entities.Empresa;
import com.wordpress.carledwinti.sistema.ponto.eletronico.api.services.EmpresaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.swing.text.html.Option;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class EmpresaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmpresaService empresaService;

    private static final String URL_EMPRESAS = "/empresas/cnpj/";
    private static final Long ID = Long.valueOf(1);
    private static final String CNPJ_MOCK = "15566281000111";
    private static final String RAZAO_SOCIAL_MOCK = "Empresa Mock";

    @Test
    public void findByInvalidCnpj() throws Exception {

        BDDMockito.given(empresaService.findByCnpj(Mockito.anyString())).willReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get(URL_EMPRESAS + CNPJ_MOCK).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").value( "Empresa Not Fond to Cnpj: " + CNPJ_MOCK));
    }

    @Test
    public void findByCnpj() throws Exception {

        BDDMockito.given(empresaService.findByCnpj(Mockito.anyString())).willReturn(Optional.of(obterDadosEmpresa()));

        mockMvc.perform(MockMvcRequestBuilders.get(URL_EMPRESAS + CNPJ_MOCK).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(ID))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.razaoSocial", equalTo(RAZAO_SOCIAL_MOCK)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.cnpj", equalTo(CNPJ_MOCK)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").isEmpty());
    }

    private Empresa obterDadosEmpresa() {

        Empresa empresa = new Empresa();
        empresa.setId(ID);
        empresa.setCnpj(CNPJ_MOCK);
        empresa.setRazaoSocial(RAZAO_SOCIAL_MOCK);

        return empresa;
    }


}
