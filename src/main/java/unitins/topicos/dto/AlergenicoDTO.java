package unitins.topicos.dto;

import jakarta.validation.constraints.NotNull;

public record AlergenicoDTO(

		@NotNull(message = "O campo descricao deve ser informado.") String descricao

) {

}
