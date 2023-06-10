package unitins.topicos.dto.usuario;

import com.fasterxml.jackson.annotation.JsonInclude;

import unitins.topicos.entity.PessoaFisica;
import unitins.topicos.entity.Sexo;

public record PessoaFisicaResponseDTO(
    Long id,
    String cpf,
    String nome,
    String email,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    Sexo sexo
) {
    public PessoaFisicaResponseDTO(PessoaFisica pf) {
        this(pf.getId(), pf.getCpf(), pf.getNome(),pf.getEmail(), pf.getSexo()); 
    }


}