package com.logicalis.controleponto.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.logicalis.controleponto.entity.Empresa;
import com.logicalis.controleponto.entity.Lancamentos;

@NamedQueries({
	@NamedQuery(name = "LancamentoRepository.findByFuncionariosId",
			query = "SELECT lanc FROM Lancamento lanc WHERE lanc.funcionarios.id = :funcionarioId")})
public interface LancamentoRepository extends JpaRepository<Lancamentos, Long> {
	
	List<Lancamentos> findByFuncionariosId(@Param("funcionarioId") Long funcionarioId);
	Page<Lancamentos> findByFuncionariosId(@Param("funcionarioId") Long funcionarioId, Pageable pegeable);
	Optional<Lancamentos> findById(Long id);

}
