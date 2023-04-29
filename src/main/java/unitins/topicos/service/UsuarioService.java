package unitins.topicos.service;

import java.util.List;


import java.util.Set;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import javax.ws.rs.NotFoundException;

import unitins.topicos.dto.UsuarioDTO;

import unitins.topicos.dto.UsuarioResponseDTO;

import unitins.topicos.entity.UsuarioEntity;
import unitins.topicos.repository.EnderecoRepository;
import unitins.topicos.repository.TelefoneRepository;
import unitins.topicos.repository.UsuarioRepository;

@ApplicationScoped
public class UsuarioService {

	@Inject
	UsuarioRepository usuarioRepository;

	@Inject
	EnderecoRepository enderecoRepository;

	@Inject
	TelefoneRepository telefoneRepository;

	@Inject
	Validator validator;

	public List<UsuarioResponseDTO> getAll() {
		List<UsuarioEntity> list = usuarioRepository.listAll();
		return list.stream().map(UsuarioResponseDTO::new).collect(Collectors.toList());
	}

	public UsuarioResponseDTO findById(Long id) {
		UsuarioEntity usuario = usuarioRepository.findById(id);
		if (usuario == null)
			throw new NotFoundException("Município não encontrado.");
		return new UsuarioResponseDTO(usuario);
	}

	@Transactional
	public UsuarioResponseDTO create(UsuarioDTO usuarioDTO) throws ConstraintViolationException {
		validar(usuarioDTO);

		UsuarioEntity entity = new UsuarioEntity();
		entity.setNome(usuarioDTO.nome());
		entity.setEmail(usuarioDTO.email());
		entity.setSenha(usuarioDTO.senha());
		entity.setEmail(usuarioDTO.email());
		entity.setCpf(usuarioDTO.cpf());
		entity.setTelefone(telefoneRepository.findById(usuarioDTO.idTelefone()));
		entity.setEndereco(enderecoRepository.findById(usuarioDTO.idEndereco()));

		usuarioRepository.persist(entity);

		return new UsuarioResponseDTO(entity);
	}

	@Transactional
	public UsuarioResponseDTO update(Long id, UsuarioDTO usuarioDTO) throws ConstraintViolationException {
		validar(usuarioDTO);

		UsuarioEntity entity = usuarioRepository.findById(id);

		entity.setNome(usuarioDTO.nome());
		entity.setEmail(usuarioDTO.email());
		entity.setSenha(usuarioDTO.senha());
		entity.setEmail(usuarioDTO.email());
		entity.setCpf(usuarioDTO.cpf());
		entity.setTelefone(telefoneRepository.findById(usuarioDTO.idTelefone()));
		entity.setEndereco(enderecoRepository.findById(usuarioDTO.idEndereco()));

		return new UsuarioResponseDTO(entity);
	}

	private void validar(UsuarioDTO usuarioDTO) throws ConstraintViolationException {
		Set<ConstraintViolation<UsuarioDTO>> violations = validator.validate(usuarioDTO);
		if (!violations.isEmpty())
			throw new ConstraintViolationException(violations);

	}

	@Transactional
	public void delete(Long id) {
		usuarioRepository.deleteById(id);
	}

	public List<UsuarioResponseDTO> findByNome(String nome) {
		List<UsuarioEntity> list = usuarioRepository.findByNome(nome);
		return list.stream().map(UsuarioResponseDTO::new).collect(Collectors.toList());
	}

	public long count() {
		return usuarioRepository.count();
	}

}