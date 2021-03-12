package com.logicalis.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.logicalis.controleponto.entity.Empresa;
import com.logicalis.controleponto.repository.EmpresaRepository;

public class EmpresaRepositoryTest {

	private static final String CNPJ = "04.232.671.0004-81";
	private static final String RAZAO_SOCIAL = "Tecnologia LDTA";

	@Mock(answer = Answers.RETURNS_SMART_NULLS)
	EmpresaRepository empresaRepository;

	Empresa empresa;

	@BeforeEach
	public void iniciandoCenarios() {
		MockitoAnnotations.openMocks(this);

		empresa = new Empresa();
		empresa.setCnpj(CNPJ);
		empresa.setRazaoSocial(RAZAO_SOCIAL);
		empresaRepository.save(empresa);
		
	}

	@Test
	void testBuscarPorCnpj() {
		Empresa empresa = empresaRepository.findByCnpj(CNPJ);
		System.out.println(empresa.getCnpj());
	}

}
