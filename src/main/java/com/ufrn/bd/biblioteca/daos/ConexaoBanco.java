package com.ufrn.bd.biblioteca.daos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBanco {
	
	private static final String URL = "jdbc:mysql://localhost:3306/biblioteca?useLegacyDatetimeCode=false&serverTimezone=America/Fortaleza";
	private static final String USUARIO = "root";
	private static final String SENHA = "root";
	
	public static Connection conexaoComBancoMySQL() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			return DriverManager.getConnection(URL, USUARIO, SENHA);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
}
