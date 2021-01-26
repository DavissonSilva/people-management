package com.logicalis.controleponto.resource;

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
@RequestMapping(value = "logicalis/empresa")
@CrossOrigin(origins ="*")
public class FuncionarioResource {

	private static final Logger log = LoggerFactory.getLogger(EmpresaServiceImpl.class);
	
	@Autowired
	private FuncionarioService funcionarioService;
	
	public FuncionarioResource() {
	}
	
	@PutMapping(value = "/{cpf}")
	public ResponseEntity<Response<FuncionarioDto>> atualizar(@PathVariable("id") String cpf, @RequestBody FuncionarioDto funcionarioDto,
			BindingResult bindingResult) throws NoSuchAlgorithmException{

		log.info("Buscando Empresa por CNPJ: {}", funcionarioDto.toString());
		
		Response<FuncionarioDto> response = new Response<FuncionarioDto>();
		
		Optional<Funcionario> funcionario = this.funcionarioService.buscarPorCpf(cpf);
		
		if(!funcionario.isPresent()) {
			bindingResult.addError(new ObjectError("Funcionario", "Funcionario não encontrado."));
			
		}
		
		return null;
	}
}
