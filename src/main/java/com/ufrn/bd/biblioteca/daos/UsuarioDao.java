package com.ufrn.bd.biblioteca.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ufrn.bd.biblioteca.models.Usuario;

public class UsuarioDao extends AbstractDao{
	
	public static boolean cadastrarUsuario(Usuario usuario) {
		
		try {
			
			StringBuffer sql = new StringBuffer();
			sql.append("INSERT INTO usuario ");
			sql.append("(username, email, senha, pessoa_id) ");	
			sql.append("VALUES (?, ?, ?, ?)");
			
			PreparedStatement statement = ConexaoBanco.conexaoComBancoMySQL().prepareStatement(sql.toString());
			statement.setString(1, usuario.getUsername());
			statement.setString(2, usuario.getEmail());
			statement.setString(3, usuario.getSenha());
			statement.setInt(4, usuario.getPessoa().getId());
			
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
	
	public static Usuario buscarUsuarioPorIdPessoa(int idPessoa) {
		
		try {
			
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT * FROM usuario u ");
			sql.append("WHERE u.pessoa_id = ?");
			
			PreparedStatement statement = ConexaoBanco.conexaoComBancoMySQL().prepareStatement(sql.toString());
			statement.setInt(1, idPessoa);

			ResultSet resultadoBusca = statement.executeQuery();

			if (resultadoBusca.next()) {
				Usuario usuario = new Usuario(resultadoBusca.getString("u.username"),
						resultadoBusca.getString("u.email"), 
						resultadoBusca.getString("u.senha"));
				usuario.setPessoa(PessoaDao.buscarPessoaPorId(resultadoBusca.getInt("u.pessoa_id")));
				
				return usuario;
			}
			return null;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Usuario buscarUsuarioPorSenhaUsername(Usuario usuario) {
		
		try {
			
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT * FROM usuario u ");
			sql.append("WHERE u.username = ? AND u.senha = ?");
			
			PreparedStatement statement = ConexaoBanco.conexaoComBancoMySQL().prepareStatement(sql.toString());
			statement.setString(1, usuario.getUsername());
			statement.setString(2, usuario.getSenha());

			ResultSet resultadoBusca = statement.executeQuery();

			if (resultadoBusca.next()) {
				Usuario usuarioResultado = new Usuario(resultadoBusca.getString("u.username"),
						resultadoBusca.getString("u.email"), 
						resultadoBusca.getString("u.senha"));
				usuarioResultado.setPessoa(PessoaDao.buscarPessoaPorId(resultadoBusca.getInt("u.pessoa_id")));
				
				return usuarioResultado;
			}
			return null;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
