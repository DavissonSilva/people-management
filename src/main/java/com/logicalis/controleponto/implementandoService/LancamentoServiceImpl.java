package com.logicalis.controleponto.implementandoService;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.logicalis.controleponto.entity.Lancamentos;
import com.logicalis.controleponto.repository.LancamentoRepository;
import com.logicalis.controleponto.service.LacamentoService;

public class LancamentoServiceImpl implements LacamentoService {

	private static final Logger log = LoggerFactory.getLogger(EmpresaServiceImpl.class);
	
	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Override
	public Page<Lancamentos> buscarPorFuncionarioId(long funcionarioId, PageRequest pageRequest) {
		log.info("Buscando lançamentos para o funcionario ID{}", pageRequest);
		return this.lancamentoRepository.findByFuncionariosId(funcionarioId, pageRequest);
	}

//	@Override
//	public Optional<Lancamentos> buscaPorId(Long id) {
//		log.info("Buscando um lancamento por id{}", id);
//		return Optional.ofNullable(this.lancamentoRepository.findOne(id));
//	}

	@Override
	public Lancamentos persistir(Lancamentos lancamento) {
		log.info("persistindo o lançamento{}", lancamento);
		return this.lancamentoRepository.save(lancamento);
	}

//	@Override
//	public void remover(Long id) {
//		log.info("Removendo o lançamento ID{}", id);
//		this.lancamentoRepository.delete(id);
//	}

}
