package com.belval.crudrest.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.belval.crudrest.model.Usuario;
import com.belval.crudrest.repository.UsuarioRepository;

@Service
public class UserDetailsServiceImpl /*implements UserDetailsService*/ {
//
//	@Autowired
//	private UsuarioRepository repository;
//	
//	@Override
//	public UserDetails loadUserByUsername(String username) 
//		throws UsernameNotFoundException {
//		
//		Optional<Usuario> usuarioOpt = repository.findByEmail(username);
//		
//		if (usuarioOpt.isEmpty()) {
//			throw new RuntimeException("Usuário não encontrado.");
//		}
//		
//		Usuario usuario = usuarioOpt.get();
//		
//		return new UserDetailsImpl(usuario);
//	}

}
