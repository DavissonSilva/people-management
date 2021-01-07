package com.logicalis.controleponto.implementandoService;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.logicalis.controleponto.entity.Funcionario;
import com.logicalis.controleponto.repository.FuncionarioRepository;
import com.logicalis.controleponto.service.FuncionarioService;

public class FuncionarioServiceImpl implements FuncionarioService{
	private static final Logger log = LoggerFactory.getLogger(EmpresaServiceImpl.class);
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	@Override
	public Funcionario persistir(Funcionario funcionario) {
		log.info("Persistindo Funcionario: {}", funcionario);

		return this.funcionarioRepository.save(funcionario);
	}

	@Override
	public Optional<Funcionario> buscarPorCpf(String cpf) {
		log.info("Buscando Funcionario por cpf: {}", cpf);
		return Optional.ofNullable(this.funcionarioRepository.findByCpf(cpf));
	}

	@Override
	public Optional<Funcionario> buscarPorEmail(String email) {
		log.info("Buscando Funcionario por email: {}", email);
		return Optional.ofNullable(this.funcionarioRepository.findByEmail(email));
		
	}
	
}
