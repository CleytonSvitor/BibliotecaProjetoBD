package com.ufrn.biblioteca.springbootapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ufrn.bd.biblioteca.daos.UsuarioDao;
import com.ufrn.bd.biblioteca.models.Usuario;

@SpringBootApplication
@RestController
public class UsuarioController {

	public static void main(String[] args) {
		SpringApplication.run(UsuarioController.class, args);
	}
	
	@GetMapping("/usuario")
	public Usuario usuario(@RequestParam(value = "id") int id) {
		Usuario u = UsuarioDao.buscarUsuarioPorIdPessoa(id);		
		
		return u;				
	}

	@GetMapping("/home")
	public String home(@RequestParam(value = "name", defaultValue = "World") String name) {
		return String.format("Hello %s!", name);
	}
}