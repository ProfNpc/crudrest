package br.com.belval.crudrest.controller;

import java.time.LocalDateTime;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.belval.crudrest.model.Produto;
import br.com.belval.crudrest.repository.ProdutoRepository;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
    @Autowired
    private ProdutoRepository produtoRepository;
    
    //curl -X POST http://localhost:8080/produtos -H "Content-Type: application/json; Charset=utf-8" -d @produto-pao.json
    @PostMapping
    public ResponseEntity<Produto> criarProduto(@RequestBody Produto produto) {
    	
		produto.setId(null);
		produto.setDataCriacao(LocalDateTime.now());
		
		System.out.println("Chegou : " + produto.toString());
		
		Produto salvo = produtoRepository.save(produto);
		
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(salvo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> obterProduto(@PathVariable Integer id) {
        Optional<Produto> optional = produtoRepository.findById(id);
        if (optional.isPresent()) {
            return ResponseEntity
            		.status(HttpStatus.OK)
            		.body(optional.get());
        }
        return ResponseEntity
        		.status(HttpStatus.NOT_FOUND)
        		.body("Produto não encontrado!");
    }
    
	@GetMapping
	public ResponseEntity<Iterable<Produto>> listarProdutos() {
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(produtoRepository.findAll());
	}
	
	//curl -X PUT http://localhost:8080/produtos/1 -H "Content-Type: application/json; Charset=utf-8" -d @produto-pao2.json
	@PutMapping("/{id}")
	public ResponseEntity<Object> atualizarProduto(
			@PathVariable Integer id,
			@RequestBody Produto produtoNovo) {
		
		Optional<Produto> optional = produtoRepository.findById(id);
		
		if (optional.isEmpty()) {

			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body("Produto não encontrado!");
		}
		
		Produto produtoAtual = optional.get();
		
		//São sobreescritos todos os atributos menos 
		//id e dataCriacao, estes são mantidos.
		//Atualiza apenas os campos mutáveis.
		produtoAtual.setNome(produtoNovo.getNome());
		produtoAtual.setDescricao(produtoNovo.getDescricao());
		produtoAtual.setPreco(produtoNovo.getPreco());
		
		produtoRepository.save(produtoAtual);
		
		return ResponseEntity
				.status(HttpStatus.OK)
				.body("Produto atualizado com sucesso!");
	}

	//curl -X DELETE http://localhost:8080/produtos/1
	@DeleteMapping("/{id}")
	public ResponseEntity<String> apagarProduto(@PathVariable Integer id) {
		
		Optional<Produto> optional = produtoRepository.findById(id);
		
		if (optional.isEmpty()) {
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body("Produto não encontrado!");
		}

		produtoRepository.deleteById(id);
		
		return ResponseEntity
				.status(HttpStatus.OK)
				.body("Produto apagado com sucesso!");
	}
}
