package com.logicalis.controleponto.resource;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.logicalis.controleponto.dto.CadastroPJDto;
import com.logicalis.controleponto.entity.Empresa;
import com.logicalis.controleponto.entity.Funcionario;
import com.logicalis.controleponto.enums.PerfilEnum;
import com.logicalis.controleponto.implementandoService.EmpresaServiceImpl;
import com.logicalis.controleponto.implementandoService.FuncionarioServiceImpl;
import com.logicalis.controleponto.repository.EmpresaRepository;
import com.logicalis.controleponto.response.Response;
import com.logicalis.controleponto.service.EmpresaService;

@RestController
@RequestMapping(value = "logicalis/cadastro-PJ")
@CrossOrigin(origins = "*")
public class CadastroPJResource {
	
	private static final Logger log = LoggerFactory.getLogger(EmpresaServiceImpl.class);
	
	@Autowired
	private FuncionarioServiceImpl funcionarioService;
	
	@Autowired
	private EmpresaService empresaService;
//	@Autowired	EmpresaRepository test;
	
	@PostMapping
	public ResponseEntity<Response<CadastroPJDto>> cadastrar(@Valid @RequestBody CadastroPJDto cadastroPJDto,
			BindingResult bindingResult) throws Exception{
		
				log.info("Cadastrodo PJ: {}",cadastroPJDto.toString());
				Response<CadastroPJDto> response = new Response<CadastroPJDto>();
				
				validarDadosExistentes(cadastroPJDto, bindingResult);
				Empresa empresa = this.converterDtoparaEmpresa(cadastroPJDto);
				Funcionario funcionario = this.converterDtoparaFuncionario(cadastroPJDto, bindingResult);
				
				if(bindingResult.hasErrors()) {
					log.info("Erro validando dados de cadastro PJ:{}", bindingResult.getAllErrors());
					bindingResult.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
					return ResponseEntity.badRequest().body(response);
				}
				this.empresaService.persistir(empresa);
				funcionario.setEmpresa(empresa);
				this.funcionarioService.persistir(funcionario);
				
				response.setData(this.converterCadastroPJDto(funcionario));
		
		return null;	
		
	}	
	
	
	
//	@GetMapping
//	public List<Empresa> get() {
//		
//		return test.findAll();
//	}
	
	
	/**
	 * Verifica se a empresa ou funcionario já existem na base de dados.
	 * @param cadastroPJDto
	 * @param bindingResult
	 */
	
	private void validarDadosExistentes(CadastroPJDto cadastroPJDto, BindingResult bindingResult) {
		this.empresaService.buscarPorCnpj(cadastroPJDto.getCnpj())
		.ifPresent(emp -> bindingResult.addError(new ObjectError("empresa","Empresaa já existente.")));
		
		this.funcionarioService.buscarPorCpf(cadastroPJDto.getCpf())
		.ifPresent(fun -> bindingResult.addError(new ObjectError("funcionario","CPF já existente.")));
		
		this.funcionarioService.buscarPorEmail(cadastroPJDto.getEmail())
		.ifPresent(func -> bindingResult.addError(new ObjectError("funcionario","Email já existente.")));
	}
	
	/**
	 *  Converte o s dados do DTO para empresa 
	 * @param cadastroPJDto
	 * @return Empresa
	 */
	private Empresa converterDtoparaEmpresa(CadastroPJDto cadastroPJDto) {
		Empresa empresa = new Empresa();
		empresa.setCnpj(cadastroPJDto.getCnpj());
		empresa.setRazaoSocial(cadastroPJDto.getRazaoSocial());
		return empresa;
	}
	
	/**
	 *  Converte os dados do DTO para funcionario
	 * @param cadastroPJDto
	 * @param bindingResult
	 * @return Funcionario
	 * @throws NoSuchAlgorithmException
	 */
	private Funcionario converterDtoparaFuncionario(CadastroPJDto cadastroPJDto, BindingResult bindingResult)
			throws NoSuchAlgorithmException{
		
		Funcionario funcionario = new Funcionario();
		funcionario.setNome(cadastroPJDto.getNome());
		funcionario.setEmail(cadastroPJDto.getEmail());
		funcionario.setCpf(cadastroPJDto.getCpf());
		funcionario.setPerfil(PerfilEnum.ROLE_ADMIN);
		funcionario.setSenha(cadastroPJDto.getSenha());
		return funcionario;
	}
	/**
	 * Popula DTO de cadastro com os dados do funcionario e empresa.
	 * 
	 * @param funcionario
	 * @return
	 */
	private CadastroPJDto converterCadastroPJDto(Funcionario funcionario) {
		CadastroPJDto cadastroPJDto = new CadastroPJDto();
		cadastroPJDto.setId(funcionario.getId());
		cadastroPJDto.setNome(funcionario.getNome());
		cadastroPJDto.setEmail(funcionario.getEmail());
		cadastroPJDto.setCpf(funcionario.getCpf());
		cadastroPJDto.setRazaoSocial(funcionario.getEmpresa().getRazaoSocial());
		cadastroPJDto.setCnpj(funcionario.getEmpresa().getCnpj());
		return cadastroPJDto;
	}
}
