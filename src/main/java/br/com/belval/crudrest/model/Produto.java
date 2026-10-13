package br.com.belval.crudrest.model;

//Add import's com CTRL+SHIFT+O
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Corresponde à tabela Produto
 */
public class Produto {

	private Integer id;
	private String nome;
	private String descricao;
	private BigDecimal preco;
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
