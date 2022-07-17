package com.ufrn.bd.biblioteca.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import com.ufrn.bd.biblioteca.models.CEP;
import com.ufrn.bd.biblioteca.models.Endereco;
import com.ufrn.bd.biblioteca.models.EnderecoPessoa;

public class EnderecoDao {
	
	public static boolean cadastrarEndereco(Endereco endereco) {
		try {
			
			StringBuffer sql = new StringBuffer();
			sql.append("INSERT INTO endereco ");
			sql.append("(numero, cep) ");
			sql.append(" VALUES (?, ?)");

			PreparedStatement statement = ConexaoBanco.conexaoComBancoMySQL().prepareStatement(sql.toString());
			statement.setString(1, endereco.getNumero());
			
			if(Objects.isNull(CEPDao.buscarCEPPeloCEP(endereco.getCep().getCep()))) {
				if(!CEPDao.cadastrarCEP(endereco.getCep())) {
					return false;
				}
			}
			
			statement.setString(2, endereco.getCep().getCep());
			
			
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
	
	public static boolean cadastarEnderecoPessoa(EnderecoPessoa enderecoPessoa) {
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("INSERT INTO endereco_pessoa ");
			sql.append("(endereco_id) ");
			sql.append(" VALUES (?)");

			PreparedStatement statement = ConexaoBanco.conexaoComBancoMySQL().prepareStatement(sql.toString());
			
			if(Objects.isNull(buscarEnderecoPeloId(enderecoPessoa.getEndereco().getId()))) {
				if(Objects.isNull(buscarIdEndereco(enderecoPessoa.getEndereco()))) {
					if(!cadastrarEndereco(enderecoPessoa.getEndereco())) {
						return false;
					}
				}
				
				Integer idEndereco = buscarIdEndereco(enderecoPessoa.getEndereco());
				
				statement.setInt(1, idEndereco);
				
			}else {
				statement.setInt(1, enderecoPessoa.getEndereco().getId());
			}
			
			
			int linhasAlteradas = statement.executeUpdate();
			if (linhasAlteradas > 0) {
				return true;
			}
			
			return false;
			
		}catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static EnderecoPessoa buscarEnderecoPessoaPeloIdEndereco(Integer idEndereco) {

		if(!Objects.isNull(idEndereco)) {
			try {
					StringBuffer sql = new StringBuffer();
					sql.append("SELECT * FROM endereco_pessoa ep " );
					sql.append("INNER JOIN endereco e ON e.id = ep.endereco_id ");
					sql.append("INNER JOIN cep c ON e.cep = c.cep ");
					sql.append("WHERE ep.endereco_id = ?");
					
					PreparedStatement statement = ConexaoBanco.conexaoComBancoMySQL().prepareStatement(sql.toString());
					statement.setInt(1, idEndereco);
					
					ResultSet resultadoBusca = statement.executeQuery();
					
					if (resultadoBusca.next()) {
						EnderecoPessoa enderecoPessoa = new EnderecoPessoa();
						Endereco endereco = new Endereco(resultadoBusca.getInt("e.id"), resultadoBusca.getString("e.numero"));
						
						CEP cep = new CEP(resultadoBusca.getString("c.cep"), 
								resultadoBusca.getString("c.logradouro"), 
								resultadoBusca.getString("c.cidade"), 
								resultadoBusca.getString("c.estado"));
						endereco.setCep(cep);
						
						enderecoPessoa.setEndereco(endereco);
						
						return enderecoPessoa;
					}
				
				
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}
	
	public static Endereco buscarEnderecoPeloId(Integer idEndereco) {
		
		if(!Objects.isNull(idEndereco)) {
			try {
				StringBuffer sql = new StringBuffer();
				sql.append("SELECT * FROM endereco e " );
				sql.append("INNER JOIN cep c ON e.cep = c.cep ");
				sql.append("WHERE e.id = ?");
				
				PreparedStatement statement = ConexaoBanco.conexaoComBancoMySQL().prepareStatement(sql.toString());
				statement.setInt(1, idEndereco);
				
				ResultSet resultadoBusca = statement.executeQuery();
				
				if (resultadoBusca.next()) {
					Endereco endereco = new Endereco(resultadoBusca.getInt("e.id"), resultadoBusca.getString("e.numero"));
					
					CEP cep = new CEP(resultadoBusca.getString("c.cep"), 
							resultadoBusca.getString("c.logradouro"), 
							resultadoBusca.getString("c.cidade"), 
							resultadoBusca.getString("c.estado"));
					endereco.setCep(cep);
					
					return endereco;
				}
				return null;
				
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}
	
	public static Integer buscarIdEndereco(Endereco endereco) {
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT e.id FROM endereco e ");
			sql.append("WHERE e.numero = ? AND e.cep = ?");
			
			PreparedStatement statement = ConexaoBanco.conexaoComBancoMySQL().prepareStatement(sql.toString());
			statement.setString(1, endereco.getNumero());
			statement.setString(2, endereco.getCep().getCep());
			
			ResultSet resultadoBusca = statement.executeQuery();
			
			if (resultadoBusca.next()) {
				
				Integer idEndereco = resultadoBusca.getInt("e.id");
				
				return idEndereco;
			}
			
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Integer buscarIdEnderecoPessoa(EnderecoPessoa enderecoPessoa) {
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT e.id FROM endereco e ");
			sql.append("INNER JOIN endereco_pessoa ep ON e.id = ep.endereco_id ");
			sql.append("WHERE e.numero = ? AND e.cep = ?");
			
			PreparedStatement statement = ConexaoBanco.conexaoComBancoMySQL().prepareStatement(sql.toString());
			statement.setString(1, enderecoPessoa.getEndereco().getNumero());
			statement.setString(2, enderecoPessoa.getEndereco().getCep().getCep());
			
			ResultSet resultadoBusca = statement.executeQuery();
			
			if (resultadoBusca.next()) {
				
				Integer idEndereco = resultadoBusca.getInt("e.id");
				
				return idEndereco;
			}
			
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
