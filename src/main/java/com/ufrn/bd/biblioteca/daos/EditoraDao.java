package com.ufrn.bd.biblioteca.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ufrn.bd.biblioteca.models.Editora;

public class EditoraDao extends AbstractDao{
	
	public static boolean cadastrarEditora(Editora editora) {
		try {
			
			StringBuffer sql = new StringBuffer();
			sql.append("INSERT INTO editora ");
			sql.append("(nome) ");	
			sql.append("VALUES (?)");
			
			PreparedStatement statement = ConexaoBanco.conexaoComBancoMySQL().prepareStatement(sql.toString());
			statement.setString(1, editora.getNome());
			
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
	
	public static Editora buscarEditoraPeloId(int idEditora) {
		try {
			
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT * FROM editora ed ");
			sql.append("WHERE ed.id = ?");
			
			PreparedStatement statement = ConexaoBanco.conexaoComBancoMySQL().prepareStatement(sql.toString());
			statement.setInt(1, idEditora);

			ResultSet resultadoBusca = statement.executeQuery();
			
			if(resultadoBusca.next()) {
				Editora editoraResultado = new Editora();
				editoraResultado.setId(resultadoBusca.getInt("ed.id"));
				editoraResultado.setNome(resultadoBusca.getString("ed.nome"));
				
				return editoraResultado;
			}
			return null;
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Editora buscarEditoraPeloNome(String nomeEditora) {
		try {
			
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT * FROM editora ed ");
			sql.append("WHERE ed.nome = ?");
			
			PreparedStatement statement = ConexaoBanco.conexaoComBancoMySQL().prepareStatement(sql.toString());
			statement.setString(1, nomeEditora);

			ResultSet resultadoBusca = statement.executeQuery();
			
			if(resultadoBusca.next()) {
				Editora editoraResultado = new Editora();
				editoraResultado.setId(resultadoBusca.getInt("ed.id"));
				editoraResultado.setNome(resultadoBusca.getString("ed.nome"));
				
				return editoraResultado;
			}
			return null;
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
