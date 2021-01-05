package com.logicalis.controleponto.implementandoService;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.logicalis.controleponto.entity.Empresa;
import com.logicalis.controleponto.repository.EmpresaRepository;
import com.logicalis.controleponto.service.EmpresaService;
@Service
public class EmpresaServiceImpl implements EmpresaService{
	
	private static final Logger log = LoggerFactory.getLogger(EmpresaServiceImpl.class);
	@Autowired
	EmpresaRepository empresaRepository;
	
	@Override
	public Optional<Empresa> buscarPorCnpj(String cnpj) {
		log.info("Buscar uma empresa para o CNPJ{}", cnpj);		
		return Optional.ofNullable(empresaRepository.findByCnpj(cnpj));
		
	}

	@Override
	public Empresa persistir(Empresa empresa) {
		log.info("Persistindo empresa: {}", empresa);
		return this.empresaRepository.save(empresa);
	}

}
