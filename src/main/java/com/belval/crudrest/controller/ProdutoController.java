package com.belval.crudrest.controller;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.belval.crudrest.model.Produto;
import com.belval.crudrest.repository.ProdutoRepository;

@RestController
public class ProdutoController {
	
	@Autowired
	private ProdutoRepository repository;
	
	/**
	 * Retorna todos os produtos
	 * @return
	 */
	@GetMapping("/produtos")
	public ResponseEntity<Iterable<Produto>> obterProdutos() {
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(repository.findByAtivoTrue());
	}
	
	@GetMapping("/produtos/inativos")
	public ResponseEntity<Iterable<Produto>> obterProdutosInativos() {
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(repository.findByAtivoFalse());
	}
	
	@GetMapping("/produtos/{id}")
	public ResponseEntity<Object> buscarPorId(
			@PathVariable(value = "id") Integer id) {
		
		Optional<Produto> produto = repository.findByIdAndAtivoTrue(id);
		
		if (produto.isPresent()) {
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(produto.get());
		}
		
		return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.body("Produto não encontrado!");
				
	}
	
	
	//curl POST http://localhost:8080/produtos -H "Content-Type: application/json; Charset=utf-8" -d @produto-pao.json
	
	@PostMapping("/produtos")
	public ResponseEntity<Produto> criarProduto(
			@RequestBody Produto produto) {
		
		//produto.setId(proxId++);
		
		System.out.println("Produto criado ... " + produto.toString());
		produto.setAtivo(true);
		produto.setDataCriacao(LocalDateTime.now());
		repository.save(produto);
		
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(produto);
		
	}
	
	
	//Observação: para métodos que não sejam o GET e o POST é necessário colocar o -X(menos xis maiúsculo)
	//curl -X PUT http://localhost:8080/produtos/1 -H "Content-Type: application/json; Charset=utf-8" -d @produto-mortadela2.json 
	@PutMapping("/produtos/{id}")
	public ResponseEntity<Object> atualizarProduto(
			@PathVariable Integer id,
			@RequestBody Produto prod) {
		
		Optional<Produto> produto = repository.findByIdAndAtivoTrue(id);
		
		if (produto.isEmpty()) {

			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body("Produto não encontrado!");
		}
		
		prod.setId(id);
		prod.setDataCriacao(produto.get().getDataCriacao());
		repository.save(prod);
		
		return ResponseEntity
				.status(HttpStatus.OK)
				.body("Produto atualizado com sucesso!");
		

	}

	//Observação: para métodos que não sejam o GET e o POST é necessário colocar o -X(menos xis maiúsculo)
	//curl -X DELETE http://localhost:8080/produtos/1 
	@DeleteMapping("/produtos/{id}")
	public ResponseEntity<Object> apagarProduto(
			@PathVariable Integer id) {
		
		Optional<Produto> produto = repository.findByIdAndAtivoTrue(id);
		
		if (produto.isEmpty()) {

			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body("Produto não encontrado!");
		}
		
		Produto prod = produto.get();
		prod.setAtivo(false);
		repository.save(prod);
		
		return ResponseEntity
				.status(HttpStatus.OK)
				.body("Produto apagado com sucesso!");
	}
	
    // PATCH /produtos/{id}/reativar — torna um produto inativo em ativo
    @PatchMapping("/produtos/{id}/reativar")
    public ResponseEntity<Produto> reativarProduto(@PathVariable Integer id) {
        Optional<Produto> optionalReativar = repository.findById(id);
        if (!optionalReativar.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Produto produto = optionalReativar.get();
        if (produto.getAtivo()) {
            // produto já está ativo — não há nada a fazer
            return new ResponseEntity<>(produto, HttpStatus.OK);
        }
        produto.setAtivo(true);
        return new ResponseEntity<>(
        	repository.save(produto), 
        	HttpStatus.OK);
    }
	
}
