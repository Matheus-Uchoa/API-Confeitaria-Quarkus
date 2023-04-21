package unitins.topicos.dto;

import unitins.topicos.entity.UsuarioEntity;

public record UsuarioResponseDto(

		String nome,

		String email, String senha,

		String cpf

) {
	public UsuarioResponseDto(UsuarioEntity usuario) {
		this(usuario.getNome(), usuario.getEmail(), usuario.getSenha(), usuario.getCpf());
	}

}
