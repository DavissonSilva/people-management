package com.logicalis.controleponto.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.Generated;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
public class Empresa extends AbstractEntity{


	@NotNull
	private String razaoSocial;
	@NotNull
	private String cnpj;
	@NotNull
	private Date dataCriacao;
	@NotNull
	private Date dataAtualizacao;
	@OneToMany(mappedBy = "empresa", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Funcionario> funcionario;
	
	public Empresa() {
	
	}
	public Empresa(String razaoSocial, String cnpj) {
		this.cnpj = cnpj;
		this.razaoSocial = razaoSocial;
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
