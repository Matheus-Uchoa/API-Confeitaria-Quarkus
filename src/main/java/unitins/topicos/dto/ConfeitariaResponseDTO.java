package unitins.topicos.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonInclude;

import unitins.topicos.entity.Categoria;
import unitins.topicos.entity.ConfeitariaEntity;

public record ConfeitariaResponseDTO(String nome,

		String descricao,

		Double preco, Integer estoque, @JsonInclude(JsonInclude.Include.NON_NULL) Categoria categoria,
		Long idAlergenico, List<IngredienteResponseDTO> ingredientes

) {

	public ConfeitariaResponseDTO(ConfeitariaEntity confeitaria) {
		this(confeitaria.getNome(), confeitaria.getDescricao(), confeitaria.getPreco(), confeitaria.getEstoque(),
				confeitaria.getCategoria(), confeitaria.getAlergenico().getId(),
				confeitaria.getIngredientes().stream().map(IngredienteResponseDTO::new).collect(Collectors.toList()));
	}

}
