package com.belval.crudrest.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.belval.crudrest.model.Arquivo;

@Repository
public interface ArquivoRepository extends CrudRepository<Arquivo, UUID> {
	
}
