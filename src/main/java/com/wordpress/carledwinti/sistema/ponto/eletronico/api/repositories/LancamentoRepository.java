package com.wordpress.carledwinti.sistema.ponto.eletronico.api.repositories;

import com.wordpress.carledwinti.sistema.ponto.eletronico.api.entities.Lancamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.util.List;

@Transactional(readOnly = true)
@NamedQueries(
        @NamedQuery(name="LancamentoRepository.findByFuncionarioId",
                query = "SELECT lancamento FROM Lancamento lancamento WHERE lancamento.funcionario.id = :funcionarioId"
        )
)
public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

    List<Lancamento> findByFuncionarioId(@Param("funcionarioId") Long funcionarioId);
    Lancamento findTop1ByFuncionarioIdOrderByDataCriacaoDesc(@Param("funcionarioId") Long funcionarioId);
    Page<Lancamento> findByFuncionarioId(@Param("funcionarioId") Long funcionarioId, Pageable pageable);

}
