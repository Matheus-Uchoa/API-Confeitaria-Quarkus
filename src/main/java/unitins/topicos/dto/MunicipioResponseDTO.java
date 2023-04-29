package unitins.topicos.dto;

import unitins.topicos.entity.MunicipioEntity;

public record MunicipioResponseDTO(Long id, String nome, Long idEstado) {
	public MunicipioResponseDTO(MunicipioEntity municipio) {
		this(municipio.getId(),municipio.getNome(), municipio.getEstado().getId());
	}

}
