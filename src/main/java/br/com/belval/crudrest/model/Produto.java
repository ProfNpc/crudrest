package br.com.belval.crudrest.model;

//Add import's com CTRL+SHIFT+O
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Corresponde à tabela Produto
 */
@Entity
@Table(name = "produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
    
    @Column(name = "nome", nullable = false, length = 100)
	private String nome;
    
    @Column(name = "descricao", length = 500)
	private String descricao;
    
    @Column(name = "preco", nullable = false, precision = 10, scale = 2)
	private BigDecimal preco;
    
    @Column(name = "data_criacao")
	private LocalDateTime dataCriacao;
	
	/**
	 * Método construtor é aquele que:
	 * 1 - Tem exatamente o mesmo nome da classe
	 * 2 - Não tem tipo de retorno(nem void)
	 * 
	 * Método construtor padrão
	 * 1 - Lista de parâmetros vazia
	 */
	public Produto() {
		
	}

	//Método que recupera o valor do atributo id
	public Integer getId() {
		return id;
	}

	//Método que define/altera o valor do atributo id
	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
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
		Produto other = (Produto) obj;
		return Objects.equals(id, other.id);
	}

	/**
	 * Para facilitar a identificação de um objeto em um momento de 
	 * debug, precisamos de uma representação textual que nos permita
	 * identificar qual objeto é aquele, para isso vamos sobreescrever
	 * o método toString()
	 * 
	 * Atalho : ALT + SHIFT + S >> Generate toString()
	 */
	@Override
	public String toString() {
		return "Produto [id=" + id + ", nome=" + nome + ", descricao=" + descricao + ", preco=" + preco
				+ ", dataCriacao=" + dataCriacao + "]";
	}
	
	public static void main(String[] args) {
		Produto p = new Produto();
		p.setId(123);
		System.out.println(p.getId());
	}
	
}
