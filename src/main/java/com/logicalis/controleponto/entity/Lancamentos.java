package com.logicalis.controleponto.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.logicalis.controleponto.enums.TipoEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Lancamentos extends AbstractEntity{

	@Temporal(TemporalType.TIMESTAMP)
	private Date data;
	
	private String descricao;
	private String localizacao;
	private Date dataCriacao;
	private Date dataAtualizacao;
	@Enumerated(EnumType.STRING)
	private TipoEnum tipo;
	@ManyToOne(fetch = FetchType.EAGER)
	private Funcionario funcionarios;
	
	public Lancamentos() {
	}
	
	@PreUpdate
	public void preUpdate(){
		this.dataAtualizacao = new Date();
	}
	
	@PrePersist
	public void prePersist() {
		final Date atual = new Date();
		this.dataAtualizacao = atual;
		this.dataCriacao = atual;
	}

	
}