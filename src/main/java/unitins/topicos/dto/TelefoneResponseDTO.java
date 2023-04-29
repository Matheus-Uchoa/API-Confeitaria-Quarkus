package unitins.topicos.dto;

import unitins.topicos.entity.TelefoneEntity;

public record TelefoneResponseDTO(Long id,String codigoArea, String numero)

{
	public TelefoneResponseDTO(TelefoneEntity telefone) {
		this(telefone.getId(),telefone.getCodigoArea(), telefone.getNumero());
	}

}
