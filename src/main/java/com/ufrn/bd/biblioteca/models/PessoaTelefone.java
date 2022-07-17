package com.ufrn.bd.biblioteca.models;

public class PessoaTelefone {
	TelefonePessoa telefonePessoa;
	Pessoa pessoa;
	
	public TelefonePessoa getTelefonePessoa() {
		return telefonePessoa;
	}
	public void setTelefonePessoa(TelefonePessoa telefonePessoa) {
		this.telefonePessoa = telefonePessoa;
	}
	public Pessoa getPessoa() {
		return pessoa;
	}
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
}
