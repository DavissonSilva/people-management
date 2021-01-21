package com.logicalis.controleponto.resource;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.logicalis.controleponto.dto.CadastroPFDto;
import com.logicalis.controleponto.entity.Empresa;
import com.logicalis.controleponto.entity.Funcionario;
import com.logicalis.controleponto.enums.PerfilEnum;
import com.logicalis.controleponto.implementandoService.EmpresaServiceImpl;
import com.logicalis.controleponto.response.Response;
import com.logicalis.controleponto.service.EmpresaService;
import com.logicalis.controleponto.service.FuncionarioService;
import com.logicalis.controleponto.utils.PasswordUtils;

@RestController
@RequestMapping(value = "logicalis/cadastro-pf")
@CrossOrigin(origins = "*")
public class CadastroPfResource {

	private static final Logger log = LoggerFactory.getLogger(EmpresaServiceImpl.class);
	
	@Autowired
	private EmpresaService empresaService;
	
	@Autowired
	private FuncionarioService funcionarioService;
	
	public CadastroPfResource() {
	}
	
	@PostMapping
	public ResponseEntity<Response<CadastroPFDto>> cadastrar(@Valid @RequestBody CadastroPFDto cadastroPFdto,
			BindingResult bindingResult ) throws NoSuchAlgorithmException{
		log.info("Cadastrodo PJ: {}",cadastroPFdto.toString());
		
		Response<CadastroPFDto> response = new Response<CadastroPFDto>();
		validarDadosExistentes(cadastroPFdto, bindingResult);
		Funcionario funcionario = this.converterDtoParaFuncionario(cadastroPFdto,bindingResult);
		
		if(bindingResult.hasErrors()) {
			log.error("Error Validando dados de cadastro PF: {}", bindingResult.getAllErrors());
			bindingResult.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			
			return ResponseEntity.ok(response);
		}
		
		Optional<Empresa> empresa = this.empresaService.buscarPorCnpj(cadastroPFdto.getCnpj());
		empresa.ifPresent(emp -> funcionario.setEmpresa(emp));
		this.funcionarioService.persistir(funcionario);
		response.setData(this.converterCadastroPFDto(funcionario));
		
		return ResponseEntity.ok(response);
	}
	
	
	/**
	 * 
	 *  Verifica se a empresa ou funcionario já existem na base de dados. 
	 *  
	 * @param cadastroPFdto
	 * @param bindingResult
	 */
	
	private void validarDadosExistentes(CadastroPFDto cadastroPFdto, BindingResult bindingResult) {
		Optional<Empresa> empresa = this.empresaService.buscarPorCnpj(cadastroPFdto.getCnpj());
		
		if(!empresa.isPresent()) {
			bindingResult.addError(new ObjectError("empresa", "Empresa não cadastrada."));
		}
		
		this.funcionarioService.buscarPorCpf(cadastroPFdto.getCnpj())
			.ifPresent(func -> bindingResult.addError(new ObjectError("funcionario", "CNPJ já existente.")));
		
		this.funcionarioService.buscarPorEmail(cadastroPFdto.getEmail())
			.ifPresent(fun -> bindingResult.addError(new ObjectError("funcionario","Email já existente")));
	}
	
	/**
	 * 
	 * @param cadastroPFDto
	 * @param bindingResult
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public Funcionario converterDtoParaFuncionario(CadastroPFDto cadastroPFDto, BindingResult bindingResult) throws NoSuchAlgorithmException {
		
		Funcionario funcionario = new Funcionario();
		
		funcionario.setNome(cadastroPFDto.getNome());
		funcionario.setEmail(cadastroPFDto.getEmail());
		funcionario.setCpf(cadastroPFDto.getCpf());
		funcionario.setPerfil(PerfilEnum.ROLE_USUARIO);
		funcionario.setSenha(PasswordUtils.gerarBCrypt(cadastroPFDto.getSenha()));
		
		cadastroPFDto.getQtdHorasAlmoco()
			.ifPresent(qtdHorasAlmoco -> funcionario.setQtdHorasAlmoco(Float.valueOf(qtdHorasAlmoco)));
		
		cadastroPFDto.getQtdHorasTrabalhoDia()
			.ifPresent(qtdHorasTrabalhoDia -> funcionario.setQtdHorasTrabalhoDia(Float.valueOf(qtdHorasTrabalhoDia)));
		
		return funcionario;
	}
	
	public CadastroPFDto converterCadastroPFDto(Funcionario funcionario) {
		CadastroPFDto cadastroPFDto = new CadastroPFDto();
		
		cadastroPFDto.setId(funcionario.getId());
		cadastroPFDto.setNome(funcionario.getNome());
		cadastroPFDto.setEmail(funcionario.getEmail());
		cadastroPFDto.setCpf(funcionario.getCpf());
		cadastroPFDto.setCnpj(funcionario.getEmpresa().getCnpj());
		
		funcionario.getQtdHorasAlmocoOpt().ifPresent(qtdHorasAlmoco -> cadastroPFDto
				.setQtdHorasAlmoco(Optional.of(Float.toString(qtdHorasAlmoco))));
		
		return cadastroPFDto;
	}
	
}
