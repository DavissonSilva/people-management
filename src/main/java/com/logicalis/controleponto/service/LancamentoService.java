package com.logicalis.controleponto.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.logicalis.controleponto.entity.Lancamentos;

@Service
public interface LancamentoService {

	Page<Lancamentos> buscarPorFuncionarioId(Long funcionario, Pageable pageable);

	Optional<Lancamentos> buscarporId(Long id);
	
	Lancamentos persistir(Lancamentos lancamentos);
	
	void remover(Long id);
	
}
