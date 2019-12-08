package com.wordpress.carledwinti.sistema.ponto.eletronico.api.controllers;

import com.wordpress.carledwinti.sistema.ponto.eletronico.api.dtos.CadastroPJDto;
import com.wordpress.carledwinti.sistema.ponto.eletronico.api.entities.Empresa;
import com.wordpress.carledwinti.sistema.ponto.eletronico.api.entities.Funcionario;
import com.wordpress.carledwinti.sistema.ponto.eletronico.api.enums.PerfilEnum;
import com.wordpress.carledwinti.sistema.ponto.eletronico.api.responses.Response;
import com.wordpress.carledwinti.sistema.ponto.eletronico.api.services.EmpresaService;
import com.wordpress.carledwinti.sistema.ponto.eletronico.api.services.FuncionarioService;
import com.wordpress.carledwinti.sistema.ponto.eletronico.api.utils.PasswordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/cadastro-pj")
@CrossOrigin(origins = "*")
public class CadastroPJController {

    private static final Logger LOG = LoggerFactory.getLogger(CadastroPJController.class);

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private FuncionarioService funcionarioService;

    @PostMapping
    public ResponseEntity<Response<CadastroPJDto>> save(@Valid @RequestBody CadastroPJDto cadastroPJDto, BindingResult bindingResult){

        Response<CadastroPJDto> responseCadastroPJDto = new Response<>();

        validateExistingData(cadastroPJDto, bindingResult);

        Empresa empresa = converterCadastroPJDtoToEmpresa(cadastroPJDto);

        Funcionario funcionario = converterCadastroPJDtoToFuncionario(cadastroPJDto);

        if(bindingResult.hasErrors()){

            LOG.info("Errors encountered while validating data: {}", bindingResult.getAllErrors());

            bindingResult.getAllErrors().forEach(
                    error -> responseCadastroPJDto.getErrors().add(error.getDefaultMessage())
            );

            return ResponseEntity.badRequest().body(responseCadastroPJDto);
        }

        empresaService.save(empresa);

        funcionario.setEmpresa(empresa);

        funcionarioService.save(funcionario);

        responseCadastroPJDto.setData(converterFuncionarioToCadastroPJDto(funcionario));

        return ResponseEntity.ok(responseCadastroPJDto);
    }

    private CadastroPJDto converterFuncionarioToCadastroPJDto(Funcionario funcionario) {

        CadastroPJDto cadastroPJDto = new CadastroPJDto();
        cadastroPJDto.setId(funcionario.getId());
        cadastroPJDto.setNome(funcionario.getNome());
        cadastroPJDto.setEmail(funcionario.getEmail());
        cadastroPJDto.setCpf(funcionario.getCpf());
        cadastroPJDto.setRazaoSocial(funcionario.getEmpresa().getRazaoSocial());
        cadastroPJDto.setCnpj(funcionario.getEmpresa().getCnpj());

        return cadastroPJDto;
    }

    private Funcionario converterCadastroPJDtoToFuncionario(CadastroPJDto cadastroPJDto) {

        Funcionario funcionario = new Funcionario();
        funcionario.setNome(cadastroPJDto.getNome());
        funcionario.setEmail(cadastroPJDto.getEmail());
        funcionario.setCpf(cadastroPJDto.getCpf());
        funcionario.setPerfilEnum(PerfilEnum.ROLE_ADMIN);
        funcionario.setSenha(PasswordUtils.getNewBCrypt(cadastroPJDto.getSenha()));

        return funcionario;
    }

    private Empresa converterCadastroPJDtoToEmpresa(CadastroPJDto cadastroPJDto) {

        Empresa empresa = new Empresa();
        empresa.setRazaoSocial(cadastroPJDto.getRazaoSocial());
        empresa.setCnpj(cadastroPJDto.getCnpj());

        return empresa;
    }

    private void validateExistingData(CadastroPJDto cadastroPJDto, BindingResult bindingResult){

        empresaService.findByCnpj(cadastroPJDto.getCnpj()).ifPresent(
                empresa -> bindingResult.addError(new ObjectError("empresa", "Empresa alredy existis")));

        funcionarioService.findByCpf(cadastroPJDto.getCpf()).ifPresent(
                funcionario -> bindingResult.addError(new ObjectError("funcionario", "Cpf do funcionario already exisits")));

        funcionarioService.findByEmail(cadastroPJDto.getEmail()).ifPresent(
                funcionario -> bindingResult.addError(new ObjectError("funcionario", "Email do funcionario already exisits")));
    }

}
