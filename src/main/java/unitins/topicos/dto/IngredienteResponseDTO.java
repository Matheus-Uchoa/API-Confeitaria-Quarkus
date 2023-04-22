package unitins.topicos.dto;

import java.util.List;
import java.util.stream.Collectors;

import unitins.topicos.entity.IngredienteEntity;

public record IngredienteResponseDTO(String nome,

		List<ConfeitariaResponseDTO> produtos) {
	public IngredienteResponseDTO(IngredienteEntity ingrediente) {
		this(ingrediente.getNome(),
				ingrediente.getProdutos().stream().map(ConfeitariaResponseDTO::new).collect(Collectors.toList()));
	}
}
