package com.ufrn.bd.biblioteca.daos;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.ufrn.bd.biblioteca.models.Livro;

public class LivroDao {
	
	public static boolean cadastrarLivro(Livro livro) {
		try {
			
			StringBuffer sql = new StringBuffer();
			sql.append("INSERT INTO livro ");
			sql.append("(edicao, isbn, titulo, data_lancamento, editora_id) ");	
			sql.append("VALUES (?, ?, ?, ?, ?)");
			
			PreparedStatement statement = ConexaoBanco.conexaoComBancoMySQL().prepareStatement(sql.toString());
			statement.setString(1, livro.getEdicao());
			statement.setString(2, livro.getIsbn());
			statement.setString(3, livro.getTitulo());
			statement.setDate(4, new Date(livro.getDataLancamento().getTime()));
			statement.setInt(5, livro.getEditora().getId());
			
			int linhasAlteradas = statement.executeUpdate();
			if (linhasAlteradas > 0) {
				
				return true;
			}
			
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static Livro buscarLivroPeloId(int idLivro) {
		try {
			
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT * FROM livro l ");
			sql.append("WHERE l.id = ?");
			
			PreparedStatement statement = ConexaoBanco.conexaoComBancoMySQL().prepareStatement(sql.toString());
			statement.setInt(1, idLivro);

			ResultSet resultadoBusca = statement.executeQuery();
			
			if(resultadoBusca.next()) {
				Livro livroResultado = new Livro();
				livroResultado.setId(resultadoBusca.getInt("l.id"));
				livroResultado.setEdicao(resultadoBusca.getString("l.edicao"));
				livroResultado.setIsbn(resultadoBusca.getString("l.isbn"));
				livroResultado.setTitulo(resultadoBusca.getString("l.titulo"));
				livroResultado.setDataLancamento(new java.util.Date(resultadoBusca.getDate("l.data_lancamento").getTime()));
				livroResultado.setEditora(EditoraDao.buscarEditoraPeloId(resultadoBusca.getInt("l.editora_id")));
				
				return livroResultado;
			}
			return null;
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Livro buscarLivroPeloIsbn(String isbnLivro) {
		try {
			
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT * FROM livro l ");
			sql.append("WHERE l.isbn = ?");
			
			PreparedStatement statement = ConexaoBanco.conexaoComBancoMySQL().prepareStatement(sql.toString());
			statement.setString(1, isbnLivro);

			ResultSet resultadoBusca = statement.executeQuery();
			
			if(resultadoBusca.next()) {
				Livro livroResultado = new Livro();
				livroResultado.setId(resultadoBusca.getInt("l.id"));
				livroResultado.setEdicao(resultadoBusca.getString("l.edicao"));
				livroResultado.setIsbn(resultadoBusca.getString("l.isbn"));
				livroResultado.setTitulo(resultadoBusca.getString("l.titulo"));
				livroResultado.setDataLancamento(new java.util.Date(resultadoBusca.getDate("l.data_lancamento").getTime()));
				livroResultado.setEditora(EditoraDao.buscarEditoraPeloId(resultadoBusca.getInt("l.editora_id")));
				
				return livroResultado;
			}
			return null;
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static ArrayList<Livro> buscarListaLivrosPeloTitulo(String titulo) {
		try {
			
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT * FROM livro l ");
			sql.append("WHERE l.titulo like ?");
			
			PreparedStatement statement = ConexaoBanco.conexaoComBancoMySQL().prepareStatement(sql.toString());
			statement.setString(1, titulo);

			ResultSet resultadoBusca = statement.executeQuery();
			
			ArrayList<Livro> listaLivros = new ArrayList<>();
			
			if(resultadoBusca.next()) {
				Livro livroResultado = new Livro();
				livroResultado.setId(resultadoBusca.getInt("l.id"));
				livroResultado.setEdicao(resultadoBusca.getString("l.edicao"));
				livroResultado.setIsbn(resultadoBusca.getString("l.isbn"));
				livroResultado.setTitulo(resultadoBusca.getString("l.titulo"));
				livroResultado.setDataLancamento(new java.util.Date(resultadoBusca.getDate("l.data_lancamento").getTime()));
				livroResultado.setEditora(EditoraDao.buscarEditoraPeloId(resultadoBusca.getInt("l.editora_id")));
				listaLivros.add(livroResultado);
				
			}
			System.out.println(listaLivros.size());
			if(!listaLivros.isEmpty()) {
				return listaLivros;
			}
			return null;
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static ArrayList<Livro> buscarListaLivrosPeloNomeEditora(String nomeEditora) {
		try {
			
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT * FROM livro l ");
			sql.append("INNER JOIN editora ed ON ed.id = l.editora_id ");
			sql.append("WHERE ed.nome like ?");
			
			PreparedStatement statement = ConexaoBanco.conexaoComBancoMySQL().prepareStatement(sql.toString());
			statement.setString(1, nomeEditora);

			ResultSet resultadoBusca = statement.executeQuery();
			
			ArrayList<Livro> listaLivros = new ArrayList<>();
			
			if(resultadoBusca.next()) {
				Livro livroResultado = new Livro();
				livroResultado.setId(resultadoBusca.getInt("l.id"));
				livroResultado.setEdicao(resultadoBusca.getString("l.edicao"));
				livroResultado.setIsbn(resultadoBusca.getString("l.isbn"));
				livroResultado.setTitulo(resultadoBusca.getString("l.titulo"));
				livroResultado.setDataLancamento(new java.util.Date(resultadoBusca.getDate("l.data_lancamento").getTime()));
				livroResultado.setEditora(EditoraDao.buscarEditoraPeloId(resultadoBusca.getInt("l.editora_id")));
				listaLivros.add(livroResultado);
			}
			
			if(!listaLivros.isEmpty()) {
				return listaLivros;
			}
			return null;
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static ArrayList<Livro> buscarListaLivrosPeloIdEditora(int idEditora) {
		try {
			
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT * FROM livro l ");
			sql.append("INNER JOIN editora ed ON ed.id = l.editora_id ");
			sql.append("WHERE ed.id = ?");
			
			PreparedStatement statement = ConexaoBanco.conexaoComBancoMySQL().prepareStatement(sql.toString());
			statement.setInt(1, idEditora);

			ResultSet resultadoBusca = statement.executeQuery();
			
			ArrayList<Livro> listaLivros = new ArrayList<>();
			
			if(resultadoBusca.next()) {
				Livro livroResultado = new Livro();
				livroResultado.setId(resultadoBusca.getInt("l.id"));
				livroResultado.setEdicao(resultadoBusca.getString("l.edicao"));
				livroResultado.setIsbn(resultadoBusca.getString("l.isbn"));
				livroResultado.setTitulo(resultadoBusca.getString("l.titulo"));
				livroResultado.setDataLancamento(new java.util.Date(resultadoBusca.getDate("l.data_lancamento").getTime()));
				livroResultado.setEditora(EditoraDao.buscarEditoraPeloId(resultadoBusca.getInt("l.editora_id")));
				listaLivros.add(livroResultado);
			}
			
			if(!listaLivros.isEmpty()) {
				return listaLivros;
			}
			return null;
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
