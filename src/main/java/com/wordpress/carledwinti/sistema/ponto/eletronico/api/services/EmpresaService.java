package com.wordpress.carledwinti.sistema.ponto.eletronico.api.services;

import com.wordpress.carledwinti.sistema.ponto.eletronico.api.entities.Empresa;

import java.util.Optional;

public interface EmpresaService {

    Optional<Empresa> findByCnpj(String cnpj);
    Empresa save(Empresa empresa);
}
