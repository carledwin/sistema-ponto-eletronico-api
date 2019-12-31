package com.wordpress.carledwinti.sistema.ponto.eletronico.api.repositories;

import com.wordpress.carledwinti.sistema.ponto.eletronico.api.entities.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly=true)
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    Funcionario findByCpf(String cpf);
    List<Funcionario> findByEmpresaId(Long id);
    Funcionario findByEmail(String email);
    Funcionario findByCpfOrEmail(String cpf, String email);
}
