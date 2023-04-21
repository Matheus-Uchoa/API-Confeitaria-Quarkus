package unitins.topicos.dto;

import unitins.topicos.entity.TelefoneEntity;

public record TelefoneResponseDTO(String codigoArea, String numero)

{
	public TelefoneResponseDTO(TelefoneEntity telefone) {
		this(telefone.getCodigoArea(), telefone.getNumero());
	}

}
