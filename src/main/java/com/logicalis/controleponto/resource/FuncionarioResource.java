package com.logicalis.controleponto.resource;

import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.logicalis.controleponto.dto.FuncionarioDto;
import com.logicalis.controleponto.entity.Funcionario;
import com.logicalis.controleponto.implementandoService.EmpresaServiceImpl;
import com.logicalis.controleponto.response.Response;
import com.logicalis.controleponto.service.FuncionarioService;

@RestController
@RequestMapping(value = "logicalis/funcionario")
@CrossOrigin(origins ="*")
public class FuncionarioResource {

	private static final Logger log = LoggerFactory.getLogger(EmpresaServiceImpl.class);
	
	@Autowired
	private FuncionarioService funcionarioService;
	
	public FuncionarioResource() {
	}
	
	@PutMapping(value = "id/{id}")
	public ResponseEntity<Response<FuncionarioDto>> atualizar(@PathVariable("id") Long id, @RequestBody FuncionarioDto funcionarioDto,
			BindingResult bindingResult) throws NoSuchAlgorithmException{

		log.info("Atualizando por CPF: {}", funcionarioDto.toString());
		
		Response<FuncionarioDto> response = new Response<FuncionarioDto>();
		
		Optional<Funcionario> funcionario = this.funcionarioService.buscaPorId(id);
		
		if(!funcionario.isPresent()) {
			bindingResult.addError(new ObjectError("Funcionario", "Funcionario não encontrado."));
			
		}
		this.atualizaDadosFuncionario(funcionario.get(), funcionarioDto, bindingResult);
		
		if(bindingResult.hasErrors()) {
			log.error("Error validando funcionarios: {}",bindingResult.getAllErrors());
			bindingResult.getAllErrors().forEach( error -> response.getErrors().add(error.getDefaultMessage()));
			
			return ResponseEntity.badRequest().body(response);
		}
		this.funcionarioService.persistir(funcionario.get());
		response.setData(this.converterFuncionarioDto(funcionario.get()));
		
		
		
		return ResponseEntity.ok(response);
	}
	
	public void atualizaDadosFuncionario(Funcionario funcionario, FuncionarioDto funcionarioDto,
			BindingResult bindingResult) throws NoSuchAlgorithmException{
		
		funcionario.setNome(funcionarioDto.getNome());
		
		if(!funcionario.getEmail().equals(funcionarioDto.getEmail())) {
				this.funcionarioService.buscarPorEmail(funcionario.getEmail())
					.ifPresent(func -> bindingResult.addError(new ObjectError("Email", "Email já existente")));
				funcionario.setEmail(funcionarioDto.getEmail());
		}
		funcionario.setQtdHorasAlmoco(null);
		funcionarioDto.getQtdHorasAlmoco()
			.ifPresent(qtdHorasAlmoco -> funcionario.setQtdHorasAlmoco(Float.valueOf(qtdHorasAlmoco)));
		
		funcionario.setQtdHorasTrabalhoDia(null);
		funcionarioDto.getQtdHorasTrabalhoDia()
			.ifPresent(qtdHorasTrabDia -> funcionario.setQtdHorasTrabalhoDia(Float.valueOf(qtdHorasTrabDia)));
		
		funcionario.setValorHora(null);
		funcionarioDto.getValorHora()
			.ifPresent(valorHora -> funcionario.setValorHora(new BigDecimal(valorHora)));
		
		if(funcionarioDto.getSenha().isPresent()) {
//			funcionario.setSenha(PasswordUtils.gerarBCrypt(funcionarioDto.getSenha().get()));
		}
	}
	
	private FuncionarioDto converterFuncionarioDto(Funcionario funcionario) {
		FuncionarioDto funcionarioDto = new FuncionarioDto();
		
		funcionarioDto.setId(funcionario.getId());
		funcionarioDto.setEmail(funcionario.getEmail());
		funcionarioDto.setNome(funcionario.getNome());
		
		funcionario.getQtdHorasAlmocoOpt().ifPresent(
				qtdHorasAlmoco -> funcionarioDto.setQtdHorasAlmoco(Optional.of(Float.toString(qtdHorasAlmoco))));
		
		funcionario.getQtdHorasTrabalhoDiaOpt().ifPresent(
				qtdHorasTrabalhoDia -> funcionarioDto.setQtdHorasTrabalhoDia(Optional.of(Float.toString(qtdHorasTrabalhoDia))));
		
		funcionario.getValorHoraOpt().ifPresent(
				valorHora -> funcionarioDto.setValorHora(Optional.of(valorHora.toString())));
		return funcionarioDto;
	}
}
