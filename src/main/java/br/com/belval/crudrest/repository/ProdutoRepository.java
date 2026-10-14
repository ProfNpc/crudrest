package br.com.belval.crudrest.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.belval.crudrest.model.Produto;

@Repository
public interface ProdutoRepository extends CrudRepository<Produto, Integer> {
    // O CrudRepository ja fornece: save, saveAll, findById,
    // findAll, delete, deleteById, count, existsById.

    // Busca produtos cujo nome contenha o texto informado (case-insensitive)
    List<Produto> findByNomeContainingIgnoreCase(String nome);
 
    // Busca produtos com preço menor ou igual ao valor informado
    List<Produto> findByPrecoLessThanEqual(BigDecimal preco);
 
    // Verifica se já existe um produto com aquele nome exato
    boolean existsByNome(String nome);

}

