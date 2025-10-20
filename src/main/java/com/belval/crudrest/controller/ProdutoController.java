package com.belval.crudrest.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
				.body(repository.findAll());
	}
	
	@GetMapping("/produtos/{id}")
	public ResponseEntity<Object> buscarPorId(
			@PathVariable(value = "id") Integer id) {
		
		Optional<Produto> produto = repository.findById(id);
		
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
		
		Optional<Produto> produto = repository.findById(id);
		
		if (produto.isEmpty()) {

			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body("Produto não encontrado!");
		}
		
		prod.setId(id);
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
		
		Optional<Produto> produto = repository.findById(id);
		
		if (produto.isEmpty()) {

			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body("Produto não encontrado!");
		}
		
		Produto prod = produto.get();
		repository.delete(prod);
		
		return ResponseEntity
				.status(HttpStatus.OK)
				.body("Produto apagado com sucesso!");
		

	}
	
	private static final String UPLOAD_DIR = "./uploads/";
	

	@PostMapping("/upload/{fileName:.*}")
	public ResponseEntity<String> uploadFile(
			@PathVariable String fileName,
			@RequestParam("file") MultipartFile file) {
		
		try {
			if (file.isEmpty()) {
				return ResponseEntity
						.badRequest()
						.body("Arquivo vazio!");
						//.status(HttpStatus.BAD_REQUEST)
						//.body("");
			}
			
			Path dirPath = Paths.get(UPLOAD_DIR);
			
			//Path uploadPath = Paths.get(UPLOAD_DIR, fileName);
			Path uploadPath = Paths.get(UPLOAD_DIR, file.getOriginalFilename());
			
			
			if (!Files.exists(dirPath)) {
				Files.createDirectories(dirPath);
			}
			
			Files.createFile(uploadPath);
			
			// Save the file to the server
			byte[] bytes = file.getBytes();
			Files.write(uploadPath, bytes);
			
			return ResponseEntity.ok("Upload com sucesso!");
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity
					.badRequest()
					.body("Arquivo vazio!");
		}
	}
	
}
