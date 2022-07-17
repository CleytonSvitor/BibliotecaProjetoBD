package com.ufrn.bd.biblioteca.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ufrn.bd.biblioteca.models.Autor;

public class AutorDao extends AbstractDao{
	
	public static boolean cadastrarAutor(Autor autor) {
		
		try {
			
			StringBuffer sql = new StringBuffer();
			sql.append("INSERT INTO autor ");
			sql.append("(pessoa_id) ");	
			sql.append("VALUES (?)");
			
			PreparedStatement statement = ConexaoBanco.conexaoComBancoMySQL().prepareStatement(sql.toString());
			statement.setInt(1, autor.getPessoa().getId());
			
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
	
	public static Autor buscarAutorPorIdPessoa(int idPessoa) {
		
		try {
			
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT * FROM autor au ");
			sql.append("WHERE au.pessoa_id = ?");
			
			PreparedStatement statement = ConexaoBanco.conexaoComBancoMySQL().prepareStatement(sql.toString());
			statement.setInt(1, idPessoa);

			ResultSet resultadoBusca = statement.executeQuery();

			if (resultadoBusca.next()) {
				Autor autor = new Autor();
				autor.setPessoa(PessoaDao.buscarPessoaPorId(resultadoBusca.getInt("au.pessoa_id")));
				
				return autor;
			}
			return null;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
