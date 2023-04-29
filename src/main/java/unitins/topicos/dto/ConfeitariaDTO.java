package unitins.topicos.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record ConfeitariaDTO(@NotBlank(message = "O campo nome deve ser informado.") String nome,
		@NotBlank(message = "O campo descrição deve ser informado.")

		String descricao,
		@NotNull(message = "O campo preço deve ser informado.")

		Double preco,
		@NotNull(message = "O campo estoque deve ser informado.")

		Integer estoque,
		@NotNull(message = "O campo idAlergenico deve ser informado.")

		Long idAlergenico,

		Integer categoria,
		List<IngredienteDTO> ingredientes) {

}
