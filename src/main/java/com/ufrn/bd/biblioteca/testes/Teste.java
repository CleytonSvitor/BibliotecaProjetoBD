package com.ufrn.bd.biblioteca.testes;

import java.util.Date;
import java.util.Objects;

import com.ufrn.bd.biblioteca.daos.AdministradorDao;
import com.ufrn.bd.biblioteca.daos.AutorDao;
import com.ufrn.bd.biblioteca.daos.EnderecoDao;
import com.ufrn.bd.biblioteca.daos.PessoaDao;
import com.ufrn.bd.biblioteca.daos.UsuarioComumDao;
import com.ufrn.bd.biblioteca.daos.UsuarioDao;
import com.ufrn.bd.biblioteca.models.Administrador;
import com.ufrn.bd.biblioteca.models.Autor;
import com.ufrn.bd.biblioteca.models.CEP;
import com.ufrn.bd.biblioteca.models.Endereco;
import com.ufrn.bd.biblioteca.models.EnderecoPessoa;
import com.ufrn.bd.biblioteca.models.Pessoa;
import com.ufrn.bd.biblioteca.models.Usuario;
import com.ufrn.bd.biblioteca.models.UsuarioComum;

public class Teste {
	public static void main(String[] args) {
		
		//TESTE DO CADASTRO DE UMA PESSOA COM SUAS DEPENDENCIAS.
		
		Pessoa pessoaTeste = new Pessoa();
		pessoaTeste.setNomeCompleto("Pessoa Teste");
		pessoaTeste.setCpf("123456789-12");
		pessoaTeste.setDataNascimento(new Date());
		
		if(Objects.isNull(PessoaDao.buscarPessoaPorCPF(pessoaTeste.getCpf()))) {
		
			EnderecoPessoa enderecoPessoaTeste = new EnderecoPessoa();
			Endereco enderecoTeste = new Endereco();
			enderecoTeste.setCep(new CEP("12345678", "rua 123", "natal", "rn"));
			enderecoTeste.setNumero("123");
			enderecoPessoaTeste.setEndereco(enderecoTeste);
			
			
			if(Objects.isNull(enderecoTeste.getId())) {
				Integer id_endereco = EnderecoDao.buscarIdEnderecoPessoa(enderecoPessoaTeste);
				if(Objects.isNull(id_endereco)){
					if(EnderecoDao.cadastarEnderecoPessoa(enderecoPessoaTeste)) {
						System.out.println("Novo endereco cadastrado com sucesso.");
					}
				}
				
			}
			enderecoPessoaTeste.getEndereco().setId(EnderecoDao.buscarIdEnderecoPessoa(enderecoPessoaTeste));
			
			pessoaTeste.setEnderecoPessoa(enderecoPessoaTeste);
			
			if(PessoaDao.cadastrarPessoa(pessoaTeste)) {
				System.out.println("Pessoa cadastrada com sucesso.");
			}
		}
		
		Pessoa pessoaResultado = PessoaDao.buscarPessoaPorCPF(pessoaTeste.getCpf());
		
		System.out.println("Pessoa Teste");
		System.out.println("Nome: " + pessoaResultado.getNomeCompleto());
		System.out.println("CPF: " + pessoaResultado.getCpf());
		System.out.println("Data de Nascimento " + pessoaResultado.getDataNascimento());
		System.out.println("CEP: " + pessoaResultado.getEnderecoPessoa().getEndereco().getCep().getCep());
		System.out.println("Estado: " + pessoaResultado.getEnderecoPessoa().getEndereco().getCep().getEstado());
		System.out.println("Cidade: " + pessoaResultado.getEnderecoPessoa().getEndereco().getCep().getCidade());
		System.out.println("Logradouro: " + pessoaResultado.getEnderecoPessoa().getEndereco().getCep().getLogradouro());
		System.out.println("Número: " + pessoaResultado.getEnderecoPessoa().getEndereco().getNumero());
		
		//Testes Usuario
		
		Usuario usuarioTeste = new Usuario("UsuarioTeste", "teste@email.com", "123456");
		usuarioTeste.setPessoa(pessoaResultado);
		if(Objects.isNull(UsuarioDao.buscarUsuarioPorSenhaUsername(usuarioTeste))) {
			
			if(UsuarioDao.cadastrarUsuario(usuarioTeste)) {
				System.out.println("Usuario cadastrado com sucesso.");
			}
		}
		
		Usuario usuarioResultado = UsuarioDao.buscarUsuarioPorIdPessoa(usuarioTeste.getPessoa().getId());
		System.out.println();
		System.out.println("Usuario Teste");
		System.out.println("Username: " + usuarioResultado.getUsername());
		System.out.println("Email: " + usuarioResultado.getEmail());
		System.out.println("Senha: " + usuarioResultado.getSenha());
		
		UsuarioComum usuarioComumTeste = new UsuarioComum();
		usuarioComumTeste.setUsuario(usuarioResultado);
		
		if(Objects.isNull(UsuarioComumDao.buscarUsuarioComumPorIdPessoa(usuarioResultado.getPessoa().getId()))) {
			if(UsuarioComumDao.cadastrarUsuarioComum(usuarioComumTeste)) {
				System.out.println("Usuario Comum cadastrado com sucesso.");
			}
		}
		
		UsuarioComum usuarioComumResultado = UsuarioComumDao.buscarUsuarioComumPorIdPessoa(usuarioResultado.getPessoa().getId());
	
		System.out.println();
		System.out.println("Usuario Comum");
		System.out.println("ID Usuario Comum: " + usuarioComumResultado.getUsuario().getPessoa().getId());
		System.out.println("Status Bloqueado: " + usuarioComumResultado.isBloqueado());
		
		
		Administrador administradorTeste = new Administrador();
		administradorTeste.setUsuario(usuarioResultado);
		
		if(Objects.isNull(AdministradorDao.buscarAdministradororPorIdPessoa(usuarioResultado.getPessoa().getId()))) {
			if(AdministradorDao.cadastrarAdministrador(administradorTeste)) {
				System.out.println("Administrador cadastrado com sucesso.");
			}
		}
		
		Administrador administradorResultado = AdministradorDao.buscarAdministradororPorIdPessoa(usuarioResultado.getPessoa().getId());
	
		System.out.println();
		System.out.println("Administrador");
		System.out.println("ID Administrador: " + administradorResultado.getUsuario().getPessoa().getId());
		
		
		Autor autor = new Autor();
		autor.setPessoa(pessoaResultado);
		
		if(Objects.isNull(AutorDao.buscarAutorPorIdPessoa(autor.getPessoa().getId()))) {
			if(AutorDao.cadastrarAutor(autor)) {
				System.out.println("Autor cadastrado com sucesso.");
			}
		}
		
		Autor autorResultado = AutorDao.buscarAutorPorIdPessoa(autor.getPessoa().getId());
		
		System.out.println();
		System.out.println("Autor");
		System.out.println("ID Autor: " + autorResultado.getPessoa().getId());
	}
}
