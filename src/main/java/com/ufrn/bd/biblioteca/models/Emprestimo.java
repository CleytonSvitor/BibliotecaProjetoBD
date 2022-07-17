package com.ufrn.bd.biblioteca.models;

import java.util.Date;

public class Emprestimo {
	private int id;
	private Date dataDevolucao;
	private Date dataEmprestimo;
	private Date dataPrevista;
	private Livro livro;
	private UsuarioComum usuarioEmprestou;
	private UsuarioComum usuarioPegouEmprestado;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getDataDevolucao() {
		return dataDevolucao;
	}
	public void setDataDevolucao(Date dataDevolucao) {
		this.dataDevolucao = dataDevolucao;
	}
	public Date getDataEmprestimo() {
		return dataEmprestimo;
	}
	public void setDataEmprestimo(Date dataEmprestimo) {
		this.dataEmprestimo = dataEmprestimo;
	}
	public Date getDataPrevista() {
		return dataPrevista;
	}
	public void setDataPrevista(Date dataPrevista) {
		this.dataPrevista = dataPrevista;
	}
	public Livro getLivro() {
		return livro;
	}
	public void setLivro(Livro livro) {
		this.livro = livro;
	}
	public UsuarioComum getUsuarioEmprestou() {
		return usuarioEmprestou;
	}
	public void setUsuarioEmprestou(UsuarioComum usuarioEmprestou) {
		this.usuarioEmprestou = usuarioEmprestou;
	}
	public UsuarioComum getUsuarioPegouEmprestado() {
		return usuarioPegouEmprestado;
	}
	public void setUsuarioPegouEmprestado(UsuarioComum usuarioPegouEmprestado) {
		this.usuarioPegouEmprestado = usuarioPegouEmprestado;
	}
	
	
}
