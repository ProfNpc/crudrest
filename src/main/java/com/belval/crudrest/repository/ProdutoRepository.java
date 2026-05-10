package com.belval.crudrest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.belval.crudrest.model.Produto;

public interface ProdutoRepository extends CrudRepository<Produto, Integer> {
	
	//select * from produto where descricao like "%descricao%"
	List<Produto> findByDescricaoContainingIgnoreCase(String descricao);

    // Retorna apenas produtos ativos
    List<Produto> findByAtivoTrue();
    
    // Retorna apenas produtos inativos
    List<Produto> findByAtivoFalse();
 
    // Busca por id somente se o produto estiver ativo
    Optional<Produto> findByIdAndAtivoTrue(Integer id);

}
