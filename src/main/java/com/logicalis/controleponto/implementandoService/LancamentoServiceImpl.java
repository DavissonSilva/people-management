package com.logicalis.controleponto.implementandoService;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.logicalis.controleponto.entity.Lancamentos;
import com.logicalis.controleponto.repository.LancamentoRepository;
import com.logicalis.controleponto.service.LancamentoService;

@Service
public class LancamentoServiceImpl implements LancamentoService {

	private static final Logger LOG = LoggerFactory.getLogger(LancamentoServiceImpl.class);

	@Autowired
	LancamentoRepository lancamentoRepository;

	@Override
	public Page<Lancamentos> buscarPorFuncionarioId(Long funcionarioId, Pageable pageable) {
		LOG.info("Buscando Lancamento para funcionario id", funcionarioId);
		return this.lancamentoRepository.findByFuncionariosId(funcionarioId, pageable);
	}

	@Override
	public Optional<Lancamentos> buscarporId(Long id) {
		LOG.info("Buscando um lancamento pelo id", id);
		return this.lancamentoRepository.findById(id);
	}

	@Override
	public Lancamentos persistir(Lancamentos lancamentos) {
		LOG.info("Salvando um lancamento", lancamentos);
		return this.lancamentoRepository.save(lancamentos);
	}

	@Override
	public void remover(Long id) {
		LOG.info("deleta um lancamento pelo id", id);
		this.lancamentoRepository.deleteById(id);
	}

}
