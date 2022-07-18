package com.ufrn.bd.biblioteca.testes;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import com.ufrn.bd.biblioteca.daos.AdministradorDao;
import com.ufrn.bd.biblioteca.daos.AutorDao;
import com.ufrn.bd.biblioteca.daos.AutorLivroDao;
import com.ufrn.bd.biblioteca.daos.AvaliacaoLivroDao;
import com.ufrn.bd.biblioteca.daos.EditoraDao;
import com.ufrn.bd.biblioteca.daos.EmprestimoDao;
import com.ufrn.bd.biblioteca.daos.EnderecoDao;
import com.ufrn.bd.biblioteca.daos.EnderecoEmpresaDao;
import com.ufrn.bd.biblioteca.daos.GeneroDao;
import com.ufrn.bd.biblioteca.daos.LivroDao;
import com.ufrn.bd.biblioteca.daos.PessoaDao;
import com.ufrn.bd.biblioteca.daos.PessoaTelefoneDao;
import com.ufrn.bd.biblioteca.daos.TelefoneDao;
import com.ufrn.bd.biblioteca.daos.TelefonePessoaDao;
import com.ufrn.bd.biblioteca.daos.UsuarioComumDao;
import com.ufrn.bd.biblioteca.daos.UsuarioDao;
import com.ufrn.bd.biblioteca.models.Administrador;
import com.ufrn.bd.biblioteca.models.Autor;
import com.ufrn.bd.biblioteca.models.AutorLivro;
import com.ufrn.bd.biblioteca.models.AvaliacaoLivro;
import com.ufrn.bd.biblioteca.models.CEP;
import com.ufrn.bd.biblioteca.models.Editora;
import com.ufrn.bd.biblioteca.models.Emprestimo;
import com.ufrn.bd.biblioteca.models.Endereco;
import com.ufrn.bd.biblioteca.models.EnderecoEmpresa;
import com.ufrn.bd.biblioteca.models.EnderecoPessoa;
import com.ufrn.bd.biblioteca.models.Genero;
import com.ufrn.bd.biblioteca.models.Livro;
import com.ufrn.bd.biblioteca.models.Pessoa;
import com.ufrn.bd.biblioteca.models.PessoaTelefone;
import com.ufrn.bd.biblioteca.models.Telefone;
import com.ufrn.bd.biblioteca.models.TelefoneEmpresa;
import com.ufrn.bd.biblioteca.models.TelefoneEmpresaDao;
import com.ufrn.bd.biblioteca.models.TelefonePessoa;
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
		
		
		Telefone telefone = new Telefone();
		telefone.setDdd(12);
		telefone.setNumero(12345678);
		
		if(Objects.isNull(TelefoneDao.buscarTelefonePeloNumeroDDD(telefone.getDdd(), telefone.getNumero()))) {
			if(TelefoneDao.cadastrarTelefone(telefone)) {
				System.out.println("Telefone Cadastrado com sucesso");
			}
		}
		
		Telefone telefoneResultado = TelefoneDao.buscarTelefonePeloNumeroDDD(telefone.getDdd(), telefone.getNumero());
		
		TelefonePessoa telefonePessoa = new TelefonePessoa();
		telefonePessoa.setTelefone(telefone);
		
		if(Objects.isNull(TelefonePessoaDao.buscarTelefonePessoaPorDddNumero(telefonePessoa.getTelefone().getDdd(), telefonePessoa.getTelefone().getNumero()))) {
			if(TelefonePessoaDao.cadastrarTelefonePessoa(telefonePessoa)) {
				System.out.println("Telefone Pessoa cadastrado com sucesso");
			}
		}
		
		TelefonePessoaDao.buscarTelefonePessoaPorDddNumero(telefonePessoa.getTelefone().getDdd(), telefonePessoa.getTelefone().getNumero());
		
		PessoaTelefone pessoaTelefone = new PessoaTelefone();
		pessoaTelefone.setPessoa(pessoaResultado);
		pessoaTelefone.setTelefonePessoa(telefonePessoa);
		
		if(Objects.isNull(PessoaTelefoneDao.buscarPessoaTelefone(pessoaTelefone))) {
			if(PessoaTelefoneDao.cadastrarTelefonePessoaNaPessoa(telefonePessoa, pessoaResultado)) {
				System.out.println("TelefonePessoa cadastrado para a Pessoa");
			}
		}
		
		ArrayList<TelefonePessoa> listaTelefonesPessoa = PessoaTelefoneDao.buscarTodosTelefonesDaPessoa(pessoaResultado);

		System.out.println("Todos os telefones da pessoa: " + pessoaResultado.getNomeCompleto());
		for(TelefonePessoa tp : listaTelefonesPessoa) {
			System.out.println("Telefone: " + tp.getTelefone().getDdd() + " " + tp.getTelefone().getNumero());
		}
		
		ArrayList<Pessoa> listaPessoasDoTelefone = PessoaTelefoneDao.buscarTodasPessoaDoTelefone(telefonePessoa);
		
		System.out.println("Todas as pessoas do telefone: " + telefonePessoa.getTelefone().getDdd() + " " + telefonePessoa.getTelefone().getNumero());
		for(Pessoa p : listaPessoasDoTelefone) {
			System.out.println("Pessoa: " + p.getNomeCompleto());
		}
		
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
		
		UsuarioComumDao.bloquearUsuario(usuarioComumResultado);
		
		usuarioComumResultado = UsuarioComumDao.buscarUsuarioComumPorIdPessoa(usuarioResultado.getPessoa().getId());
		
		System.out.println();
		System.out.println("Usuario Comum BLOQUEADO");
		System.out.println("ID Usuario Comum: " + usuarioComumResultado.getUsuario().getPessoa().getId());
		System.out.println("Status Bloqueado: " + usuarioComumResultado.isBloqueado());
		
		UsuarioComumDao.desbloquear(usuarioComumResultado);
		
		usuarioComumResultado = UsuarioComumDao.buscarUsuarioComumPorIdPessoa(usuarioResultado.getPessoa().getId());
		
		System.out.println();
		System.out.println("Usuario Comum DESBLOQUEADO");
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
		
		//Teste Editora
		
		Editora editoraTeste = new Editora();
		editoraTeste.setNome("Editora Teste");
		
		if(Objects.isNull(EditoraDao.buscarEditoraPeloNome(editoraTeste.getNome()))) {
			if(EditoraDao.cadastrarEditora(editoraTeste)) {
				System.out.println("Editora cadastrada com sucesso.");
			}
		}
	
		Editora editoraResultado = EditoraDao.buscarEditoraPeloNome(editoraTeste.getNome());
		System.out.println();
		System.out.println("Editora buscada pelo Nome:");
		System.out.println("Id: " + editoraResultado.getId());
		System.out.println("Nome: " + editoraResultado.getNome());

		Editora editoraPorID = EditoraDao.buscarEditoraPeloId(editoraResultado.getId());
		
		if(Objects.isNull(editoraPorID)) {
			System.out.println("Essa editora não existe");
		}else {
			System.out.println("Editora encontrada");
		}
		
		EnderecoEmpresa enderecoEmpresa = new EnderecoEmpresa();
		enderecoEmpresa.setEditora(editoraPorID);
		enderecoEmpresa.setEndereco(EnderecoDao.buscarEnderecoPeloId(pessoaResultado.getEnderecoPessoa().getEndereco().getId()));
		
		if(Objects.isNull(EnderecoEmpresaDao.buscarEnderecoEmpresaPorIdEndereco(enderecoEmpresa.getEndereco().getId()))) {
			if(EnderecoEmpresaDao.cadastrarEnderecoDaEditora(enderecoEmpresa.getEditora(), enderecoEmpresa.getEndereco())) {
				System.out.println("Endereco Empresa cadastrado com sucesso");
			}
		}
		
		ArrayList<EnderecoEmpresa> enderecosEmpresas = EnderecoEmpresaDao.buscarTodosEnderecoEmpresaDaEditora(enderecoEmpresa.getEditora());
		
		System.out.println("Enderecos da Editora: " + enderecoEmpresa.getEditora().getNome());
		for(EnderecoEmpresa endEmp : enderecosEmpresas) {
			System.out.println("Endereco: CEP: " + endEmp.getEndereco().getCep().getCep());
		}
		
		TelefoneEmpresa telefoneEmpresa = new TelefoneEmpresa();
		telefoneEmpresa.setEditora(editoraPorID);
		telefoneEmpresa.setTelefone(telefoneResultado);
		
		if(Objects.isNull(TelefoneEmpresaDao.buscarEnderecoEmpresaPorNumeroDdd(telefoneEmpresa.getTelefone().getDdd(), telefoneEmpresa.getTelefone().getNumero()))) {
			if(TelefoneEmpresaDao.cadastrarTelefoneDaEditora(editoraPorID, telefoneEmpresa.getTelefone())) {
				System.out.println("TelefoneEmpresa cadastrado com sucesso.");
			}
		}
		
		ArrayList<TelefoneEmpresa> listaTelefonesEmpresas = TelefoneEmpresaDao.buscarTodosTelefonesEmpresaDaEditora(editoraPorID);
		
		System.out.println("Todos os telefones da Editora: " + editoraPorID.getNome());
		
		for(TelefoneEmpresa te : listaTelefonesEmpresas) {
			System.out.println("Telefone Empresa: " + te.getTelefone().getDdd() + " " + te.getTelefone().getNumero());
		}
		
		System.out.println();
		
		//Teste Livros
		
		Genero genero = new Genero();
		genero.setNome("Aventura");
		
		if(Objects.isNull(GeneroDao.buscarGeneroPeloNome(genero.getNome()))) {
			if(GeneroDao.cadastrarGenero(genero)) {
				System.out.println("Genero cadastrado com sucesso");
			}
		}
		
		Genero generoResultado = GeneroDao.buscarGeneroPeloNome(genero.getNome());
		
		System.out.println("Genero:");
		System.out.println("Nome: " + generoResultado.getNome());
		
		Livro livro = new Livro();
		livro.setDataLancamento(new Date());
		livro.setEdicao("edicao 1");
		livro.setEditora(editoraPorID);
		livro.setIsbn("12345641");
		livro.setTitulo("Livro Teste");
		
		if(Objects.isNull(LivroDao.buscarLivroPeloIsbn(livro.getIsbn()))) {
			if(LivroDao.cadastrarLivro(livro)) {
				System.out.println("Livro cadastrado com sucesso");
			}
		}
		
		ArrayList<Livro> listaLivrosPorTitulo = LivroDao.buscarListaLivrosPeloTitulo(livro.getTitulo());
		System.out.println();
		System.out.println("Lista livros com o titulo: " + livro.getTitulo());
		
		for(Livro l : listaLivrosPorTitulo) {
			System.out.println("Livro: " + l.getTitulo());
		}
		
		Livro livroResultado = LivroDao.buscarLivroPeloIsbn(livro.getIsbn());
		
		AutorLivro autorLivro = new AutorLivro();
		autorLivro.setAutor(autorResultado);
		autorLivro.setLivro(livroResultado);
		
		if(Objects.isNull(AutorLivroDao.buscarAutorLivro(livroResultado, autorResultado))) {
			if(AutorLivroDao.cadastrarAutorNoLivro(autorResultado, livroResultado)) {
				System.out.println("Autor Livro cadastrado com sucesso.");
			}
		}
		
		ArrayList<Autor> listaAutoresLivro = AutorLivroDao.buscarTodosAutoresDoLivro(livroResultado);
		System.out.println("Lista dos autores do livro : " + livroResultado.getTitulo());
		for(Autor a : listaAutoresLivro) {
			System.out.println("Nome Autor: " + a.getPessoa().getNomeCompleto());
		}
		
		ArrayList<Livro> listaLivrosAutores = AutorLivroDao.buscarTodosLivrosDoAutor(autorResultado);
		System.out.println("Lista dos livros do autor : " + autorResultado.getPessoa().getNomeCompleto());
		for(Livro l : listaLivrosAutores) {
			System.out.println("Nome Livro: " + l.getTitulo());
		}
		
		System.out.println();
		
		AvaliacaoLivro avaliacaoLivro = new AvaliacaoLivro();
		avaliacaoLivro.setComentario("muito bom");
		avaliacaoLivro.setLivro(livroResultado);
		avaliacaoLivro.setNota(10);
		avaliacaoLivro.setUsuarioComum(usuarioComumResultado);
		
		if(Objects.isNull(AvaliacaoLivroDao.buscarAvaliacaoLivro(avaliacaoLivro))) {
			if(AvaliacaoLivroDao.cadastrarAvaliacaoLivro(avaliacaoLivro)) {
				System.out.println("Avaliação cadastrada com sucesso.");
			}
		}
		
		ArrayList<AvaliacaoLivro> listaAvaliacoesPorLivro = AvaliacaoLivroDao.buscarTodasAvaliacoesDoLivro(livroResultado);
		
		System.out.println("Avaliacoes do livro " + livroResultado.getTitulo());
		for(AvaliacaoLivro al : listaAvaliacoesPorLivro) {
			System.out.println("Usuario: " + al.getUsuarioComum().getUsuario().getUsername());
			System.out.println("Nota: " + al.getNota());
		}
		
		Integer media = AvaliacaoLivroDao.buscarMediaNotasDoLivro(livroResultado);
		
		System.out.println("Media das notas: " + media);
		
		ArrayList<AvaliacaoLivro> listaAvalicaoPorUsuario = AvaliacaoLivroDao.buscarTodasAvaliacoesDoUsuario(usuarioComumResultado);
	
		System.out.println("Avaliacoes do usuario " + usuarioComumResultado.getUsuario().getUsername());
		
		for(AvaliacaoLivro al : listaAvalicaoPorUsuario) {
			System.out.println("Livro: " + al.getLivro().getTitulo());
			System.out.println("Nota: " + al.getNota());
		}
		
		//Adicionando outro usuario Para os Testes
		
		Pessoa pessoaTeste2 = new Pessoa();
		pessoaTeste2.setNomeCompleto("Teste2");
		pessoaTeste2.setCpf("789123564895");
		pessoaTeste2.setDataNascimento(new Date());
		
		if(Objects.isNull(PessoaDao.buscarPessoaPorCPF(pessoaTeste2.getCpf()))) {
		
			EnderecoPessoa enderecoPessoaTeste = new EnderecoPessoa();
			Endereco enderecoTeste = new Endereco();
			enderecoTeste.setCep(new CEP("99999999", "rua 999", "natal", "rn"));
			enderecoTeste.setNumero("999");
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
			
			pessoaTeste2.setEnderecoPessoa(enderecoPessoaTeste);
			
			if(PessoaDao.cadastrarPessoa(pessoaTeste2)) {
				System.out.println("Pessoa cadastrada com sucesso.");
			}
		}
		
		Pessoa pessoaResultado2 = PessoaDao.buscarPessoaPorCPF(pessoaTeste2.getCpf());
		
		Usuario usuarioTeste2 = new Usuario("Usuario2dsa", "2d@email.com", "789123");
		usuarioTeste2.setPessoa(pessoaResultado2);
		
		if(Objects.isNull(UsuarioDao.buscarUsuarioPorSenhaUsername(usuarioTeste2))) {
			if(UsuarioDao.cadastrarUsuario(usuarioTeste2)) {
				System.out.println("Usuario cadastrado com sucesso.");
			}
		}
		
		Usuario usuarioResultado2 = UsuarioDao.buscarUsuarioPorIdPessoa(usuarioTeste2.getPessoa().getId());
		
		
		UsuarioComum usuarioComumTeste2 = new UsuarioComum();
		usuarioComumTeste2.setUsuario(usuarioResultado2);
		
		if(Objects.isNull(UsuarioComumDao.buscarUsuarioComumPorIdPessoa(usuarioResultado2.getPessoa().getId()))) {
			if(UsuarioComumDao.cadastrarUsuarioComum(usuarioComumTeste2)) {
				System.out.println("Usuario Comum cadastrado com sucesso.");
			}
		}
		
		System.out.println();
		
		Emprestimo emprestimo = new Emprestimo();
		emprestimo.setDataEmprestimo(new Date());
		emprestimo.setDataPrevista(new Date());
		emprestimo.setLivro(livroResultado);
		emprestimo.setUsuarioEmprestou(usuarioComumTeste);
		emprestimo.setUsuarioPegouEmprestado(usuarioComumTeste2);
		
		if(Objects.isNull(EmprestimoDao.buscarEmprestimo(emprestimo))) {
			if(EmprestimoDao.cadastrarEmprestimo(emprestimo)) {
				System.out.println("Emprestimo cadastrado com sucesso");
			}
		}
		
		ArrayList<Emprestimo> listaEmprestimosQueUmUsuarioPegou= EmprestimoDao.buscarEmprestimosQueUmUsuarioPegou(usuarioComumTeste2);
		
		System.out.println("Emprestimos que o usuario: " + usuarioComumTeste2.getUsuario().getUsername() + " pegou emprestado.");
		for(Emprestimo e : listaEmprestimosQueUmUsuarioPegou) {
			System.out.println("Usuario emprestou: " + e.getUsuarioEmprestou().getUsuario().getUsername());
			System.out.println("Livro: " + e.getLivro().getTitulo());
		}

		System.out.println();
		
		ArrayList<Emprestimo> listaEmprestimosDeUmUsuario= EmprestimoDao.buscarTodosEmprestimosDeUmUsuario(usuarioComumTeste);
		
		System.out.println("Emprestimos do usuario: " + usuarioComumTeste.getUsuario().getUsername());
		for(Emprestimo e : listaEmprestimosDeUmUsuario) {
			System.out.println("Usuario pegou emprestado: " + e.getUsuarioPegouEmprestado().getUsuario().getUsername());
			System.out.println("Livro: " + e.getLivro().getTitulo());
		}
		
		System.out.println();
		
		ArrayList<Emprestimo> listaEmprestimosLivro = EmprestimoDao.buscarTodosOsEmprestimosDeUmLivro(livroResultado);
		
		System.out.println("Emprestimos do livro: " + livroResultado.getTitulo());
		for(Emprestimo e : listaEmprestimosLivro) {
			System.out.println("Usuario emprestou: " + e.getUsuarioEmprestou().getUsuario().getUsername());
			System.out.println("Usuario pegou emprestado: " + e.getUsuarioPegouEmprestado().getUsuario().getUsername());
		}
		
		System.out.println();
		
		Emprestimo emprestimoResultado = EmprestimoDao.buscarEmprestimo(emprestimo);
		
		emprestimoResultado.setDataDevolucao(new Date());
		
		if(EmprestimoDao.devolverLivroEmprestado(emprestimoResultado)) {
			System.out.println("Atualizacao do Emprestimo efetuada com sucesso.");
		}
		
		Emprestimo emprestimoPosAtualizacao = EmprestimoDao.buscarEmprestimo(emprestimo);
		
		System.out.println("Id emprestimo: " + emprestimoPosAtualizacao.getId());
		System.out.println("Data devolucao: " + emprestimoPosAtualizacao.getDataDevolucao());
		
		
	}
}
