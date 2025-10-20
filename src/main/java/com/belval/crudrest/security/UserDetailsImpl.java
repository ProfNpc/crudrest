package com.belval.crudrest.security;

import java.util.Collection;
import java.util.stream.Collectors;

//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;

import com.belval.crudrest.model.Usuario;

public class UserDetailsImpl /*implements UserDetails */{
	
//	private static final long serialVersionUID = 1L;
//	
//	private Usuario usuario;
//	
//	public UserDetailsImpl(Usuario usuario) {
//		this.usuario = usuario;
//	}
//	
//	public Usuario getUsuario() {
//		return usuario;
//	}
//
//	public void setUsuario(Usuario usuario) {
//		this.usuario = usuario;
//	}
//
//	@Override
//	public Collection<? extends GrantedAuthority> getAuthorities() {
//        /*
//        Este método converte a lista de papéis associados ao usuário 
//        em uma coleção de GrantedAuthorities, que é a forma que o Spring Security 
//        usa para representar papéis. Isso é feito mapeando cada papel para um 
//        novo SimpleGrantedAuthority, que é uma implementação simples de 
//        GrantedAuthority
//       */
//       return usuario.getPapeis()
//               .stream()
//               .map(papel -> new SimpleGrantedAuthority(papel.getNome().name()))
//               .collect(Collectors.toList());
//	}
//
//	// Retorna a credencial do usuário que criamos anteriormente
//	@Override
//	public String getPassword() {
//		return this.usuario.getPassword();
//	}
//
//	// Retorna o nome de usuário do usuário que criamos anteriormente
//	@Override
//	public String getUsername() {
//		return this.usuario.getEmail();
//	}
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }	
}
