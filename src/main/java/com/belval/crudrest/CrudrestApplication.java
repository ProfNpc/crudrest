package com.belval.crudrest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.annotation.SessionScope;

import com.belval.crudrest.model.CarrinhoDeCompra;

@SpringBootApplication
public class CrudrestApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudrestApplication.class, args);
	}
	
	@Bean
	@SessionScope
	public CarrinhoDeCompra sessionScopedBean() {
	    return new CarrinhoDeCompra();
	}

}
