package unitins.topicos.dto;

import unitins.topicos.entity.AlergenicoEntity;

public record AlergenicoResponseDTO(Long id,String descricao) {
	public AlergenicoResponseDTO(AlergenicoEntity alergenico) {
		this(alergenico.getId(),alergenico.getDescricao());
	}
}
