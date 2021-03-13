package com.logicalis.controleponto.resource;

import java.text.SimpleDateFormat;
import java.util.Optional;

import org.apache.commons.lang3.EnumUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.logicalis.controleponto.dto.LancamendoDto;
import com.logicalis.controleponto.entity.Funcionario;
import com.logicalis.controleponto.entity.Lancamentos;
import com.logicalis.controleponto.enums.TipoEnum;
import com.logicalis.controleponto.implementandoService.EmpresaServiceImpl;
import com.logicalis.controleponto.response.Response;
import com.logicalis.controleponto.service.FuncionarioService;
import com.logicalis.controleponto.service.LancamentoService;

@RestController
@RequestMapping(value = "logicalis/lancamentos")
@CrossOrigin(origins = "*")
public class LancamentosResource {

	private static final Logger LOG = LoggerFactory.getLogger(EmpresaServiceImpl.class);

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");

	@Autowired
	private LancamentoService lancamentoService;

	@Autowired
	private FuncionarioService funcionarioService;

	public LancamentosResource() {
	}

	@GetMapping(value = "/funcionario/{funcionarioId}")
	public ResponseEntity<Response<Page<LancamendoDto>>> ListarPorFuncionarioId(
			@PathVariable("funcionarioId") Long funcionarioId, Pageable pageable) {
		LOG.info("Buscando lancamento por ID do funcionario:{}, pagina:{} ", funcionarioId, pageable);

		Response<Page<LancamendoDto>> response = new Response<Page<LancamendoDto>>();

		Page<Lancamentos> lancamentos = this.lancamentoService.buscarPorFuncionarioId(funcionarioId, pageable);
		Page<LancamendoDto> lancamendoDto = lancamentos.map(lancamento -> this.converterLancamentoDto(lancamento));

		response.setData(lancamendoDto);
		return ResponseEntity.ok(response);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Response<LancamendoDto>> listaPorId(@PathVariable("id") Long id) {
		LOG.info("Buscando lancamento por ID", id);

		Response<LancamendoDto> response = new Response<LancamendoDto>();
		Optional<Lancamentos> lancamento = this.lancamentoService.buscarporId(id);

		if (!lancamento.isPresent()) {
			LOG.info("Lancamento não encontrado para o id {}  ", id);
			response.getErrors().add("Lancamendo não encontrado para o id" + id);
			return ResponseEntity.badRequest().body(response);
		}

		response.setData(this.converterLancamentoDto(lancamento.get()));

		return ResponseEntity.ok(response);
	}

	@PostMapping
	public ResponseEntity<Response<LancamendoDto>> salvandolancamento(@RequestBody LancamendoDto dto,
			BindingResult bindingResult) throws Exception {
		LOG.info("Adicionando lançamento:{}", dto.toString());
		Response<LancamendoDto> response = new Response<LancamendoDto>();

		this.validaFuncionario(dto, bindingResult);

		Lancamentos lancamentos = this.converterDtoparaLancamento(dto, bindingResult);

		if (bindingResult.hasErrors()) {
			LOG.info("Erro validando lançamento: {}", bindingResult.getAllErrors());
			bindingResult.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		lancamentos = this.lancamentoService.persistir(lancamentos);
		response.setData(this.converterLancamentoDto(lancamentos));
		return ResponseEntity.ok(response);

	}
	
	@PutMapping(value="/{id}")
	public ResponseEntity<Response<LancamendoDto>> atualizar(@PathVariable("id") Long id,
			@RequestBody LancamendoDto dto, BindingResult bindingResult) throws Exception{
		LOG.info("Atualizando lançamento:{}", dto.toString());
		Response<LancamendoDto> response = new Response<LancamendoDto>();
		validaFuncionario(dto, bindingResult);
		dto.setId(Optional.of(id));
		Lancamentos lancamentos = this.converterDtoparaLancamento(dto, bindingResult);
		
		if (bindingResult.hasErrors()) {
			LOG.info("Erro validando lançamento: {}", bindingResult.getAllErrors());
			bindingResult.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		lancamentos = this.lancamentoService.persistir(lancamentos);
		response.setData(this.converterLancamentoDto(lancamentos));
		return ResponseEntity.ok(response);
	}

	private void validaFuncionario(LancamendoDto dto, BindingResult bindingResult) {
		if (dto.getFuncionarioId() == null) {
			bindingResult.addError(new ObjectError("Funcionario", "Funcionario não informado."));
			return;
		}
		LOG.info("Validando funcionario por id {}:", dto.getFuncionarioId());
		Optional<Funcionario> funcionario = this.funcionarioService.buscaPorId(dto.getFuncionarioId());
		if (!funcionario.isPresent()) {
			bindingResult.addError(new ObjectError("Funcionario", "Funcionario não encontrado. ID inexistente."));
		}
	}

	private Lancamentos converterDtoparaLancamento(LancamendoDto dto, BindingResult bindingResult) throws Exception {
		Lancamentos lancamentos = new Lancamentos();
		if (dto.getId().isPresent()) {
			Optional<Lancamentos> lanc = this.lancamentoService.buscarporId(dto.getId().get());
			if (lanc.isPresent()) {
				lancamentos = lanc.get();
			} else {
				bindingResult.addError(new ObjectError("Lancamento", "Lancamento não encontrado"));
			}
		} else {
			lancamentos.setFuncionarios(new Funcionario());
			lancamentos.getFuncionarios().setId(dto.getFuncionarioId());
		}
		
		lancamentos.setData(this.DATE_FORMAT.parse(dto.getData()));
		
		lancamentos.setDescricao(dto.getDescricao());
		lancamentos.setLocalizacao(dto.getLocalizacao());

		if (EnumUtils.isValidEnum(TipoEnum.class, dto.getTipo())) {
			lancamentos.setTipo(TipoEnum.valueOf(dto.getTipo()));
		} else {
			bindingResult.addError(new ObjectError("tipo", "Tipo invalido"));
		}

		return lancamentos;

	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Response<String>> remover(@PathVariable("id") Long id){
		LOG.info("Deletando por lançamento por id {}:", id);
		Response<String> response = new Response<String>();
		Optional<Lancamentos> lancamento = this.lancamentoService.buscarporId(id);
		if(!lancamento.isPresent()) {
			LOG.info("Erro ao remove devido ao lançamento. Registro não encontrado para o id:  ", id);
			response.getErrors().add("Erro ao remove devido ao lançamento. Registro não encontrado para o id" +id);
			return ResponseEntity.badRequest().body(response);
		}
		this.lancamentoService.remover(id);		
		return ResponseEntity.ok(new Response<String>());
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
