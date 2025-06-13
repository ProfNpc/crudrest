package com.belval.crudrest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.belval.crudrest.model.Compra;

public interface CompraRepository extends CrudRepository<Compra, Integer> {
	
    @Query("SELECT DISTINCT compra FROM Compra compra ")
    List<Compra> list();

}
