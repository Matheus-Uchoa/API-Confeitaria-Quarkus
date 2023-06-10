package unitins.topicos.dto.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosPessoaisDTO(

    @NotBlank(message = "O campo email não pode estar nulo")
    String email,

    @NotNull
   
    Integer sexo
) {
    
}

