package com.wordpress.carledwinti.sistema.ponto.eletronico.api.controllers;

import com.wordpress.carledwinti.sistema.ponto.eletronico.api.dtos.EmpresaDto;
import com.wordpress.carledwinti.sistema.ponto.eletronico.api.entities.Empresa;
import com.wordpress.carledwinti.sistema.ponto.eletronico.api.responses.Response;
import com.wordpress.carledwinti.sistema.ponto.eletronico.api.services.EmpresaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/empresas")
@CrossOrigin(origins = "*")
public class EmpresaController {

    private static final Logger LOG = LoggerFactory.getLogger(CadastroPFController.class);

    @Autowired
    private EmpresaService empresaService;

    @GetMapping("/cnpj/{cnpj}")
    public ResponseEntity<Response<EmpresaDto>> findByCNPJ(@PathVariable("cnpj") String cnpj){

        Response<EmpresaDto> responseEmpresaDto = new Response<>();

        Optional<Empresa> optionalEmpresa = empresaService.findByCnpj(cnpj);

        if(!optionalEmpresa.isPresent()){

            responseEmpresaDto.getErrors().add("Empresa Not Fond to Cnpj: " + cnpj);

            return ResponseEntity.badRequest().body(responseEmpresaDto);
        }

        responseEmpresaDto.setData(converterEmpresaDto(optionalEmpresa.get()));
        return ResponseEntity.ok(responseEmpresaDto);
    }

    private EmpresaDto converterEmpresaDto(Empresa empresa) {

        EmpresaDto empresaDto = new EmpresaDto();
        empresaDto.setCnpj(empresa.getCnpj());
        empresaDto.setId(empresa.getId());
        empresaDto.setRazaoSocial(empresa.getRazaoSocial());

        return empresaDto;
    }
}
