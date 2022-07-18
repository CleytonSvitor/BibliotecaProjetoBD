package com.ufrn.bd.biblioteca.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ufrn.bd.biblioteca.models.Genero;

public class GeneroDao extends AbstractDao{
	
	public static boolean cadastrarGenero(Genero genero) {
		try {
			
			StringBuffer sql = new StringBuffer();
			sql.append("INSERT INTO genero ");
			sql.append("(nome) ");	
			sql.append("VALUES (?)");
			
			PreparedStatement statement = ConexaoBanco.conexaoComBancoMySQL().prepareStatement(sql.toString());
			statement.setString(1, genero.getNome());
			
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
	
	public static Genero buscarGeneroPeloNome (String nomeGenero) {
		try {
			
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT * FROM genero g ");
			sql.append("WHERE g.nome = ?");
			
			PreparedStatement statement = ConexaoBanco.conexaoComBancoMySQL().prepareStatement(sql.toString());
			statement.setString(1, nomeGenero);

			ResultSet resultadoBusca = statement.executeQuery();
			
			if(resultadoBusca.next()) {
				Genero generoResultado = new Genero();
				generoResultado.setNome(resultadoBusca.getString("g.nome"));
				
				return generoResultado;
			}
			return null;
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
