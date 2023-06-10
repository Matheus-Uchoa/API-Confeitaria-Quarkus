package unitins.topicos.dto.usuario;

import jakarta.validation.constraints.NotNull;
import unitins.topicos.dto.EnderecoDTO;
import unitins.topicos.dto.TelefoneDTO;

public record CadastroCompletoDTO (
    
@NotNull(message = "O campo telefone deve ser informado.") TelefoneDTO telefone,

@NotNull(message = "O campo endereco deve ser informado.") EnderecoDTO endereco,

@NotNull(message = "O campo pessoa deve ser informado.") PessoaFisicaDTO pessoa)
{
    
}
