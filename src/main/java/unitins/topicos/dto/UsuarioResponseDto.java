package unitins.topicos.dto;

import unitins.topicos.entity.UsuarioEntity;

public record UsuarioResponseDTO(

		String nome,

		String email, String senha,

		String cpf, Long idEndereco, Long idTelefone

) {
	public UsuarioResponseDTO(UsuarioEntity usuario) {
		this(usuario.getNome(), usuario.getEmail(), usuario.getSenha(), usuario.getCpf(), usuario.getEndereco().getId(),
				usuario.getTelefone().getId());
	}

}
