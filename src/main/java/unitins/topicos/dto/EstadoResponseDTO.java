package unitins.topicos.dto;

import unitins.topicos.entity.EstadoEntity;

public record EstadoResponseDTO(String nome, String sigla

) {
	public EstadoResponseDTO(EstadoEntity estado) {
		this(estado.getNome(), estado.getSigla());
	}
}
