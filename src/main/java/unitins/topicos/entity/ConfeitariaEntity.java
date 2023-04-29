package unitins.topicos.entity;

import java.util.ArrayList;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "id")

public class ConfeitariaEntity extends Produto {
	@Column(nullable = false)
	private Double peso;

	private Categoria categoria;

	@ManyToMany
	@JoinTable(name = "produto_ingrediente", joinColumns = @JoinColumn(name = "produto_id"), inverseJoinColumns = @JoinColumn(name = "ingrediente_id"))
	private List<IngredienteEntity> ingredientes = new ArrayList<>();
	
	
	@ManyToOne
	@JoinColumn(name = "id_alergenico")
	private AlergenicoEntity alergenico;
	

	public AlergenicoEntity getAlergenico() {
		return alergenico;
	}

	public void setAlergenico(AlergenicoEntity alergenico) {
		this.alergenico = alergenico;
	}

	public List<IngredienteEntity> getIngredientes() {
		return ingredientes;
	}

	public void setIngredientes(List<IngredienteEntity> ingredientes) {
		this.ingredientes = ingredientes;
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

}
