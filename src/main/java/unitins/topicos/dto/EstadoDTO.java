package unitins.topicos.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record EstadoDTO(@NotBlank(message = "O campo nome deve ser informado.") String nome,
		@NotNull(message = "O campo sigla deve ser informado.") String sigla) {

}
