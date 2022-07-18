package com.ufrn.bd.biblioteca.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ufrn.bd.biblioteca.models.TelefonePessoa;

public class TelefonePessoaDao extends AbstractDao{
	
	public static boolean cadastrarTelefonePessoa(TelefonePessoa telefonePessoa) {
		
		try {
			
			StringBuffer sql = new StringBuffer();
			sql.append("INSERT INTO telefone_pessoa ");
			sql.append("(telefone_ddd, telefone_numero) ");	
			sql.append("VALUES (?, ?)");
			
			PreparedStatement statement = ConexaoBanco.conexaoComBancoMySQL().prepareStatement(sql.toString());
			statement.setInt(1, telefonePessoa.getTelefone().getDdd());
			statement.setInt(2, telefonePessoa.getTelefone().getNumero());
			
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
	
	public static TelefonePessoa buscarTelefonePessoaPorDddNumero(int ddd, int numero) {
		
		try {
			
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT * FROM telefone_pessoa tp ");
			sql.append("WHERE tp.telefone_ddd = ? AND tp.telefone_numero = ?");
			
			PreparedStatement statement = ConexaoBanco.conexaoComBancoMySQL().prepareStatement(sql.toString());
			statement.setInt(1, ddd);
			statement.setInt(2, numero);

			ResultSet resultadoBusca = statement.executeQuery();

			if (resultadoBusca.next()) {
				TelefonePessoa telefonePessoa = new TelefonePessoa();
				telefonePessoa.setTelefone(TelefoneDao.buscarTelefonePeloNumeroDDD(resultadoBusca.getInt("tp.telefone_ddd"), resultadoBusca.getInt("tp.telefone_numero")));
				
				
				return telefonePessoa;
			}
			return null;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
