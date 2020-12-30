package com.logicalis.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.logicalis.enums.PerfilEnum;
import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Funcionario  implements Serializable{

	private static final long serialVersionUID = -7716996022724918950L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)	
	private Long id;
	@NotNull
	private String nome;
	@NotNull
	private String email;
	@NotNull
	private String senha;
	@NotNull
	private String cpf;	
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

	@Override
	public String toString() {
		return "Funcionario [id=" + id + ", nome=" + nome + ", email=" + email + ", senha=" + senha + ", cpf=" + cpf
				+ ", valorHora=" + valorHora + ", qtdHorasTrabalhoDia=" + qtdHorasTrabalhoDia + ", qtdHorasAlmoco="
				+ qtdHorasAlmoco + ", perfil=" + perfil + ", dataCriacao=" + dataCriacao + ", dataAtualizacao="
				+ dataAtualizacao + ", empresa=" + empresa + "]";
	}
	
	
}
