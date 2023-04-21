package unitins.topicos.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Validator;

import unitins.topicos.dto.UsuarioResponseDto;
import unitins.topicos.entity.UsuarioEntity;
import unitins.topicos.repository.UsuarioRepository;

@ApplicationScoped
public class UsuarioService {
	@Inject
	UsuarioRepository usuarioRepository;

	@Inject
	Validator validator;

	public List<UsuarioResponseDto> getAll() {
		List<UsuarioEntity> list = usuarioRepository.listAll();
		return list.stream().map(UsuarioResponseDto::new).collect(Collectors.toList());
	}
}
