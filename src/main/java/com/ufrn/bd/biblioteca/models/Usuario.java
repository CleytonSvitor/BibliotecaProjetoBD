package com.ufrn.bd.biblioteca.models;

public class Usuario {
	private Pessoa pessoa;
	private String username;
	private String email;
	private String senha;
	
	public Usuario() {
		
	}
	
	public Usuario(String username, String email, String senha) {
		super();
		this.username = username;
		this.email = email;
		this.senha = senha;
	}
	
	public Pessoa getPessoa() {
		return pessoa;
	}
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
}
