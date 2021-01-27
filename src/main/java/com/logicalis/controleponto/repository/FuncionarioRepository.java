package com.logicalis.controleponto.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.logicalis.controleponto.entity.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

	Funcionario findByCpf(String cpf);
	
	Funcionario findByEmail(String email);
	
	Funcionario findByCpfOrEmail(String cpf, String email);

	Optional<Funcionario> findById(Long id);

}
