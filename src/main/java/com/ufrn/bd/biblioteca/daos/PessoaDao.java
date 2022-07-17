package com.ufrn.bd.biblioteca.daos;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ufrn.bd.biblioteca.models.Pessoa;

public class PessoaDao  extends AbstractDao{
	
	public static boolean cadastrarPessoa(Pessoa pessoa) {
		
		try {
			
			StringBuffer sql = new StringBuffer();
			sql.append("INSERT INTO pessoa ");
			sql.append("(nome_completo, data_nascimento, cpf, endereco_pessoa_id) ");	
			sql.append("VALUES (?, ?, ?, ?)");
			
			PreparedStatement statement = ConexaoBanco.conexaoComBancoMySQL().prepareStatement(sql.toString());
			statement.setString(1, pessoa.getNomeCompleto());
			statement.setDate(2, new Date(pessoa.getDataNascimento().getTime()));
			statement.setString(3, pessoa.getCpf());
			statement.setInt(4, pessoa.getEnderecoPessoa().getEndereco().getId());
			
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
	
	public static Pessoa buscarPessoaPorCPF(String cpf) {
		
		try {
			
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT * FROM pessoa p ");
			sql.append("WHERE p.cpf = ?");
			
			PreparedStatement statement = ConexaoBanco.conexaoComBancoMySQL().prepareStatement(sql.toString());
			statement.setString(1, cpf);

			ResultSet resultadoBusca = statement.executeQuery();

			if (resultadoBusca.next()) {
				Pessoa pessoa = new Pessoa(resultadoBusca.getInt("p.id"),
						resultadoBusca.getString("p.nome_completo"), 
						new java.util.Date(resultadoBusca.getDate("p.data_nascimento").getTime()), 
						resultadoBusca.getString("p.cpf"));
				pessoa.setEnderecoPessoa(EnderecoDao.buscarEnderecoPessoaPeloIdEndereco(resultadoBusca.getInt("p.endereco_pessoa_id")));

				return pessoa;
			}
			return null;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Pessoa buscarPessoaPorId(int idPessoa) {
		
		try {
			
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT * FROM pessoa p ");
			sql.append("WHERE p.id = ?");
			
			PreparedStatement statement = ConexaoBanco.conexaoComBancoMySQL().prepareStatement(sql.toString());
			statement.setInt(1, idPessoa);

			ResultSet resultadoBusca = statement.executeQuery();

			if (resultadoBusca.next()) {
				Pessoa pessoa = new Pessoa(resultadoBusca.getInt("p.id"),
						resultadoBusca.getString("p.nome_completo"), 
						new java.util.Date(resultadoBusca.getDate("p.data_nascimento").getTime()), 
						resultadoBusca.getString("p.cpf"));
				pessoa.setEnderecoPessoa(EnderecoDao.buscarEnderecoPessoaPeloIdEndereco(resultadoBusca.getInt("p.endereco_pessoa_id")));

				return pessoa;
			}
			return null;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
