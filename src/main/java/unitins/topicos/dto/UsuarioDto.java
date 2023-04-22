package unitins.topicos.dto;

import javax.validation.constraints.NotBlank;

import javax.validation.constraints.Size;

public record UsuarioDTO(

		@NotBlank(message = "O campo nome deve ser informado") String nome,

		@NotBlank(message = "O campo Email deve ser informado") String email,
		@NotBlank(message = "O campo senha deve ser informado") String senha,

		@NotBlank(message = "O campo cpf deve ser informado.") @Size(max = 14, message = "O cpf deve posssuir no máximo 14 caracteres.") String cpf,

		@NotBlank(message = "O campo de endereço deve ser informados") Long idEndereco,
		@NotBlank(message = "O campo de telefone devem ser informados") Long idTelefone

) {

}
