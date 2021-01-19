package com.logicalis.controleponto.dto;

import java.util.Optional;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CadastroPFdto {
	
	private Long id;
	@NotEmpty(message = "Nome não pode ser vazio.")
	@Length(min = 3, max = 200, message = "Nome deve conter entre 3 e 200 caracteres.")
	private String nome;
	
	@NotEmpty(message = "Email não pode ser vazio")
	@Length(min = 3, max = 200, message = "Email deve conter entre 5 a 200 caracteres")
	@Email(message = "Email não é valido")
	private String email;
	
	@NotEmpty(message = "Senha não pode ser vazio")
	private String senha;
	
	@NotEmpty(message = "CPF não pode ser vazio")
	@CPF
	private String cpf;
	
	private Optional<String> valorHora = Optional.empty();
	private Optional<String> qtdHorasTrabalhoDia = Optional.empty();
	private Optional<String> qtdHorasAlmoco = Optional.empty();
	
	@NotEmpty(message = "CNPJ não pode ser vazio")
	@CNPJ
	private String cnpj;

	@Override
	public String toString() {
		return "CadastroPFdto [id=" + id + ", nome=" + nome + ", email=" + email + ", senha=" + senha + ", cpf=" + cpf
				+ ", valorHora=" + valorHora + ", qtdHorasTrabalhoDia=" + qtdHorasTrabalhoDia + ", qtdHorasAlmoco="
				+ qtdHorasAlmoco + ", cnpj=" + cnpj + "]";
	}
	
	
}
