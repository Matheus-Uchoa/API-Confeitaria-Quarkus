package unitins.topicos.dto.usuario;

import com.fasterxml.jackson.annotation.JsonInclude;

import unitins.topicos.entity.PessoaFisica;
import unitins.topicos.entity.Sexo;
import unitins.topicos.entity.UsuarioEntity;

public record UsuarioResponseDTO(
    Long id,
    String cpf,
    String nome,
    String email,
    String login,
    PessoaFisica pessoa,
    String nomeImagem,

    @JsonInclude(JsonInclude.Include.NON_NULL)
    Sexo sexo
) {

    public static UsuarioResponseDTO valueOf(UsuarioEntity u) {
        if (u.getPessoaFisica() == null) 
            return new UsuarioResponseDTO(u.getId(), null, null, null, u.getLogin(),null, null, null);
        
        return new UsuarioResponseDTO(u.getId(), 
            u.getPessoaFisica().getCpf(), 
            u.getPessoaFisica().getNome(),
            u.getPessoaFisica().getEmail(),
            u.getLogin(), 
            u.getPessoaFisica(),
            u.getNomeImagem(),
            u.getPessoaFisica().getSexo());
    }


}