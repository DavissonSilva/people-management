package com.logicalis.controleponto.resource;

import java.text.SimpleDateFormat;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.logicalis.controleponto.dto.LancamendoDto;
import com.logicalis.controleponto.entity.Lancamentos;
import com.logicalis.controleponto.implementandoService.EmpresaServiceImpl;
import com.logicalis.controleponto.response.Response;
import com.logicalis.controleponto.service.FuncionarioService;
import com.logicalis.controleponto.service.LancamentoService;

@RestController
@RequestMapping(value = "logicalis/lancamentos")
@CrossOrigin(origins ="*")
public class LancamentosResource {

	private static final Logger LOG = LoggerFactory.getLogger(EmpresaServiceImpl.class);
	
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
	
	@Autowired
	private LancamentoService lancamentoService;
	
	@Autowired
	private FuncionarioService funcionarioService;
	
	@Value("${paginacao.qtd_por_pagina}")
	private int qtdPorpagina;
	
	public LancamentosResource() {
	}
	
	@GetMapping(value = "/funcionario/{funcionarioId}")
	public ResponseEntity<Response<Page<LancamendoDto>>> ListarPorFuncionarioId(
			@PathVariable("funcionarioId") Long funcionarioId,
			Pageable pageable){
		LOG.info("Buscando lancamento por ID do funcionario:{}, pagina:{} ", funcionarioId,pageable);
		
		Response<Page<LancamendoDto>> response = new Response<Page<LancamendoDto>>();		
		
		Page<Lancamentos> lancamentos = this.lancamentoService.buscarPorFuncionarioId(funcionarioId, pageable);
		Page<LancamendoDto> lancamendoDto = lancamentos.map(lancamento -> this.converterLancamentoDto(lancamento));
		
		response.setData(lancamendoDto);
		return ResponseEntity.ok(response);
	}
	
	private LancamendoDto converterLancamentoDto(Lancamentos lancamentos) {
		LancamendoDto dto = new LancamendoDto();
		
		dto.setData(this.DATE_FORMAT.format(lancamentos.getData()));
		dto.setId(Optional.of(lancamentos.getId()));
		dto.setTipo(lancamentos.getTipo().toString());
		dto.setDescricao(lancamentos.getDescricao());
		dto.setLocalizacao(lancamentos.getLocalizacao());
		dto.setFuncionarioId(lancamentos.getFuncionarios().getId());
		return dto;
	}
}
