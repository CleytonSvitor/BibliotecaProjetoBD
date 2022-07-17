package com.ufrn.bd.biblioteca.models;

public class Endereco {
	private Integer id;
	private String numero;
	private CEP cep;
	
	public Endereco() {
		
	}
	
	public Endereco(Integer id, String numero) {
		super();
		this.id = id;
		this.numero = numero;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public CEP getCep() {
		return cep;
	}
	public void setCep(CEP cep) {
		this.cep = cep;
	}
	
	
}
