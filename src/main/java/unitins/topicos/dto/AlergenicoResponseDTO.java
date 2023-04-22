package unitins.topicos.dto;

import unitins.topicos.entity.AlergenicoEntity;

public record AlergenicoResponseDTO(String descricao) {
	public AlergenicoResponseDTO(AlergenicoEntity alergenico) {
		this(alergenico.getDescricao());
	}
}
