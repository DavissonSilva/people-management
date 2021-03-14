package com.logicalis.controleponto.implementandoService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.logicalis.controleponto.entity.Funcionario;
import com.logicalis.controleponto.security.JwtUserFactory;
import com.logicalis.controleponto.service.FuncionarioService;

public class JwtUserDetailsServiceImpl implements UserDetailsService{
	@Autowired
	private FuncionarioService funcionarioService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Funcionario> funcionario = this.funcionarioService.buscarPorEmail(username);;
		
		if(funcionario.isPresent()) {
			return JwtUserFactory.create(funcionario.get());
		}
		throw new UsernameNotFoundException("E-mail não encontrado.");
	}

}
