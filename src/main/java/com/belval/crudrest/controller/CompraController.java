package com.belval.crudrest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.belval.crudrest.model.Compra;
import com.belval.crudrest.repository.CompraRepository;

@RestController
public class CompraController {
	
	@Autowired
	private CompraRepository repository;

	@GetMapping("/compras")
	public ResponseEntity<List<Compra>> list() {

		List<Compra> all = repository.list();
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(all);
	}
}
