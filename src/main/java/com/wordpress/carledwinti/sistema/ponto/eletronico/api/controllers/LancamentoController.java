package com.wordpress.carledwinti.sistema.ponto.eletronico.api.controllers;

import com.wordpress.carledwinti.sistema.ponto.eletronico.api.dtos.LancamentoDto;
import com.wordpress.carledwinti.sistema.ponto.eletronico.api.entities.Funcionario;
import com.wordpress.carledwinti.sistema.ponto.eletronico.api.entities.Lancamento;
import com.wordpress.carledwinti.sistema.ponto.eletronico.api.enums.LancamentoEnum;
import com.wordpress.carledwinti.sistema.ponto.eletronico.api.responses.Response;
import com.wordpress.carledwinti.sistema.ponto.eletronico.api.services.FuncionarioService;
import com.wordpress.carledwinti.sistema.ponto.eletronico.api.services.LancamentoService;
import org.apache.commons.lang3.EnumUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import javax.validation.Valid;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

@RestController
@RequestMapping("/lancamentos")
@CrossOrigin(origins = "*")
public class LancamentoController {

    private static final Logger LOG = LoggerFactory.getLogger(LancamentoController.class);
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Value("${paginacao.qtd_por_pagina}")
    private int paginacaoQtdPorPagina;

    @Autowired
    private LancamentoService lancamentoService;

    @Autowired
    private FuncionarioService funcionarioService;

    @GetMapping("/funcionarios/{funcionarioId}")
    public ResponseEntity<Response<Page<LancamentoDto>>> findByFuncionarioId(@PathVariable("funcionarioId") Long funcionarioId,
                                                                          @RequestParam(value="page", defaultValue = "0") int page,
                                                                          @RequestParam(value="order", defaultValue = "id") String order,
                                                                          @RequestParam(value="direction", defaultValue = "DESC") String direction){

        LOG.info("Find Lancamentos By funcionarioId: {}, page: {}", funcionarioId, page);

        Response<Page<LancamentoDto>> responsePageLancamentoDto = new Response<>();

        PageRequest pageRequest = new PageRequest(page, paginacaoQtdPorPagina, Sort.Direction.valueOf(direction), order);

        Page<Lancamento> pageLancamentos = lancamentoService.findByFuncionarioId(funcionarioId, pageRequest);

        Page<LancamentoDto> pageLancamentoDto = pageLancamentos.map(lancamento -> converterLancamentoToLancamentoDto(lancamento));

        responsePageLancamentoDto.setData(pageLancamentoDto);

        return ResponseEntity.ok(responsePageLancamentoDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<LancamentoDto>> findById(@PathVariable("id") Long id){

        LOG.info("Find Lancamentos By id: {}", id);

        Response<LancamentoDto> responseLancamentoDto = new Response<>();

        Optional<Lancamento> optionalLancamento = lancamentoService.findById(id);

        if(!optionalLancamento.isPresent()){
            responseLancamentoDto.getErrors().add("Lancamento not found to id: " + id);
            return ResponseEntity.badRequest().body(responseLancamentoDto);
        }

        responseLancamentoDto.setData(converterLancamentoToLancamentoDto(optionalLancamento.get()));

        return ResponseEntity.ok(responseLancamentoDto);
    }

    @PostMapping
    public ResponseEntity<Response<LancamentoDto>> save(@Valid @RequestBody LancamentoDto lancamentoDto, BindingResult bindingResult) throws ParseException {

        LOG.info("Adding new Lancamento: {}", lancamentoDto);

        Response<LancamentoDto> responseLancamentoDto = new Response<>();

        validateFuncionario(lancamentoDto, bindingResult);

        Lancamento lancamento = converterLancamentoDtoToLancamento(lancamentoDto, bindingResult);

        if(bindingResult.hasErrors()){

            LOG.info("Error on validate funcionario: {}", bindingResult.getAllErrors());

            bindingResult.getAllErrors()
                    .forEach(error -> responseLancamentoDto.getErrors().add(error.getDefaultMessage()));

            return ResponseEntity.badRequest().body(responseLancamentoDto);
        }

        Optional<Lancamento> optionalLancamento = lancamentoService.save(lancamento);

        responseLancamentoDto.setData(converterLancamentoToLancamentoDto(optionalLancamento.get()));

        URI location = URI.create("/funcionarios/"+optionalLancamento.get().getId());
        return ResponseEntity.created(location).body(responseLancamentoDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<LancamentoDto>> update(@PathVariable("id") Long id, @Valid @RequestBody LancamentoDto lancamentoDto, BindingResult bindingResult) throws ParseException {

        LOG.info("Updating Lancamento: {}", lancamentoDto);

        Response<LancamentoDto> responseLancamentoDto = new Response<>();

        validateFuncionario(lancamentoDto, bindingResult);

        lancamentoDto.setId(Optional.of(id));

        Lancamento lancamento = converterLancamentoDtoToLancamento(lancamentoDto, bindingResult);

        if(bindingResult.hasErrors()){

            LOG.info("Error on validate funcionario: {}", bindingResult.getAllErrors());

            bindingResult.getAllErrors()
                    .forEach(error -> responseLancamentoDto.getErrors().add(error.getDefaultMessage()));

            return ResponseEntity.badRequest().body(responseLancamentoDto);
        }

        Optional<Lancamento> optionalLancamento = lancamentoService.save(lancamento);

        responseLancamentoDto.setData(converterLancamentoToLancamentoDto(optionalLancamento.get()));

        return ResponseEntity.ok(responseLancamentoDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){

        LOG.info("Removing Lancamento by id: {}", id);

        Response<String> responseString = new Response<>();

        Optional<Lancamento> optionalLancamento = lancamentoService.findById(id);

        if(!optionalLancamento.isPresent()){
            LOG.info("Error trying to remove Lancamento by id: {}, because It not found", id);
            responseString.getErrors().add("Lancamento not found");
            return ResponseEntity.badRequest().body(responseString);
        }

        lancamentoService.remove(id);

        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    private void validateFuncionario(LancamentoDto lancamentoDto, BindingResult bindingResult) {

        if(lancamentoDto.getFuncionarioId() == null){
            bindingResult.addError(new ObjectError("funcionario", "The funcionarioId is not informed"));
            return;
        }

        Optional<Funcionario> optionalFuncionario = funcionarioService.findById(lancamentoDto.getFuncionarioId());

        if(!optionalFuncionario.isPresent()){
            bindingResult.addError(new ObjectError("funcionario", "Funcionario informed not found"));
            return;
        }
    }

    private Lancamento converterLancamentoDtoToLancamento(LancamentoDto lancamentoDto, BindingResult bindingResult) throws ParseException {

        Lancamento lancamento = new Lancamento();

        if(lancamentoDto.getId().isPresent()){

            Optional<Lancamento> optionalLancamento = lancamentoService.findById(lancamentoDto.getId().get());

            if(optionalLancamento.isPresent()){
                lancamento = optionalLancamento.get();
            }else{
                bindingResult.addError(new ObjectError("lancamento", "Lancamento not found"));
            }
        }else{
            lancamento.setFuncionario(new Funcionario());
            lancamento.getFuncionario().setId(lancamentoDto.getFuncionarioId());
        }

        lancamento.setDescricao(lancamentoDto.getDescricao());
        lancamento.setLocalizacao(lancamentoDto.getLocalizacao());
        lancamento.setData(simpleDateFormat.parse(lancamentoDto.getData()));

        if(EnumUtils.isValidEnum(LancamentoEnum.class, lancamentoDto.getTipo())){
            lancamento.setLancamentoEnum(LancamentoEnum.valueOf(lancamentoDto.getTipo()));
        }else{
            bindingResult.addError(new ObjectError("lancamento", "Tipo is invalid"));
        }

        return lancamento;
    }

    private LancamentoDto converterLancamentoToLancamentoDto(Lancamento lancamento) {

        LancamentoDto lancamentoDto = new LancamentoDto();
        lancamentoDto.setId(Optional.of(lancamento.getId()));
        lancamentoDto.setData(simpleDateFormat.format(lancamento.getData()));
        lancamentoDto.setTipo(lancamento.getLancamentoEnum().toString());
        lancamentoDto.setDescricao(lancamento.getDescricao());
        lancamentoDto.setLocalizacao(lancamento.getLocalizacao());
        lancamentoDto.setFuncionarioId(lancamento.getFuncionario().getId());

        return lancamentoDto;
    }

}
