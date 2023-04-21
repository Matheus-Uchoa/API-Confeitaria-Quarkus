package unitins.topicos.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public record EnderecoDTO(boolean principal,

		@NotNull(message = "O campo logradouro deve ser informado.") String logradouro,
		@NotNull(message = "O campo bairro deve ser informado.") String bairro,
		@NotNull(message = "O campo numero deve ser informado.") String numero, String complemento,
		@NotNull(message = "O campo cep deve ser informado.") @Size(max = 9, message = "O cpf deve posssuir no máximo 9 caracteres.") String cep,
		@NotNull(message = "O campo id_municipio deve ser informado.") Long id_municipio

) {

}
