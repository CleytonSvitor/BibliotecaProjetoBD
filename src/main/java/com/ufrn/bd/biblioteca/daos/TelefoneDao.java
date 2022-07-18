package com.ufrn.bd.biblioteca.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ufrn.bd.biblioteca.models.Telefone;

public class TelefoneDao extends AbstractDao{
	
	public static boolean cadastrarTelefone(Telefone telefone) {
		try {
			
			StringBuffer sql = new StringBuffer();
			sql.append("INSERT INTO telefone ");
			sql.append("(ddd, numero) ");	
			sql.append("VALUES (?, ?)");
			
			PreparedStatement statement = ConexaoBanco.conexaoComBancoMySQL().prepareStatement(sql.toString());
			statement.setInt(1, telefone.getDdd());
			statement.setInt(2, telefone.getNumero());
			
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
	
	public static Telefone buscarTelefonePeloNumeroDDD(int ddd, int numero) {
		try {
			
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT * FROM telefone t ");
			sql.append("WHERE t.ddd = ? AND t.numero = ?");
			
			PreparedStatement statement = ConexaoBanco.conexaoComBancoMySQL().prepareStatement(sql.toString());
			statement.setInt(1, ddd);
			statement.setInt(2, numero);

			ResultSet resultadoBusca = statement.executeQuery();
			
			if(resultadoBusca.next()) {
				Telefone telefone = new Telefone();
				telefone.setDdd(resultadoBusca.getInt("t.ddd"));
				telefone.setNumero(resultadoBusca.getInt("t.numero"));
				
				return telefone;
			}
			return null;
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
