package com.ufrn.bd.biblioteca.models;

import java.util.Date;

public class Pessoa {
	private int id;
	private String nomeCompleto;
	private Date dataNascimento;
	private String cpf;
	private EnderecoPessoa enderecoPessoa;
	
	public Pessoa() {
		
	}
	
	public Pessoa(int id, String nomeCompleto, Date dataNascimento, String cpf) {
		super();
		this.id = id;
		this.nomeCompleto = nomeCompleto;
		this.dataNascimento = dataNascimento;
		this.cpf = cpf;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNomeCompleto() {
		return nomeCompleto;
	}
	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}
	public Date getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public EnderecoPessoa getEnderecoPessoa() {
		return enderecoPessoa;
	}
	public void setEnderecoPessoa(EnderecoPessoa enderecoPessoa) {
		this.enderecoPessoa = enderecoPessoa;
	}
	
}
