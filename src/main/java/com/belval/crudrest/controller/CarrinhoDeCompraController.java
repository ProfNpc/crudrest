package com.belval.crudrest.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.belval.crudrest.model.CarrinhoDeCompra;
import com.belval.crudrest.model.Compra;
import com.belval.crudrest.model.ItemCompra;
import com.belval.crudrest.model.Produto;
import com.belval.crudrest.repository.CompraRepository;
import com.belval.crudrest.repository.ItemCompraRepository;
import com.belval.crudrest.repository.ProdutoRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Configuration
@RestController
public class CarrinhoDeCompraController {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private ItemCompraRepository itemCompraRepository;
	
	@Autowired
	private CompraRepository compraRepository;
	
	@GetMapping("/carrinho")
	public ResponseEntity<CarrinhoDeCompra> obterCarrinho(HttpServletRequest request) {
		CarrinhoDeCompra carrinho = getCarrinho(request);
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(carrinho);
	}

	private CarrinhoDeCompra getCarrinho(HttpServletRequest request) {
		HttpSession session = request.getSession();
		CarrinhoDeCompra carrinho = (CarrinhoDeCompra)session.getAttribute("carrinho");
		if (carrinho == null) {
			carrinho = new CarrinhoDeCompra();
			session.setAttribute("carrinho", carrinho);
		}
		return carrinho;
	}
	
	//curl --header 'Set-Cookie: JSESSIONID=E71B745ED4194F2A4AA0D3C8EDC26580' -X POST http://localhost:8081/carrinho/1/1 -H "Content-Type: application/json; Charset=utf-8"
	@PostMapping("/carrinho/{idProduto}/{qtd}")
	public ResponseEntity<Object> adicionarProduto(
			@PathVariable Integer idProduto,
			@PathVariable BigDecimal qtd,
			HttpServletRequest request) {
		
		CarrinhoDeCompra carrinho = getCarrinho(request);
		
		Optional<Produto> produto = produtoRepository.findById(idProduto);
		
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
	
	//curl --cookie 'JSESSIONID=E71B745ED4194F2A4AA0D3C8EDC26580' -X POST http://localhost:8081/carrinho/comprar -H "Content-Type: application/json; Charset=utf-8"
	@PostMapping("/carrinho/comprar")
	public ResponseEntity<Object> efetuarCompra(HttpServletRequest request) {
		
		Compra novaCompra = new Compra();
		novaCompra.setData(LocalDateTime.now());
		novaCompra.setTotal(BigDecimal.ZERO);
		
		compraRepository.save(novaCompra);
		
		CarrinhoDeCompra carrinho = getCarrinho(request);
		Collection<ItemCompra> itens = carrinho.getMapItens().values();
		for (ItemCompra itemCompra : itens) {
			
			itemCompraRepository.save(itemCompra);
			
			BigDecimal total = novaCompra.getTotal().add(itemCompra.getValorItem());
			novaCompra.setTotal(total);
			
			itemCompra.setCompra(novaCompra);
		}
		
		compraRepository.save(novaCompra);

		
		return ResponseEntity
				.status(HttpStatus.OK)
				.body("Compra efetuada com sucesso!");
	}
	
}
