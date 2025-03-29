package com.belval.crudrest.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.belval.crudrest.model.Produto;

@RestController
public class ProdutoController {
	
	private static List<Produto> lista = new ArrayList<>();
	private static int proxId = 1;
	
	@GetMapping("/produtos")
	public ResponseEntity<Iterable<Produto>> obterProdutos() {
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(lista);
	}
	
	
	//curl POST http://localhost:8080/produtos -H "Content-Type: application/json; Charset=utf-8" -d @produto-pao.json
	
	@PostMapping("/produtos")
	public ResponseEntity<Produto> criarProduto(
			@RequestBody Produto produto) {
		
		produto.setId(proxId++);
		
		System.out.println("Produto criado ... " + produto.toString());
		lista.add(produto);
		
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(produto);
		
	}

}
