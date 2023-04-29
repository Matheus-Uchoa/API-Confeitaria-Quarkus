package unitins.topicos.dto;

import unitins.topicos.entity.EstadoEntity;

public record EstadoResponseDTO(Long id, String nome, String sigla

) {
	public EstadoResponseDTO(EstadoEntity estado) {
		this(estado.getId(),estado.getNome(), estado.getSigla());
	}
}
