package com.wordpress.carledwinti.sistema.ponto.eletronico.api.repositories;

import com.wordpress.carledwinti.sistema.ponto.eletronico.api.entities.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

    @Transactional(readOnly=true)
    Empresa findByCnpj(String cnpj);
}
