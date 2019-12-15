package com.wordpress.carledwinti.sistema.ponto.eletronico.api.controllers;

import com.wordpress.carledwinti.sistema.ponto.eletronico.api.dtos.CadastroPFDto;
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
import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("/cadastro-pf")
@CrossOrigin(origins = "*")
public class CadastroPFController {

    private static final Logger LOG = LoggerFactory.getLogger(CadastroPFController.class);

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private FuncionarioService funcionarioService;

    @PostMapping
    public ResponseEntity<Response<CadastroPFDto>> save(@Valid @RequestBody CadastroPFDto cadastroPFDto, BindingResult bindingResult){

        Response<CadastroPFDto> responseCadastroPFDto = new Response<>();

        validateExistingData(cadastroPFDto, bindingResult);

        Funcionario funcionario = converterCadastroPFDtoToFuncionario(cadastroPFDto);

        if(bindingResult.hasErrors()){

            LOG.info("Errors encountered while validating data: {}", bindingResult.getAllErrors());

            bindingResult.getAllErrors().forEach(
                    error -> responseCadastroPFDto.getErrors().add(error.getDefaultMessage())
            );

            return ResponseEntity.badRequest().body(responseCadastroPFDto);
        }

        Optional<Empresa> optionalEmpresa = empresaService.findByCnpj(cadastroPFDto.getCnpj());
        optionalEmpresa.ifPresent(empresa -> funcionario.setEmpresa(empresa));

        funcionarioService.save(funcionario);

        responseCadastroPFDto.setData(converterFuncionarioToCadastroPFDto(funcionario));

        return ResponseEntity.ok(responseCadastroPFDto);
    }

    private CadastroPFDto converterFuncionarioToCadastroPFDto(Funcionario funcionario) {

        CadastroPFDto cadastroPFDto = new CadastroPFDto();
        cadastroPFDto.setId(funcionario.getId());
        cadastroPFDto.setNome(funcionario.getNome());
        cadastroPFDto.setEmail(funcionario.getEmail());
        cadastroPFDto.setCpf(funcionario.getCpf());
        cadastroPFDto.setCnpj(funcionario.getEmpresa().getCnpj());

        if(funcionario.getQtdHorasTrabalhoDiaOpt().isPresent()){
            cadastroPFDto.setQtdHorasTrabalhadasDia(Optional.of(funcionario.getQtdHorasTrabalhoDia().toString()));
        }

        if(funcionario.getQtdHorasAlmocoOpt().isPresent()){
            cadastroPFDto.setQtdHorasAlmocoDia(Optional.of(funcionario.getQtdHorasAlmoco().toString()));
        }

        if(funcionario.getValorHoraOpt().isPresent()){
            cadastroPFDto.setValorHora(Optional.of(funcionario.getValorHora().toString()));
        }
        /*funcionario.getQtdHorasAlmocoOpt()
                .isPresent(qtdHorasAlmoco -> cadastroPFDto.setQtdHorasAlmocoDia(Optional.of(Float.toString(qtdHorasAlmoco))));

        funcionario.getQtdHorasTrabalhoDiaOpt()
                .ifPresent(qtdHorasTrabalhoDia -> cadastroPFDto.setQtdHorasTrabalhadasDia(Optional.of(Float.toString(qtdHorasTrabalhoDia))));
                funcionario.getValorHoraOpt()
                        .isPresent( valorHora -> cadastroPFDto.setValorHora(Optional.of(valorHora.toString())));*/
        return cadastroPFDto;
    }

    private Funcionario converterCadastroPFDtoToFuncionario(CadastroPFDto cadastroPFDto) {

        Funcionario funcionario = new Funcionario();
        funcionario.setNome(cadastroPFDto.getNome());
        funcionario.setEmail(cadastroPFDto.getEmail());
        funcionario.setCpf(cadastroPFDto.getCpf());
        funcionario.setPerfilEnum(PerfilEnum.ROLE_USER);
        funcionario.setSenha(PasswordUtils.getNewBCrypt(cadastroPFDto.getSenha()));

        if(cadastroPFDto.getQtdHorasAlmocoDia().isPresent()){
            funcionario.setQtdHorasAlmoco(Float.valueOf(cadastroPFDto.getQtdHorasAlmocoDia().toString()));
        }

        if(cadastroPFDto.getQtdHorasTrabalhadasDia().isPresent()){
            funcionario.setQtdHorasTrabalhoDia(Float.valueOf(cadastroPFDto.getQtdHorasTrabalhadasDia().toString()));
        }

        if(cadastroPFDto.getValorHora().isPresent()){
            funcionario.setValorHora(new BigDecimal(cadastroPFDto.getValorHora().get()));
        }

        /*cadastroPFDto.getQtdHorasAlmocoDia()
                .isPresent(qtdHorasAlmocoDia -> funcionario.setQtdHorasAlmoco(Float.valueOf(qtdHorasAlmocoDia)));
        cadastroPFDto.getQtdHorasTrabalhadasDia()
                .isPresent(qtHorasTrabalhoDia -> funcionario.setQtdHorasTrabalhoDia(Float.valueOf(qtHorasTrabalhoDia)));
        cadastroPFDto.getValorHora()
                .isPresent(valorHora -> funcionario.setValorHora(new BigDecimal(valorHora.toString())));*/
        return funcionario;
    }

    private void validateExistingData(CadastroPFDto cadastroPFDto, BindingResult bindingResult){

        Optional<Empresa> empresaOptional = empresaService.findByCnpj(cadastroPFDto.getCnpj());

        if(!empresaOptional.isPresent()){
            bindingResult.addError(new ObjectError("empresa", "The Empresa is not registered"));
        }

        funcionarioService.findByCpf(cadastroPFDto.getCpf()).ifPresent(
                funcionario -> bindingResult.addError(new ObjectError("funcionario", "Cpf do funcionario already exisits")));

        funcionarioService.findByEmail(cadastroPFDto.getEmail()).ifPresent(
                funcionario -> bindingResult.addError(new ObjectError("funcionario", "Email do funcionario already exisits")));
    }
}
