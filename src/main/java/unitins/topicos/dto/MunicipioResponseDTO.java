package unitins.topicos.dto;

import java.util.HashMap;
import java.util.Map;

import unitins.topicos.entity.MunicipioEntity;

public record MunicipioResponseDTO(Long id, String nome, Map<String, Object> estado) {
	public MunicipioResponseDTO(MunicipioEntity municipio) {
		this(municipio.getId(), municipio.getNome(),
				verEstado(municipio.getEstado().getNome(), municipio.getEstado().getSigla()));
	}

	public static Map<String, Object> verEstado(String nome, String sigla) {

		Map<String, Object> estado = new HashMap<>();

		estado.put("nome", nome);
		estado.put("sigla", sigla);

		return estado;
	}
}
