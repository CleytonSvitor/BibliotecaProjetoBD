package com.ufrn.bd.biblioteca.daos;

import java.sql.Connection;

public abstract class AbstractDao {
	
	private static Connection conexao = null;
		
	public AbstractDao() {
	}
	
	public static Connection getConexao() {
		if (conexao == null) {
			conexao = ConexaoBanco.conexaoComBancoMySQL();
		}
		return conexao;
	}
}
