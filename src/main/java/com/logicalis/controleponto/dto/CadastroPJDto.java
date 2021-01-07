package com.logicalis.controleponto.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CadastroPJDto {

	private Long id;
	
	@NotEmpty(message = "Nome não pode ser vazio")
	@Length(min = 3, max = 200, message = "Nome deve conter entre 3 e 200 caracteres.")
	private String nome;
	
	@NotEmpty(message = "E-mail não pode ser vazio")
	@Length(min = 10, max = 100, message = "E-mail deve conter entre 10 e 100 caracteres.")
	@Email()
	private String email;
	
	@NotEmpty(message = "senha não pode ser vazio")
	@Length(min = 8, max = 30, message = "Senha deve conter entre 3 e 30 caracteres.")
	private String senha;
	
	@NotEmpty(message = "CPF não pode ser vazio")
	@CPF(message = "CPF inválido")
	private String cpf;
	
	@NotEmpty(message = "razao Social não pode ser vazio")
	@Length(min = 8, max = 200, message = "razaoSocial deve conter entre 3 e 200 caracteres.")
	private String razaoSocial;
	
	@NotEmpty(message = "CNPJ não pode ser vazio")
	@CNPJ(message = "CNPJ é invalido")
	private String cnpj;
		
	public CadastroPJDto() {
	}

	@Override
	public String toString() {
		return "CadastroPJDto [id=" + id + ", nome=" + nome + ", email=" + email + ", senha=" + senha + ", cpf=" + cpf
				+ ", razaoSocial=" + razaoSocial + ", cnpj=" + cnpj + "]";
	}
	
	
}
