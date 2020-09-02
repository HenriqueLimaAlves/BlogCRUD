package com.blog_pessoal.blogPessoal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog_pessoal.blogPessoal.model.Postagem;
import com.blog_pessoal.blogPessoal.repository.PostagemRepository;

@RestController
@RequestMapping("/postagens")
@CrossOrigin("*")
public class PostagensController {
	
	@Autowired
	private PostagemRepository repository;
	//assim que for feita alguma requisição por GET em postagens, o metodo retornará todos os objetos contidos em Postagem
	@GetMapping
	public ResponseEntity<List<Postagem>> GetAll(){
		return ResponseEntity.ok(repository.findAll());
	}
	//assim que for feita alguma requisição por GET em postagens/id, esse metodo irá capturar a variavel e retornará um objeto Postagem ou um 404
	@GetMapping("/{id}")
	public ResponseEntity<Postagem> GetById(@PathVariable long id){
		return repository.findById(id).map(resp -> ResponseEntity.ok(resp)).orElse(ResponseEntity.notFound().build());
	}
	//assim que for feita alguma requisição por GET em postagens/titulo/valor, esse metodo irá capturar a variavel que conter o valor informado na coluna "titulo" 
	@GetMapping("/titulo/{titulo}")
	public ResponseEntity<List<Postagem>> GetByTitulo(@PathVariable String titulo){
		return ResponseEntity.ok(repository.findAllByTituloContainingIgnoreCase(titulo));
	}
	//receberá um objeto Postagem via body e retornara um objeto ResponseEnetity com  um valor pelo body para o bd
	@PostMapping
	public ResponseEntity<Postagem> post(@RequestBody Postagem postagem){
		return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(postagem));
	}
	//receberá um objeto Postagem via body e retornara um objeto ResponseEnetity com  um valor pelo body para o bd
	@PutMapping
	public ResponseEntity<Postagem> put(@RequestBody Postagem postagem){
		return ResponseEntity.status(HttpStatus.OK).body(repository.save(postagem));
	}	
	//recebe a variavel id e deleta o objeto
	@DeleteMapping("/{id}")
	public void delete(@PathVariable long id) {
		repository.deleteById(id);
	}
}
