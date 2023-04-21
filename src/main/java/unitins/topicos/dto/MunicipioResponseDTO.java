package unitins.topicos.dto;

import unitins.topicos.entity.MunicipioEntity;

public record MunicipioResponseDTO(

		String nome, Long idEstado) {
	public MunicipioResponseDTO(MunicipioEntity municipio) {
		this(municipio.getNome(), municipio.getEstado().getId());
	}

}
