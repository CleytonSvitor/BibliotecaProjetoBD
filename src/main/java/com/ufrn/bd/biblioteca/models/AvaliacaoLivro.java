package com.ufrn.bd.biblioteca.models;

public class AvaliacaoLivro {
	private UsuarioComum usuarioComum;
	private Livro livro;
	private int nota;
	private String comentario;
	
	public UsuarioComum getUsuarioComum() {
		return usuarioComum;
	}
	public void setUsuarioComum(UsuarioComum usuarioComum) {
		this.usuarioComum = usuarioComum;
	}
	public Livro getLivro() {
		return livro;
	}
	public void setLivro(Livro livro) {
		this.livro = livro;
	}
	public int getNota() {
		return nota;
	}
	public void setNota(int nota) {
		this.nota = nota;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	
	
}
