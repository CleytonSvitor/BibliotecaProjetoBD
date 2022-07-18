package com.ufrn.bd.biblioteca.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

import com.ufrn.bd.biblioteca.models.Editora;
import com.ufrn.bd.biblioteca.models.Endereco;
import com.ufrn.bd.biblioteca.models.EnderecoEmpresa;

public class EnderecoEmpresaDao extends AbstractDao{
	
	public static boolean cadastrarEnderecoDaEditora(Editora editora, Endereco endereco) {
		try {
			
			StringBuffer sql = new StringBuffer();
			sql.append("INSERT INTO endereco_empresa ");
			sql.append("(endereco_id, editora_id) ");	
			sql.append("VALUES (?, ?)");
			
			PreparedStatement statement = ConexaoBanco.conexaoComBancoMySQL().prepareStatement(sql.toString());
			statement.setInt(1, endereco.getId());
			statement.setInt(2, editora.getId());
			
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
	
	public static EnderecoEmpresa buscarEnderecoEmpresaPorIdEndereco(int idEndereco) {
		try {
			
			Endereco enderecoResultado = EnderecoDao.buscarEnderecoPeloId(idEndereco);
			
			if(!Objects.isNull(enderecoResultado)) {
			
				StringBuffer sql = new StringBuffer();
				sql.append("SELECT * FROM endereco_empresa ee ");
				sql.append("WHERE ee.endereco_id = ?");
				
				PreparedStatement statement = ConexaoBanco.conexaoComBancoMySQL().prepareStatement(sql.toString());
				statement.setInt(1, idEndereco);
	
				ResultSet resultadoBusca = statement.executeQuery();
				
				if(resultadoBusca.next()) {
					EnderecoEmpresa enderecoEmpresaResultado = new EnderecoEmpresa();
					enderecoEmpresaResultado.setEditora(EditoraDao.buscarEditoraPeloId(resultadoBusca.getInt("ee.editora_id")));
					enderecoEmpresaResultado.setEndereco(enderecoResultado);
					return enderecoEmpresaResultado;
				}
				
			}
			
			return null;
			
			
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static ArrayList<EnderecoEmpresa> buscarTodosEnderecoEmpresaDaEditora(Editora editora) {
		try {
			
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT * FROM endereco_empresa ee ");
			sql.append("WHERE ee.editora_id = ?");
			
			PreparedStatement statement = ConexaoBanco.conexaoComBancoMySQL().prepareStatement(sql.toString());
			statement.setInt(1, editora.getId());

			ResultSet resultadoBusca = statement.executeQuery();
			
			ArrayList<EnderecoEmpresa> listaEnderecoEmpresa = new ArrayList<>();
			
			if(resultadoBusca.next()) {
				EnderecoEmpresa enderecoEmpresaResultado = new EnderecoEmpresa();
				enderecoEmpresaResultado.setEditora(EditoraDao.buscarEditoraPeloId(resultadoBusca.getInt("ee.editora_id")));
				enderecoEmpresaResultado.setEndereco(EnderecoDao.buscarEnderecoPeloId(resultadoBusca.getInt("ee.endereco_id")));
				
				listaEnderecoEmpresa.add(enderecoEmpresaResultado);
			}

			if(!listaEnderecoEmpresa.isEmpty()) {
				return listaEnderecoEmpresa;
			}
			
			return null;
			
			
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
