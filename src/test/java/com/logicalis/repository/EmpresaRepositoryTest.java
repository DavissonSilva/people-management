package com.logicalis.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import com.logicalis.controleponto.entity.Empresa;
import com.logicalis.controleponto.repository.EmpresaRepository;

@RunWith(SpringRunner.class)
public class EmpresaRepositoryTest {

	@Autowired
	EmpresaRepository empresaRepository;
	private static final String razaoSocial ="NEGOCIOS E TECNOLOGIA DA INFORMACAO LTDA";
	private static final String cnpj = "04.232.671.0004-81";
	
	@Before
	public void setUp() {
		Empresa empresa = new Empresa();
		empresa.setRazaoSocial(razaoSocial);
		empresa.setCnpj(cnpj);
		this.empresaRepository.save(empresa);	
		
	}
	@After
	public final void tearDown() {
		this.empresaRepository.deleteAll();
	}
	
	@Test
	public final void testBuscarPorCnpj() {
		Empresa empresa = this.empresaRepository.findByCnpj(cnpj);
		assertEquals(cnpj, empresa.getCnpj());
	}
	
	 
}
