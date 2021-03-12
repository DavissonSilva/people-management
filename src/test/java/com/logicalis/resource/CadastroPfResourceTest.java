package com.logicalis.resource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.BindingResult;

import com.logicalis.controleponto.dto.CadastroPFDto;
import com.logicalis.controleponto.entity.Funcionario;
import com.logicalis.controleponto.resource.CadastroPfResource;
import com.logicalis.controleponto.service.EmpresaService;
import com.logicalis.controleponto.service.FuncionarioService;

public class CadastroPfResourceTest {

	@Mock
	EmpresaService empresaService;
	
	@Mock
	FuncionarioService funcionarioService;
	
	@InjectMocks
	CadastroPfResource cadastroPfResource;
	
	CadastroPFDto cadastroPFdto;
	BindingResult bindingResult;
	Funcionario funcionario;
	
	@BeforeEach
	void init()throws Exception{
		MockitoAnnotations.openMocks(this);
		cadastroPFdto = new CadastroPFDto();
	}
	
	@Test
	void tesRota() throws Exception{
		
		cadastroPfResource.cadastrar(cadastroPFdto, bindingResult);
		
	}
	
}
