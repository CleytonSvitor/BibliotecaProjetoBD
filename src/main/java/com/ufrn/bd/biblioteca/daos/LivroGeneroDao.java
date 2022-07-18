package com.ufrn.bd.biblioteca.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.ufrn.bd.biblioteca.models.Genero;
import com.ufrn.bd.biblioteca.models.Livro;

public class LivroGeneroDao extends AbstractDao{
	
	public static boolean cadastrarGeneroEmUmLivro(Genero genero, Livro livro) {
		try {
			
			StringBuffer sql = new StringBuffer();
			sql.append("INSERT INTO livro_genero ");
			sql.append("(livro_id, genero_nome) ");	
			sql.append("VALUES (?, ?)");
			
			PreparedStatement statement = ConexaoBanco.conexaoComBancoMySQL().prepareStatement(sql.toString());
			statement.setInt(1, livro.getId());
			statement.setString(2, genero.getNome());
			
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
	
	public static ArrayList<Genero> buscarTodosGenerosDoLivro (Livro livro) {
		try {
			
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT g* FROM genero g ");
			sql.append("INNER JOIN livro_genero lg on lg.genero_nome = g.nome ");
			sql.append("INNER JOIN livro l on l.id = lg.livro_id ");
			sql.append("WHERE l.id = ?");
			
			PreparedStatement statement = ConexaoBanco.conexaoComBancoMySQL().prepareStatement(sql.toString());
			statement.setInt(1, livro.getId());

			ResultSet resultadoBusca = statement.executeQuery();
			
			ArrayList<Genero> generosLivro = new ArrayList<>();
			
			if(resultadoBusca.next()) {
				Genero generoResultado = new Genero();
				generoResultado.setNome(resultadoBusca.getString("g.nome"));
				
				generosLivro.add(generoResultado);
			}
			
			if(!generosLivro.isEmpty()) {
				return generosLivro;
			}
			
			return null;
			
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static ArrayList<Livro> buscarTodosLivrosDoGenero(Genero genero) {
		try {
			
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT l* FROM livro l ");
			sql.append("INNER JOIN livro_genero lg ON lg.livro_id = l.id ");
			sql.append("INNER JOIN genero g ON g.nome = lg.genero_nome ");
			sql.append("WHERE g.nome = ?");
			
			PreparedStatement statement = ConexaoBanco.conexaoComBancoMySQL().prepareStatement(sql.toString());
			statement.setString(1, genero.getNome());

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
