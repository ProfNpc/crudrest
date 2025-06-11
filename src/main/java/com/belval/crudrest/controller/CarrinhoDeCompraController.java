package com.belval.crudrest.controller;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.SessionScope;

import com.belval.crudrest.model.CarrinhoDeCompra;
import com.belval.crudrest.model.Produto;
import com.belval.crudrest.repository.ProdutoRepository;

@Configuration
@RestController
public class CarrinhoDeCompraController {
	
	@Autowired
	private ProdutoRepository repository;
	
	@Autowired
	private CarrinhoDeCompra carrinho;
	
	@Bean
	@SessionScope
	public CarrinhoDeCompra sessionScopedBean() {
	    return new CarrinhoDeCompra();
	}
	
	@GetMapping("/carrinho")
	public CarrinhoDeCompra getCarrinho() {
		return carrinho;
	}
	
	
	@PostMapping("/carrinho/{idProduto}/{qtd}")
	public ResponseEntity<Object> adicionarProduto(
			@PathVariable Integer idProduto,
			@PathVariable(required = false, value = "1") BigDecimal qtd) {
		
		Optional<Produto> produto = repository.findById(idProduto);
		
		if (produto.isEmpty()) {
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body("Produto não encontrado!");
		}
		
		carrinho.addProduto(produto.get(), qtd);
		
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(produto.get());
		
	}
	
}
