package unitins.topicos.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;

public record ConfeitariaDTO(@NotBlank(message = "O campo nome deve ser informado.") String nome,
		@NotBlank(message = "O campo nome deve ser informado.")

		String descricao,
		@NotBlank(message = "O campo nome deve ser informado.")

		Double preco,
		@NotBlank(message = "O campo nome deve ser informado.")

		Integer estoque,
		@NotBlank(message = "O campo nome deve ser informado.")

		Long idAlergenico,

		Integer categoria,
		List<IngredienteDTO> ingredientes) {

}
