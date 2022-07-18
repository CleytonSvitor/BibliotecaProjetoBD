package com.ufrn.bd.biblioteca.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

import com.ufrn.bd.biblioteca.daos.ConexaoBanco;
import com.ufrn.bd.biblioteca.daos.EditoraDao;
import com.ufrn.bd.biblioteca.daos.TelefoneDao;

public class TelefoneEmpresaDao {
	
	public static boolean cadastrarTelefoneDaEditora(Editora editora, Telefone telefone) {
		try {
			
			StringBuffer sql = new StringBuffer();
			sql.append("INSERT INTO telefone_empresa ");
			sql.append("(telefone_ddd, telefone_numero, editora_id) ");	
			sql.append("VALUES (?, ?, ?)");
			
			PreparedStatement statement = ConexaoBanco.conexaoComBancoMySQL().prepareStatement(sql.toString());
			statement.setInt(1, telefone.getDdd());
			statement.setInt(2, telefone.getNumero());
			statement.setInt(3, editora.getId());
			
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
	
	public static TelefoneEmpresa buscarEnderecoEmpresaPorNumeroDdd(int ddd, int numero) {
		try {
			
			Telefone telefoneResultado = TelefoneDao.buscarTelefonePeloNumeroDDD(ddd, numero);
			
			if(!Objects.isNull(telefoneResultado)) {
			
				StringBuffer sql = new StringBuffer();
				sql.append("SELECT * FROM telefone_empresa te ");
				sql.append("WHERE te.telefone_ddd = ? AND te.telefone_numero = ?");
				
				PreparedStatement statement = ConexaoBanco.conexaoComBancoMySQL().prepareStatement(sql.toString());
				statement.setInt(1, ddd);
				statement.setInt(2, numero);
	
				ResultSet resultadoBusca = statement.executeQuery();
				
				if(resultadoBusca.next()) {
					TelefoneEmpresa telefoneEmpresa = new TelefoneEmpresa();
					telefoneEmpresa.setTelefone(telefoneResultado);
					telefoneEmpresa.setEditora(EditoraDao.buscarEditoraPeloId(resultadoBusca.getInt("te.editora_id")));
					
					return telefoneEmpresa;
				}
				
			}
			
			return null;
			
			
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static ArrayList<TelefoneEmpresa> buscarTodosTelefonesEmpresaDaEditora(Editora editora) {
		try {
			
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT * FROM telefone_empresa te ");
			sql.append("WHERE te.editora_id = ?");
			
			PreparedStatement statement = ConexaoBanco.conexaoComBancoMySQL().prepareStatement(sql.toString());
			statement.setInt(1, editora.getId());

			ResultSet resultadoBusca = statement.executeQuery();
			
			ArrayList<TelefoneEmpresa> listaTelefoneEmpresa = new ArrayList<>();
			
			if(resultadoBusca.next()) {
				TelefoneEmpresa telefoneEmpresa = new TelefoneEmpresa();
				telefoneEmpresa.setTelefone(TelefoneDao.buscarTelefonePeloNumeroDDD(resultadoBusca.getInt("te.telefone_ddd"), resultadoBusca.getInt("te.telefone_numero")));
				telefoneEmpresa.setEditora(EditoraDao.buscarEditoraPeloId(resultadoBusca.getInt("te.editora_id")));
				listaTelefoneEmpresa.add(telefoneEmpresa);
			}

			if(!listaTelefoneEmpresa.isEmpty()) {
				return listaTelefoneEmpresa;
			}
			
			return null;
			
			
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
