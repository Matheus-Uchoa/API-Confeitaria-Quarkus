package unitins.topicos.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Endereco")
public class EnderecoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private boolean principal;

	@Column(nullable = false)
	private String logradouro;

	@Column(nullable = false)
	private String bairro;

	@Column(nullable = false)
	private String numero;

	@Column(nullable = false)
	private String complemento;

	@Column(nullable = false)
	private String cep;

	@ManyToOne
	@JoinColumn(name = "id_municipio")
	private MunicipioEntity municipio;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isPrincipal() {
		return principal;
	}

	public void setPrincipal(boolean principal) {
		this.principal = principal;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public MunicipioEntity getMunicipio() {
		return municipio;
	}

	public void setMunicipio(MunicipioEntity endereco) {
		this.municipio = endereco;
	}

}
