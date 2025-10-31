package com.belval.crudrest.model;

import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

@Entity
public class Arquivo {
	
	@Id
    @GeneratedValue(strategy = GenerationType.UUID) // For Spring Boot 3+
    // For older versions, you might use:
    // @GeneratedValue(generator = "uuid2")
    // @GenericGenerator(name = "uuid2", strategy = "uuid2")
	private UUID id;
	
	private String hash;
	
	private String contentType;//"image/jpeg"
	
    @Lob // Indicates a large object (BLOB or CLOB)
    @Column(name = "binary_data") // Specifies the column name in the database
	private byte[] conteudo;

	public Arquivo() {
		super();
	}

	public Arquivo(UUID id, String hash, String contentType, byte[] conteudo) {
		super();
		this.id = id;
		this.hash = hash;
		this.contentType = contentType;
		this.conteudo = conteudo;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public byte[] getConteudo() {
		return conteudo;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public void setConteudo(byte[] conteudo) {
		this.conteudo = conteudo;
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
		Arquivo other = (Arquivo) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		
		byte[] subArray = Arrays.copyOfRange(conteudo, 0, 50);
		
		return "Arquivo [id=" + id + ", hash=" + hash + ", contentType=" + contentType + ", conteudo="
				+ Arrays.toString(subArray) + "]";
	}
}
