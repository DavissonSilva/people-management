package com.logicalis.controleponto.dto;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LancamendoDto {
	
	private Optional<Long> id = Optional.empty();
	
	private String data;
	private String tipo;
	private String descricao;
	private String localizacao;
	private Long funcionarioId;
	
	public LancamendoDto() {
	}

	@Override
	public String toString() {
		return "LancamendoDto [id=" + id + ", data=" + data + ", tipo=" + tipo + ", descricao=" + descricao
				+ ", localizacao=" + localizacao + ", funcionarioId=" + funcionarioId + "]";
	}
	
}
