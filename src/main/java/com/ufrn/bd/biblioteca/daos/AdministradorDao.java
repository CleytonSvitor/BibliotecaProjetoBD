package com.ufrn.bd.biblioteca.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import com.ufrn.bd.biblioteca.models.Administrador;
import com.ufrn.bd.biblioteca.models.Usuario;

public class AdministradorDao extends AbstractDao{

	public static boolean cadastrarAdministrador(Administrador administrador) {
		try {
			
			StringBuffer sql = new StringBuffer();
			sql.append("INSERT INTO administrador ");
			sql.append("(usuario_pessoa_id) ");	
			sql.append("VALUES (?)");
			
			PreparedStatement statement = ConexaoBanco.conexaoComBancoMySQL().prepareStatement(sql.toString());
			statement.setInt(1, administrador.getUsuario().getPessoa().getId());
			
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
	
	public static Administrador buscarAdministradororPorIdPessoa(int idPessoa) {
		try {
			
			Usuario usuarioResultado = UsuarioDao.buscarUsuarioPorIdPessoa(idPessoa);
			
			if(!Objects.isNull(usuarioResultado)) {
			
				StringBuffer sql = new StringBuffer();
				sql.append("SELECT * FROM administrador ad ");
				sql.append("WHERE ad.usuario_pessoa_id = ?");
				
				PreparedStatement statement = ConexaoBanco.conexaoComBancoMySQL().prepareStatement(sql.toString());
				statement.setInt(1, idPessoa);
	
				ResultSet resultadoBusca = statement.executeQuery();
				
				if(resultadoBusca.next()) {
					Administrador administradorResultado = new Administrador();
					administradorResultado.setUsuario(usuarioResultado);
					
					return administradorResultado;
				}
			}
			return null;
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
