package com.logicalis.controleponto.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.logicalis.controleponto.entity.Lancamentos;

@Service
public interface LacamentoService {
	
	/**
	 * Retorna uma lista paginada de lançamentos de um determinado funcionario 
	 * @param funcionario
	 * @param pageRequest
	 * @return
	 */
	
	Page<Lancamentos> buscarPorFuncionarioId(long funcionarioId, PageRequest pageRequest);
	
	/**
	 * Persiste um lancamento na base de dados
	 * 
	 * @param id
	 * @return Optional<Lancamento>
	 */
	Optional<Lancamentos> buscaPorId(Long id);
	
	/**
	 * Persiste um lancamento na base de dados
	 * 
	 * @param lancamento
	 * @return lancamento
	 */
	Lancamentos persistir(Lancamentos lancamento);
	/**
	 * Remover um lancamento da base de dados
	 * 
	 * @param id
	 */
	void remover(Long id);
}
