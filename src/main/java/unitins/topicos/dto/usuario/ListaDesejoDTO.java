package unitins.topicos.dto.usuario;


import jakarta.validation.constraints.NotNull;

public record ListaDesejoDTO(
    @NotNull
    Long idUsuario,

    Long idProduto
) {
    
}
