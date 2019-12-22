package com.wordpress.carledwinti.sistema.ponto.eletronico.api.controllers;

import com.wordpress.carledwinti.sistema.ponto.eletronico.api.dtos.CadastroPFDto;
import com.wordpress.carledwinti.sistema.ponto.eletronico.api.dtos.LancamentoDto;
import com.wordpress.carledwinti.sistema.ponto.eletronico.api.entities.Empresa;
import com.wordpress.carledwinti.sistema.ponto.eletronico.api.entities.Funcionario;
import com.wordpress.carledwinti.sistema.ponto.eletronico.api.entities.Lancamento;
import com.wordpress.carledwinti.sistema.ponto.eletronico.api.enums.PerfilEnum;
import com.wordpress.carledwinti.sistema.ponto.eletronico.api.responses.Response;
import com.wordpress.carledwinti.sistema.ponto.eletronico.api.services.EmpresaService;
import com.wordpress.carledwinti.sistema.ponto.eletronico.api.services.FuncionarioService;
import com.wordpress.carledwinti.sistema.ponto.eletronico.api.utils.PasswordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*")
public class HomeController {

    private static final Logger LOG = LoggerFactory.getLogger(HomeController.class);

    @GetMapping
    public ResponseEntity<String> home(){

        LOG.info("It works!");

        return ResponseEntity.ok("Sistema de Ponto Eletronico works...");
    }
}
