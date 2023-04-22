package unitins.topicos.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Categoria {
	BOLO(1, "Bolo"), DOCE(2, "Doce"), CUPCAKE(3, "Cupcake"), BISCOITO(4, "Biscoito"), SALGADO(5, "Salgado"),
	TORTA(6, "Torta");

	private int id;
	private String label;

	Categoria(int id, String label) {
		this.id = id;
		this.label = label;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public static Categoria valueOf(Integer id) throws IllegalArgumentException {
		if (id == null)
			return null;
		for (Categoria categoria : Categoria.values()) {
			if (id.equals(categoria.getId()))
				return categoria;
		}
		throw new IllegalArgumentException("Id inválido:" + id);
	}

}
