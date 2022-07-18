package com.ufrn.bd.biblioteca.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

import com.ufrn.bd.biblioteca.models.AvaliacaoLivro;
import com.ufrn.bd.biblioteca.models.Livro;
import com.ufrn.bd.biblioteca.models.UsuarioComum;

public class AvaliacaoLivroDao extends AbstractDao{
	
	public static boolean cadastrarAvaliacaoLivro(AvaliacaoLivro avaliacaoLivro) {
		try {
			
			StringBuffer sql = new StringBuffer();
			sql.append("INSERT INTO usuario_comum_avalia_livro ");
			sql.append("(usuario_comum_id, livro_id, nota, comentario) ");	
			sql.append("VALUES (?, ?, ?, ?)");
			
			PreparedStatement statement = ConexaoBanco.conexaoComBancoMySQL().prepareStatement(sql.toString());
			statement.setInt(1, avaliacaoLivro.getUsuarioComum().getUsuario().getPessoa().getId());
			statement.setInt(2, avaliacaoLivro.getLivro().getId());
			statement.setInt(3, avaliacaoLivro.getNota());
			
			if(!Objects.isNull(avaliacaoLivro.getComentario()) || avaliacaoLivro.getComentario().equals("")) {
				statement.setString(4, null);
			}else {
				statement.setString(4, avaliacaoLivro.getComentario());
			}
			
			
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
	
	public static AvaliacaoLivro buscarAvaliacaoLivro(AvaliacaoLivro avaliacaoLivro) {
		try {
			
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT * FROM usuario_comum_avalia_livro acal ");
			sql.append("WHERE acal.livro_id = ? AND acal.usuario_comum_id = ?");
			
			PreparedStatement statement = ConexaoBanco.conexaoComBancoMySQL().prepareStatement(sql.toString());
			statement.setInt(1, avaliacaoLivro.getLivro().getId());
			statement.setInt(2, avaliacaoLivro.getUsuarioComum().getUsuario().getPessoa().getId());

			ResultSet resultadoBusca = statement.executeQuery();
			
			if(resultadoBusca.next()) {
				AvaliacaoLivro avaliacaoLivroResultado = new AvaliacaoLivro();
				avaliacaoLivroResultado.setComentario(resultadoBusca.getString("acal.comentario"));
				avaliacaoLivroResultado.setNota(resultadoBusca.getInt("acal.nota"));
				avaliacaoLivroResultado.setUsuarioComum(UsuarioComumDao.buscarUsuarioComumPorIdPessoa(resultadoBusca.getInt("acal.usuario_comum_id")));
				avaliacaoLivroResultado.setLivro(LivroDao.buscarLivroPeloId(resultadoBusca.getInt("acal.livro_id")));
				
				return avaliacaoLivroResultado;
			}

			
			return null;
			
			
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static ArrayList<AvaliacaoLivro> buscarTodasAvaliacoesDoLivro(Livro livro) {
		try {
			
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT * FROM usuario_comum_avalia_livro acal ");
			sql.append("WHERE acal.livro_id = ?");
			
			PreparedStatement statement = ConexaoBanco.conexaoComBancoMySQL().prepareStatement(sql.toString());
			statement.setInt(1, livro.getId());

			ResultSet resultadoBusca = statement.executeQuery();
			
			ArrayList<AvaliacaoLivro> listaAvaliacoes = new ArrayList<>();
			
			if(resultadoBusca.next()) {
				AvaliacaoLivro avaliacaoLivroResultado = new AvaliacaoLivro();
				avaliacaoLivroResultado.setComentario(resultadoBusca.getString("acal.comentario"));
				avaliacaoLivroResultado.setNota(resultadoBusca.getInt("acal.nota"));
				avaliacaoLivroResultado.setUsuarioComum(UsuarioComumDao.buscarUsuarioComumPorIdPessoa(resultadoBusca.getInt("acal.usuario_comum_id")));
				avaliacaoLivroResultado.setLivro(LivroDao.buscarLivroPeloId(resultadoBusca.getInt("acal.livro_id")));
				
				listaAvaliacoes.add(avaliacaoLivroResultado);
			}

			if(!listaAvaliacoes.isEmpty()) {
				return listaAvaliacoes;
			}
			
			return null;
			
			
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static ArrayList<AvaliacaoLivro> buscarTodasAvaliacoesDoUsuario(UsuarioComum usuarioComum) {
		try {
			
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT * FROM usuario_comum_avalia_livro acal ");
			sql.append("WHERE acal.usuario_comum_id = ?");
			
			PreparedStatement statement = ConexaoBanco.conexaoComBancoMySQL().prepareStatement(sql.toString());
			statement.setInt(1, usuarioComum.getUsuario().getPessoa().getId());

			ResultSet resultadoBusca = statement.executeQuery();
			
			ArrayList<AvaliacaoLivro> listaAvaliacoes = new ArrayList<>();
			
			if(resultadoBusca.next()) {
				AvaliacaoLivro avaliacaoLivroResultado = new AvaliacaoLivro();
				avaliacaoLivroResultado.setComentario(resultadoBusca.getString("acal.comentario"));
				avaliacaoLivroResultado.setNota(resultadoBusca.getInt("acal.nota"));
				avaliacaoLivroResultado.setUsuarioComum(UsuarioComumDao.buscarUsuarioComumPorIdPessoa(resultadoBusca.getInt("acal.usuario_comum_id")));
				avaliacaoLivroResultado.setLivro(LivroDao.buscarLivroPeloId(resultadoBusca.getInt("acal.livro_id")));
				
				listaAvaliacoes.add(avaliacaoLivroResultado);
			}

			if(!listaAvaliacoes.isEmpty()) {
				return listaAvaliacoes;
			}
			
			return null;
			
			
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Integer buscarMediaNotasDoLivro (Livro livro) {
		try {
			
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT AVG(acal.nota) as media_nota FROM usuario_comum_avalia_livro acal ");
			sql.append("WHERE acal.livro_id = ?");
			
			PreparedStatement statement = ConexaoBanco.conexaoComBancoMySQL().prepareStatement(sql.toString());
			statement.setInt(1, livro.getId());

			ResultSet resultadoBusca = statement.executeQuery();
			
			if(resultadoBusca.next()) {
				return resultadoBusca.getInt("media_nota");
			}
			
			return null;
			
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
