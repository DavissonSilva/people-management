package com.logicalis.controleponto.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.logicalis.controleponto.entity.Funcionario;

@Service
public interface FuncionarioService {
	
	/**
	 * Persiste um funcionario na base de dados.
	 * 
	 * @param funcionario
	 * @return funcionario
	 */
	Funcionario persistir(Funcionario funcionario);
	/**
	 * Busca e retorna m funcionario dado um CPF
	 * @param cpf
	 * @return Optional<Funcionario>
	 */
	Optional<Funcionario> buscarPorCpf(String cpf);
	/**
	 * Busca e retorna m funcionario dado um email
	 * @param email
	 * @return Optional<Funcionario>
	 */
	Optional<Funcionario> buscarPorEmail(String email);
	
	Optional<Funcionario> buscaPorId(Long id);



}
