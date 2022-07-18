package com.ufrn.bd.biblioteca.daos;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

import com.ufrn.bd.biblioteca.models.Emprestimo;
import com.ufrn.bd.biblioteca.models.Livro;
import com.ufrn.bd.biblioteca.models.UsuarioComum;

public class EmprestimoDao extends AbstractDao{
	
	public static boolean cadastrarEmprestimo(Emprestimo emprestimo) {
		try {
			
			StringBuffer sql = new StringBuffer();
			sql.append("INSERT INTO emprestimo ");
			sql.append("(livro_id, usuario_comum_empresta_id, usuario_comum_pega_emprestado_id, data_emprestimo, data_prevista) ");	
			sql.append("VALUES (?, ?, ?, ?, ?)");
			
			PreparedStatement statement = ConexaoBanco.conexaoComBancoMySQL().prepareStatement(sql.toString());
			statement.setInt(1, emprestimo.getLivro().getId());
			statement.setInt(2, emprestimo.getUsuarioEmprestou().getUsuario().getPessoa().getId());
			statement.setInt(3, emprestimo.getUsuarioPegouEmprestado().getUsuario().getPessoa().getId());
			statement.setDate(4, new Date(emprestimo.getDataEmprestimo().getTime()));
			statement.setDate(5, new Date(emprestimo.getDataPrevista().getTime()));
			
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
	
	public static Emprestimo buscarEmprestimo(Emprestimo emprestimo) {
		try {
			
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT * FROM emprestimo emp ");
			sql.append("WHERE emp.livro_id = ? AND emp.usuario_comum_empresta_id = ? AND emp.usuario_comum_pega_emprestado_id = ? ");
			sql.append("AND emp.data_emprestimo = ? AND emp.data_prevista = ?");
			
			PreparedStatement statement = ConexaoBanco.conexaoComBancoMySQL().prepareStatement(sql.toString());
			statement.setInt(1, emprestimo.getLivro().getId());
			statement.setInt(2, emprestimo.getUsuarioEmprestou().getUsuario().getPessoa().getId());
			statement.setInt(3, emprestimo.getUsuarioPegouEmprestado().getUsuario().getPessoa().getId());
			statement.setDate(4, new Date(emprestimo.getDataEmprestimo().getTime()));
			statement.setDate(5, new Date(emprestimo.getDataPrevista().getTime()));
			
			ResultSet resultadoBusca = statement.executeQuery();
			
			if(resultadoBusca.next()) {
				Emprestimo emprestimoResultado = new Emprestimo();
				emprestimoResultado.setId(resultadoBusca.getInt("emp.id"));
				emprestimoResultado.setLivro(LivroDao.buscarLivroPeloId(resultadoBusca.getInt("emp.livro_id")));
				emprestimoResultado.setUsuarioEmprestou(UsuarioComumDao.buscarUsuarioComumPorIdPessoa(resultadoBusca.getInt("emp.usuario_comum_empresta_id")));
				emprestimoResultado.setUsuarioPegouEmprestado(UsuarioComumDao.buscarUsuarioComumPorIdPessoa(resultadoBusca.getInt("emp.usuario_comum_pega_emprestado_id")));
				emprestimoResultado.setDataEmprestimo(new java.util.Date(resultadoBusca.getDate("emp.data_emprestimo").getTime()));
				emprestimoResultado.setDataPrevista(new java.util.Date(resultadoBusca.getDate("emp.data_prevista").getTime()));
				Date dataDevolucao = resultadoBusca.getDate("emp.data_devolucao");
				if(!Objects.isNull(dataDevolucao)) {
					emprestimoResultado.setDataDevolucao(new java.util.Date(dataDevolucao.getTime()));
				}
				
				return emprestimoResultado;
			}
			
			return null;
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static ArrayList<Emprestimo> buscarTodosEmprestimosDeUmUsuario(UsuarioComum usuarioEmprestou) {
		try {
			
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT * FROM emprestimo emp ");
			sql.append("WHERE emp.usuario_comum_empresta_id = ?");
			
			PreparedStatement statement = ConexaoBanco.conexaoComBancoMySQL().prepareStatement(sql.toString());
			statement.setInt(1, usuarioEmprestou.getUsuario().getPessoa().getId());

			ResultSet resultadoBusca = statement.executeQuery();
			
			
			ArrayList<Emprestimo> listaEmprestimos = new ArrayList<>();
			
			if(resultadoBusca.next()) {
				Emprestimo emprestimoResultado = new Emprestimo();
				emprestimoResultado.setId(resultadoBusca.getInt("emp.id"));
				emprestimoResultado.setLivro(LivroDao.buscarLivroPeloId(resultadoBusca.getInt("emp.livro_id")));
				emprestimoResultado.setUsuarioEmprestou(UsuarioComumDao.buscarUsuarioComumPorIdPessoa(resultadoBusca.getInt("emp.usuario_comum_empresta_id")));
				emprestimoResultado.setUsuarioPegouEmprestado(UsuarioComumDao.buscarUsuarioComumPorIdPessoa(resultadoBusca.getInt("emp.usuario_comum_pega_emprestado_id")));
				emprestimoResultado.setDataEmprestimo(new java.util.Date(resultadoBusca.getDate("emp.data_emprestimo").getTime()));
				emprestimoResultado.setDataPrevista(new java.util.Date(resultadoBusca.getDate("emp.data_prevista").getTime()));
				Date dataDevolucao = resultadoBusca.getDate("emp.data_devolucao");
				if(!Objects.isNull(dataDevolucao)) {
					emprestimoResultado.setDataDevolucao(new java.util.Date(dataDevolucao.getTime()));
				}
				
				listaEmprestimos.add(emprestimoResultado);
			}
			
			if(!listaEmprestimos.isEmpty()) {
				return listaEmprestimos;
			}
			
			return null;
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static ArrayList<Emprestimo> buscarTodosOsEmprestimosDeUmLivro(Livro livro) {
		try {
			
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT * FROM emprestimo emp ");
			sql.append("WHERE emp.livro_id = ?");
			
			PreparedStatement statement = ConexaoBanco.conexaoComBancoMySQL().prepareStatement(sql.toString());
			statement.setInt(1, livro.getId());
			ResultSet resultadoBusca = statement.executeQuery();
			
			ArrayList<Emprestimo> listaEmprestimos = new ArrayList<>();
			
			if(resultadoBusca.next()) {
				Emprestimo emprestimoResultado = new Emprestimo();
				emprestimoResultado.setId(resultadoBusca.getInt("emp.id"));
				emprestimoResultado.setLivro(LivroDao.buscarLivroPeloId(resultadoBusca.getInt("emp.livro_id")));
				emprestimoResultado.setUsuarioEmprestou(UsuarioComumDao.buscarUsuarioComumPorIdPessoa(resultadoBusca.getInt("emp.usuario_comum_empresta_id")));
				emprestimoResultado.setUsuarioPegouEmprestado(UsuarioComumDao.buscarUsuarioComumPorIdPessoa(resultadoBusca.getInt("emp.usuario_comum_pega_emprestado_id")));
				emprestimoResultado.setDataEmprestimo(new java.util.Date(resultadoBusca.getDate("emp.data_emprestimo").getTime()));
				emprestimoResultado.setDataPrevista(new java.util.Date(resultadoBusca.getDate("emp.data_prevista").getTime()));
				Date dataDevolucao = resultadoBusca.getDate("emp.data_devolucao");
				if(!Objects.isNull(dataDevolucao)) {
					emprestimoResultado.setDataDevolucao(new java.util.Date(dataDevolucao.getTime()));
				}
				
				listaEmprestimos.add(emprestimoResultado);
			}
			
			if(!listaEmprestimos.isEmpty()) {
				return listaEmprestimos;
			}
			
			return null;
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static ArrayList<Emprestimo> buscarEmprestimosQueUmUsuarioPegou(UsuarioComum usuarioPegouEmprestado) {
		try {
			
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT * FROM emprestimo emp ");
			sql.append("WHERE emp.usuario_comum_pega_emprestado_id = ?");
			
			PreparedStatement statement = ConexaoBanco.conexaoComBancoMySQL().prepareStatement(sql.toString());
			statement.setInt(1, usuarioPegouEmprestado.getUsuario().getPessoa().getId());

			ResultSet resultadoBusca = statement.executeQuery();
			
			ArrayList<Emprestimo> listaEmprestimos = new ArrayList<>();
			
			if(resultadoBusca.next()) {
				Emprestimo emprestimoResultado = new Emprestimo();
				emprestimoResultado.setId(resultadoBusca.getInt("emp.id"));
				emprestimoResultado.setLivro(LivroDao.buscarLivroPeloId(resultadoBusca.getInt("emp.livro_id")));
				emprestimoResultado.setUsuarioEmprestou(UsuarioComumDao.buscarUsuarioComumPorIdPessoa(resultadoBusca.getInt("emp.usuario_comum_empresta_id")));
				emprestimoResultado.setUsuarioPegouEmprestado(UsuarioComumDao.buscarUsuarioComumPorIdPessoa(resultadoBusca.getInt("emp.usuario_comum_pega_emprestado_id")));
				emprestimoResultado.setDataEmprestimo(new java.util.Date(resultadoBusca.getDate("emp.data_emprestimo").getTime()));
				emprestimoResultado.setDataPrevista(new java.util.Date(resultadoBusca.getDate("emp.data_prevista").getTime()));
				Date dataDevolucao = resultadoBusca.getDate("emp.data_devolucao");
				if(!Objects.isNull(dataDevolucao)) {
					emprestimoResultado.setDataDevolucao(new java.util.Date(dataDevolucao.getTime()));
				}
				
				listaEmprestimos.add(emprestimoResultado);
			}
			
			if(!listaEmprestimos.isEmpty()) {
				return listaEmprestimos;
			}
			
			return null;
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static boolean devolverLivroEmprestado(Emprestimo emprestimo) {
		try {
			
			StringBuffer sql = new StringBuffer();
			sql.append("UPDATE emprestimo ");
			sql.append("SET data_devolucao = ? ");
			sql.append("WHERE id = ? ");
			
			PreparedStatement statement = ConexaoBanco.conexaoComBancoMySQL().prepareStatement(sql.toString());
			statement.setDate(1, new Date(emprestimo.getDataDevolucao().getTime()));
			statement.setInt(2, emprestimo.getId());
			
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
}
