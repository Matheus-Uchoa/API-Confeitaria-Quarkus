package unitins.topicos.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TelefoneDTO(
		@NotNull(message = "O campo de DDD deve ser informado.") @Size(max = 2, message = "O DDD deve possuir apenas 2 dígitos") String codigoArea,
		@NotNull(message = "O campo numero deve ser informado.") @Size(min = 9, max = 9, message = "O numero deve possuir 9 dígitos") String numero) {

}
