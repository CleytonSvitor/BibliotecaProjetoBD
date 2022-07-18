package com.ufrn.bd.biblioteca.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.ufrn.bd.biblioteca.models.Autor;
import com.ufrn.bd.biblioteca.models.AutorLivro;
import com.ufrn.bd.biblioteca.models.Livro;

public class AutorLivroDao extends AbstractDao{
	
	public static boolean cadastrarAutorNoLivro(Autor autor, Livro livro) {
		try {
			
			StringBuffer sql = new StringBuffer();
			sql.append("INSERT INTO autor_livro ");
			sql.append("(autor_pessoa_id, livro_id) ");	
			sql.append("VALUES (?, ?)");
			
			PreparedStatement statement = ConexaoBanco.conexaoComBancoMySQL().prepareStatement(sql.toString());
			statement.setInt(1, autor.getPessoa().getId());
			statement.setInt(2, livro.getId());
			
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
	
	public static AutorLivro buscarAutorLivro (Livro livro, Autor autor) {
		try {
			
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT * from autor_livro al ");
			sql.append("WHERE al.autor_pessoa_id = ? AND al.livro_id = ?");
			
			PreparedStatement statement = ConexaoBanco.conexaoComBancoMySQL().prepareStatement(sql.toString());
			statement.setInt(1, autor.getPessoa().getId());
			statement.setInt(2, livro.getId());

			ResultSet resultadoBusca = statement.executeQuery();
			
			
			if(resultadoBusca.next()) {
				Autor autorResultado = new Autor();
				autorResultado.setPessoa(PessoaDao.buscarPessoaPorId(resultadoBusca.getInt("al.autor_pessoa_id")));
				Livro livroResultado = new Livro();
				livroResultado = LivroDao.buscarLivroPeloId(resultadoBusca.getInt("al.livro_id"));
				AutorLivro autorLivroResultado = new AutorLivro();
				autorLivroResultado.setAutor(autorResultado);
				autorLivroResultado.setLivro(livroResultado);
				
				return autorLivroResultado;
			}
			return null;
			
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static ArrayList<Autor> buscarTodosAutoresDoLivro (Livro livro) {
		try {
			
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT a.* FROM autor a ");
			sql.append("INNER JOIN autor_livro al ON al.autor_pessoa_id = a.pessoa_id ");
			sql.append("INNER JOIN livro l ON l.id = al.livro_id ");
			sql.append("WHERE l.id = ?");
			
			PreparedStatement statement = ConexaoBanco.conexaoComBancoMySQL().prepareStatement(sql.toString());
			statement.setInt(1, livro.getId());

			ResultSet resultadoBusca = statement.executeQuery();
			
			ArrayList<Autor> listaAutoresDoLivro = new ArrayList<>();
			
			if(resultadoBusca.next()) {
				Autor autorResultado = new Autor();
				autorResultado.setPessoa(PessoaDao.buscarPessoaPorId(resultadoBusca.getInt("a.pessoa_id")));
				listaAutoresDoLivro.add(autorResultado);
			}
			
			if(!listaAutoresDoLivro.isEmpty()) {
				return listaAutoresDoLivro;
			}
			
			return null;
			
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static ArrayList<Livro> buscarTodosLivrosDoAutor (Autor autor) {
		try {
			
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT l.* FROM livro l ");
			sql.append("INNER JOIN autor_livro al ON al.livro_id = l.id ");
			sql.append("INNER JOIN autor a ON a.pessoa_id = al.autor_pessoa_id ");
			sql.append("WHERE a.pessoa_id = ?");
			
			PreparedStatement statement = ConexaoBanco.conexaoComBancoMySQL().prepareStatement(sql.toString());
			statement.setInt(1, autor.getPessoa().getId());

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
