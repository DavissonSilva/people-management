package com.logicalis.controleponto.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;

import com.logicalis.controleponto.enums.PerfilEnum;
import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
public class Funcionario extends AbstractEntity{

	@NotNull
	private String nome;
	@NotNull
	private String email;
	@NotNull
	private String senha;
	@NotNull
	private String cpf;	
	@NotNull
	private BigDecimal valorHora;
	@NotNull
	private Float qtdHorasTrabalhoDia;
	@NotNull
	private Float qtdHorasAlmoco;

	@Enumerated(EnumType.STRING)
	private PerfilEnum perfil;
	private Date dataCriacao;
	private Date dataAtualizacao;
	@ManyToOne(fetch =FetchType.EAGER)
	private Empresa empresa;
	@OneToMany(mappedBy = "funcionarios", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Lancamentos> lancamentos;
	
	
	public Funcionario() {
	}
	
	
//	@Transient
//	private Optional<Float> qtdHorasAlmocoOpt = Optional.empty();
//	@Transient
//	private Optional<Float> qtdHorasTrabalhoDiaOpt = Optional.empty();
//	@Transient
//	private Optional<Float> ValorHoraOpt = Optional.empty();
//	
	
	@Transient
	public Optional<BigDecimal> getValorHoraOpt(){
		return Optional.ofNullable(this.valorHora);
	}
	
	@Transient
	public Optional<Float> getQtdHorasAlmocoOpt(){
		return Optional.ofNullable(this.qtdHorasAlmoco);
	}
	
	public Optional<Float> getQtdHorasTrabalhoDiaOpt(){
		
		return Optional.ofNullable(this.qtdHorasTrabalhoDia);
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
