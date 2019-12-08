package com.wordpress.carledwinti.sistema.ponto.eletronico.api.controllers;

import com.wordpress.carledwinti.sistema.ponto.eletronico.api.dtos.FuncionarioDto;
import com.wordpress.carledwinti.sistema.ponto.eletronico.api.entities.Funcionario;
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
@RequestMapping("/funcionarios")
@CrossOrigin(origins = "*")
public class FuncionarioController {


    private static final Logger LOG = LoggerFactory.getLogger(CadastroPFController.class);

    @Autowired
    private FuncionarioService funcionarioService;

    @PutMapping("/{id}")
    public ResponseEntity<Response<FuncionarioDto>> update(@PathVariable("id") Long id, @Valid @RequestBody FuncionarioDto funcionarioDto, BindingResult bindingResult){

        Response<FuncionarioDto> responseFuncionarioDto = new Response<>();

        Optional<Funcionario> optionalFuncionario = funcionarioService.findById(id);

        if (!optionalFuncionario.isPresent()){
            bindingResult.addError(new ObjectError("funcionario", "Funcionario Not Found"));
        }

        updateFuncionarioData(optionalFuncionario.get(), funcionarioDto, bindingResult);

        if(bindingResult.hasErrors()){

            LOG.info("Error on validing Funcionario: {}", bindingResult.getAllErrors());

            bindingResult.getAllErrors()
                    .forEach(error -> responseFuncionarioDto.getErrors().add(error.getDefaultMessage()));

            return ResponseEntity.badRequest().body(responseFuncionarioDto);
        }

        funcionarioService.save(optionalFuncionario.get());

        responseFuncionarioDto.setData(converterFuncionarioToFuncionarioDto(optionalFuncionario.get()));

        return ResponseEntity.ok(responseFuncionarioDto);
    }

    private FuncionarioDto converterFuncionarioToFuncionarioDto(Funcionario funcionario) {

        FuncionarioDto funcionarioDto = new FuncionarioDto();

        funcionarioDto.setId(funcionario.getId());
        funcionarioDto.setEmail(funcionario.getEmail());
        funcionarioDto.setNome(funcionario.getNome());

        funcionario.getQtdHorasAlmocoOpt()
                .ifPresent( qtdHorasAlmoco -> funcionarioDto.setQtdHorasAlmoco(Optional.of(Float.toString(qtdHorasAlmoco))));
        funcionario.getQtdHorasTrabalhoDiaOpt()
                .ifPresent(qtdHorasTrabahoDia -> funcionarioDto.setQtdHorasTrabalhoDia(Optional.of(Float.toString(qtdHorasTrabahoDia))));
        funcionario.getValorHoraOpt()
                .ifPresent(valorHora -> funcionarioDto.setValorHora(Optional.of(valorHora.toString())));

        return funcionarioDto;
    }

    private void updateFuncionarioData(Funcionario funcionario, FuncionarioDto funcionarioDto, BindingResult bindingResult) {

        funcionario.setNome(funcionarioDto.getNome());

        if(!funcionario.getEmail().equals(funcionarioDto.getEmail())){

            funcionarioService.findByEmail(funcionarioDto.getEmail())
                    .ifPresent(func -> bindingResult.addError(new ObjectError("funcionario", "Email already exists")));

            funcionario.setEmail(funcionarioDto.getEmail());
        }

        funcionario.setQtdHorasAlmoco(null);
        funcionarioDto.getQtdHorasAlmoco()
                .ifPresent(qtdHorasAlmoco -> funcionario.setQtdHorasAlmoco(Float.valueOf(qtdHorasAlmoco)));

        funcionario.setQtdHorasTrabalhoDia(null);
        funcionarioDto.getQtdHorasTrabalhoDia()
                .ifPresent(qtdHorasTrabalhoDia -> funcionario.setQtdHorasTrabalhoDia(Float.valueOf(qtdHorasTrabalhoDia)));

        funcionario.setValorHora(null);
        funcionarioDto.getValorHora()
                .ifPresent(valorHora -> funcionario.setValorHora(new BigDecimal(valorHora)));

        funcionarioDto.getSenha()
                .ifPresent(senha -> funcionario.setSenha(PasswordUtils.getNewBCrypt(senha)));
    }

}
