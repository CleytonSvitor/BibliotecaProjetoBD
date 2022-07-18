package com.ufrn.bd.biblioteca.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import com.ufrn.bd.biblioteca.models.Usuario;
import com.ufrn.bd.biblioteca.models.UsuarioComum;

public class UsuarioComumDao extends AbstractDao{
	
	public static boolean cadastrarUsuarioComum(UsuarioComum usuarioComum) {
		try {
			
			StringBuffer sql = new StringBuffer();
			sql.append("INSERT INTO usuario_comum ");
			sql.append("(usuario_pessoa_id, bloqueado) ");	
			sql.append("VALUES (?, ?)");
			
			PreparedStatement statement = ConexaoBanco.conexaoComBancoMySQL().prepareStatement(sql.toString());
			statement.setInt(1, usuarioComum.getUsuario().getPessoa().getId());
			statement.setBoolean(2, false);
			
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
	
	public static boolean bloquearUsuario(UsuarioComum usuarioComum) {
		try {
			
			StringBuffer sql = new StringBuffer();
			sql.append("UPDATE usuario_comum ");
			sql.append("SET bloqueado = ? ");
			sql.append("WHERE usuario_pessoa_id = ? ");
			
			PreparedStatement statement = ConexaoBanco.conexaoComBancoMySQL().prepareStatement(sql.toString());
			statement.setBoolean(1, true);
			statement.setInt(2, usuarioComum.getUsuario().getPessoa().getId());
			
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
	
	public static boolean desbloquear(UsuarioComum usuarioComum) {
		try {
			
			StringBuffer sql = new StringBuffer();
			sql.append("UPDATE usuario_comum ");
			sql.append("SET bloqueado = ? ");
			sql.append("WHERE usuario_pessoa_id = ? ");
			
			PreparedStatement statement = ConexaoBanco.conexaoComBancoMySQL().prepareStatement(sql.toString());
			statement.setBoolean(1, false);
			statement.setInt(2, usuarioComum.getUsuario().getPessoa().getId());
			
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
	
	public static UsuarioComum buscarUsuarioComumPorIdPessoa(int idPessoa) {
		try {
			
			Usuario usuarioResultado = UsuarioDao.buscarUsuarioPorIdPessoa(idPessoa);
			
			if(!Objects.isNull(usuarioResultado)) {
			
				StringBuffer sql = new StringBuffer();
				sql.append("SELECT * FROM usuario_comum uc ");
				sql.append("WHERE uc.usuario_pessoa_id = ?");
				
				PreparedStatement statement = ConexaoBanco.conexaoComBancoMySQL().prepareStatement(sql.toString());
				statement.setInt(1, idPessoa);
	
				ResultSet resultadoBusca = statement.executeQuery();
				
				if(resultadoBusca.next()) {
					UsuarioComum usuarioComumResultado = new UsuarioComum();
					usuarioComumResultado.setBloqueado(resultadoBusca.getBoolean("uc.bloqueado"));
					usuarioComumResultado.setUsuario(usuarioResultado);
					
					return usuarioComumResultado;
				}
				
			}
			
			return null;
			
			
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
}
