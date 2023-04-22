package unitins.topicos.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record MunicipioDTO(

		@NotBlank(message = "O campo nome deve ser informado.") String nome,
		@NotNull(message = "O campo idEstado deve ser informado.") Long idEstado

)

{

}
