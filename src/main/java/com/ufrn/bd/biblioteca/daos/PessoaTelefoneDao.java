package com.ufrn.bd.biblioteca.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.ufrn.bd.biblioteca.models.Pessoa;
import com.ufrn.bd.biblioteca.models.PessoaTelefone;
import com.ufrn.bd.biblioteca.models.TelefonePessoa;

public class PessoaTelefoneDao extends AbstractDao{
	
	public static boolean cadastrarTelefonePessoaNaPessoa(TelefonePessoa telefonePessoa, Pessoa pessoa) {
		try {
			
			StringBuffer sql = new StringBuffer();
			sql.append("INSERT INTO pessoa_telefone ");
			sql.append("(telefone_pessoa_telefone_ddd, telefone_pessoa_telefone_numero, pessoa_id) ");	
			sql.append("VALUES (?, ?, ?)");
			
			PreparedStatement statement = ConexaoBanco.conexaoComBancoMySQL().prepareStatement(sql.toString());
			statement.setInt(1, telefonePessoa.getTelefone().getDdd());
			statement.setInt(2, telefonePessoa.getTelefone().getNumero());
			statement.setInt(3, pessoa.getId());
			
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
	
	public static PessoaTelefone buscarPessoaTelefone(PessoaTelefone pessoaTelefone) {
		try {
			
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT * FROM pessoa_telefone pt ");
			sql.append("WHERE pt.telefone_pessoa_telefone_ddd = ? ");
			sql.append("AND pt.telefone_pessoa_telefone_numero = ? ");
			sql.append("AND pt.pessoa_id = ?");
			
			PreparedStatement statement = ConexaoBanco.conexaoComBancoMySQL().prepareStatement(sql.toString());
			statement.setInt(1, pessoaTelefone.getTelefonePessoa().getTelefone().getDdd());
			statement.setInt(2, pessoaTelefone.getTelefonePessoa().getTelefone().getNumero());
			statement.setInt(3, pessoaTelefone.getPessoa().getId());

			ResultSet resultadoBusca = statement.executeQuery();
			
			
			if(resultadoBusca.next()) {
				TelefonePessoa telefonePessoa = TelefonePessoaDao.buscarTelefonePessoaPorDddNumero(resultadoBusca.getInt("pt.telefone_pessoa_telefone_ddd"), resultadoBusca.getInt("pt.telefone_pessoa_telefone_numero"));
				Pessoa pessoaResultado = PessoaDao.buscarPessoaPorId(resultadoBusca.getInt("pt.pessoa_id"));
				PessoaTelefone pessoaTelefoneResultado = new PessoaTelefone();
				pessoaTelefoneResultado.setPessoa(pessoaResultado);
				pessoaTelefoneResultado.setTelefonePessoa(telefonePessoa);
				
				return pessoaTelefoneResultado;
			}

			
			return null;
			
			
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static ArrayList<TelefonePessoa> buscarTodosTelefonesDaPessoa(Pessoa pessoa) {
		try {
			
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT * FROM telefone_pessoa tp ");
			sql.append("INNER JOIN pessoa_telefone pt ON pt.telefone_pessoa_telefone_ddd = tp.telefone_ddd AND pt.telefone_pessoa_telefone_numero = tp.telefone_numero ");
			sql.append("INNER JOIN pessoa p ON p.id = pt.pessoa_id ");
			sql.append("WHERE p.id = ?");
			
			PreparedStatement statement = ConexaoBanco.conexaoComBancoMySQL().prepareStatement(sql.toString());
			statement.setInt(1, pessoa.getId());

			ResultSet resultadoBusca = statement.executeQuery();
			
			ArrayList<TelefonePessoa> listaTelefonePessoa = new ArrayList<>();
			
			if(resultadoBusca.next()) {
				TelefonePessoa telefonePessoa = TelefonePessoaDao.buscarTelefonePessoaPorDddNumero(resultadoBusca.getInt("tp.telefone_ddd"), resultadoBusca.getInt("tp.telefone_numero"));
				listaTelefonePessoa.add(telefonePessoa);
			}

			if(!listaTelefonePessoa.isEmpty()) {
				return listaTelefonePessoa;
			}
			
			return null;
			
			
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static ArrayList<Pessoa> buscarTodasPessoaDoTelefone(TelefonePessoa telefonePessoa) {
		try {
			
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT * FROM pessoa p ");
			sql.append("INNER JOIN pessoa_telefone pt ON pt.pessoa_id = p.id ");
			sql.append("INNER JOIN telefone_pessoa tp ON pt.telefone_pessoa_telefone_ddd = tp.telefone_ddd AND pt.telefone_pessoa_telefone_numero = tp.telefone_numero ");
			sql.append("WHERE tp.telefone_ddd = ? AND tp.telefone_numero = ?");
			
			PreparedStatement statement = ConexaoBanco.conexaoComBancoMySQL().prepareStatement(sql.toString());
			statement.setInt(1, telefonePessoa.getTelefone().getDdd());
			statement.setInt(2, telefonePessoa.getTelefone().getNumero());
			
			ResultSet resultadoBusca = statement.executeQuery();
			
			ArrayList<Pessoa> listaPessoas = new ArrayList<>();
			
			if(resultadoBusca.next()) {
				Pessoa pessoaResultado = new Pessoa();
				pessoaResultado.setCpf(resultadoBusca.getString("p.cpf"));
				pessoaResultado.setDataNascimento(new java.util.Date(resultadoBusca.getDate("p.data_nascimento").getTime()));
				pessoaResultado.setEnderecoPessoa(EnderecoDao.buscarEnderecoPessoaPeloIdEndereco(resultadoBusca.getInt("p.endereco_pessoa_id")));
				pessoaResultado.setId(resultadoBusca.getInt("p.id"));
				pessoaResultado.setNomeCompleto(resultadoBusca.getString("p.nome_completo"));
				
				listaPessoas.add(pessoaResultado);
			}

			if(!listaPessoas.isEmpty()) {
				return listaPessoas;
			}
			
			return null;
			
			
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
