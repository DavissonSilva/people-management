package com.logicalis.controleponto.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.logicalis.controleponto.entity.Empresa;

@Service
public interface EmpresaService {
	
	/**
	 * Retorna uma empresa dado um CNPJ
	 * 
	 * @param cnpj
	 * @return Optional<Empresa>
	 */
	
	Optional<Empresa> buscarPorCnpj(String cnpj);
	
	Empresa persistir(Empresa empresa);
}
