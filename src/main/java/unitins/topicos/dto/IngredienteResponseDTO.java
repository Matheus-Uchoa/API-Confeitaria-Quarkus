package unitins.topicos.dto;

import java.util.List;
import java.util.stream.Collectors;

import unitins.topicos.entity.IngredienteEntity;

public record IngredienteResponseDTO(Long id,String nome,

		List<ConfeitariaResponseDTO> produtos) {
	public IngredienteResponseDTO(IngredienteEntity ingrediente) {
		this(ingrediente.getId(),ingrediente.getNome(),
				ingrediente.getProdutos().stream().map(ConfeitariaResponseDTO::new).collect(Collectors.toList()));
	}
}
