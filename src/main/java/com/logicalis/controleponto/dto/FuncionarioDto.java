package com.logicalis.controleponto.dto;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class FuncionarioDto {
	
	private Long id;
	
//	@NotEmpty(message = "Nome não pode ser vazio")
//	@Length(min = 3, max = 200, message = "Nome deve conter entre 3 e 200 caracteres.")
	private String nome;
	
//	@NotEmpty(message = "E-mail não pode ser vazio")
//	@Length(min = 10, max = 100, message = "E-mail deve conter entre 10 e 100 caracteres.")
//	@Email()
	private String email;
	
	private Optional<String> senha = Optional.empty();
	private Optional<String> valorHora = Optional.empty();
	private Optional<String> qtdHorasTrabalhoDia = Optional.empty();
	private Optional<String> qtdHorasAlmoco = Optional.empty();

	public FuncionarioDto() {
	}

	@Override
	public String toString() {
		return "FuncionarioDto [id=" + id + ", nome=" + nome + ", email=" + email + ", senha=" + senha + ", valorHora="
				+ valorHora + ", qtdHorasTrabalhoDia=" + qtdHorasTrabalhoDia + ", qtdHorasAlmoco=" + qtdHorasAlmoco
				+ "]";
	}
	
	
}
