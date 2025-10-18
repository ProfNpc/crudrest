package com.belval.crudrest.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.belval.crudrest.model.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, Integer>{
	
	Optional<Usuario> findByEmail(String email);

}
