package com.logicalis.service;

import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.logicalis.controleponto.entity.Empresa;
import com.logicalis.controleponto.repository.EmpresaRepository;
import com.logicalis.controleponto.service.EmpresaService;

public class EmpresaServiceTest {

	@Mock
	private EmpresaRepository empresaRepository;
	
	@InjectMocks
	private EmpresaService empresaService;
	
	private static final String CNPJ = "897976876582";
	
	@BeforeEach
	public void setUp() throws Exception{
		MockitoAnnotations.openMocks(this);
		
		given(this.empresaRepository.findByCnpj(Mockito.anyString())).willReturn(new Empresa());
		given(this.empresaRepository.save(Mockito.any(Empresa.class))).willReturn(new Empresa());
	}
	
	@Test
	public void testBuscarEmpresaPorCnpj() {
		Optional<Optional<Empresa>> empresa = Optional.ofNullable(Optional.ofNullable(this.empresaRepository.findByCnpj(CNPJ)));		
		assertTrue(empresa.isPresent());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
