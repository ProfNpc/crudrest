package com.belval.crudrest.model;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class PapelUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
    @Enumerated(EnumType.STRING)
	private EnumNomePapelUsuario nome;

	public PapelUsuario() {
		super();
	}

	public PapelUsuario(Integer id, EnumNomePapelUsuario nome) {
		super();
		this.id = id;
		this.nome = nome;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public EnumNomePapelUsuario getNome() {
		return nome;
	}

	public void setNome(EnumNomePapelUsuario nome) {
		this.nome = nome;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PapelUsuario other = (PapelUsuario) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "PapelUsuario [id=" + id + ", nome=" + nome + "]";
	}
}
