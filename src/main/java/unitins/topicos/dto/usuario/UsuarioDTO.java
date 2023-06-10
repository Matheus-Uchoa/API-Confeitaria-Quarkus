package unitins.topicos.dto.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import unitins.topicos.dto.EnderecoDTO;
import unitins.topicos.dto.TelefoneDTO;

public record UsuarioDTO(

    @NotBlank
    String login,

    @NotBlank(message = "O campo senha não pode estar nulo")
    String senha,

    @NotNull
    PessoaFisicaDTO pessoa,

    @NotNull
    EnderecoDTO endereco,

    @NotNull
    TelefoneDTO telefonePrincipal


) {
    
}