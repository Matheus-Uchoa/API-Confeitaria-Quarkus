package unitins.topicos.dto;

import javax.validation.constraints.NotBlank;

public record IngredienteDTO(@NotBlank(message = "O campo nome deve ser informado.") String nome) {

}
