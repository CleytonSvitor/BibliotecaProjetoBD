package com.ufrn.bd.biblioteca.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ufrn.bd.biblioteca.models.CEP;

public class CEPDao extends AbstractDao{
	
	public static boolean cadastrarCEP(CEP cep) {
		try {
			
			StringBuffer sql = new StringBuffer();
			sql.append("INSERT INTO cep ");
			sql.append("(cep, logradouro, cidade, estado) ");
			sql.append(" VALUES (?, ?, ?, ?)");
			
			PreparedStatement statement = ConexaoBanco.conexaoComBancoMySQL().prepareStatement(sql.toString());
			statement.setString(1, cep.getCep());
			statement.setString(2, cep.getLogradouro());
			statement.setString(3, cep.getCidade());
			statement.setString(4, cep.getEstado());
			
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
	
	public static CEP buscarCEPPeloCEP(String cep) {
		
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT * FROM cep c WHERE c.cep = ?");
			
			PreparedStatement statement = ConexaoBanco.conexaoComBancoMySQL().prepareStatement(sql.toString());
			statement.setString(1, cep);
			
			ResultSet resultadoBusca = statement.executeQuery();
			
			if (resultadoBusca.next()) {
				CEP objCep = new CEP(resultadoBusca.getString("c.cep"), 
						resultadoBusca.getString("c.logradouro"), 
						resultadoBusca.getString("c.cidade"), 
						resultadoBusca.getString("c.estado"));
				
				return objCep;
			}
			return null;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
