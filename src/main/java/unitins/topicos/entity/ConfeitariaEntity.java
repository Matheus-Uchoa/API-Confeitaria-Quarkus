package unitins.topicos.entity;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@PrimaryKeyJoinColumn(name = "id")
@Table(name = "Confeitaria")

public class ConfeitariaEntity extends Produto {
	@Column(nullable = false)
	private Double peso;

	private Categoria categoria;
  private String nomeImagem;
	

	@ManyToOne
	@JoinColumn(name = "id_alergenico")
	private AlergenicoEntity alergenico;

	public AlergenicoEntity getAlergenico() {
		return alergenico;
	}

	public void setAlergenico(AlergenicoEntity alergenico) {
		this.alergenico = alergenico;
	}

	public Double getPeso() {
		return peso;
	}

	public void setPeso(Double peso) {
		this.peso = peso;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	public String getNomeImagem() {
		return nomeImagem;
	}

	public void setNomeImagem(String nomeImagem) {
		this.nomeImagem = nomeImagem;
	}
}
