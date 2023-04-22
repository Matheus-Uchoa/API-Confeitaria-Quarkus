package unitins.topicos.dto;

import javax.validation.constraints.NotNull;

public record AlergenicoDTO(

		@NotNull(message = "O campo descricao deve ser informado.") String descricao

) {

}
