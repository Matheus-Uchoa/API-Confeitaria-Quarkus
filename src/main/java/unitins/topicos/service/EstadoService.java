package unitins.topicos.service;

import java.util.List;


import java.util.Set;
import java.util.stream.Collectors;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import jakarta.ws.rs.NotFoundException;

import unitins.topicos.dto.EstadoDTO;
import unitins.topicos.dto.EstadoResponseDTO;
import unitins.topicos.entity.EstadoEntity;
import unitins.topicos.repository.EstadoRepository;

@ApplicationScoped
public class EstadoService {

	@Inject
	EstadoRepository estadoRepository;

	@Inject
	Validator validator;

	public List<EstadoResponseDTO> getAll() {
		List<EstadoEntity> list = estadoRepository.listAll();
		return list.stream().map(EstadoResponseDTO::new).collect(Collectors.toList());
	}

	public EstadoResponseDTO findById(Long id) {
		EstadoEntity estado = estadoRepository.findById(id);
		if (estado == null)
			throw new NotFoundException("Estado não encontrado.");
		return new EstadoResponseDTO(estado);
	}

	@Transactional
	public EstadoResponseDTO create(EstadoDTO estadoDto) throws ConstraintViolationException {
		validar(estadoDto);

		EstadoEntity entity = new EstadoEntity();
		entity.setNome(estadoDto.nome());
		entity.setSigla(estadoDto.sigla());
		;
		estadoRepository.persist(entity);

		return new EstadoResponseDTO(entity);
	}

	@Transactional
	public EstadoResponseDTO update(Long id, EstadoDTO estadoDTO) throws ConstraintViolationException {
		validar(estadoDTO);

		EstadoEntity entity = estadoRepository.findById(id);

		entity.setNome(estadoDTO.nome());
		entity.setSigla(estadoDTO.sigla());

		return new EstadoResponseDTO(entity);
	}

	private void validar(EstadoDTO estadoDTO) throws ConstraintViolationException {
		Set<ConstraintViolation<EstadoDTO>> violations = validator.validate(estadoDTO);
		if (!violations.isEmpty())
			throw new ConstraintViolationException(violations);

	}

	@Transactional
	public void delete(Long id) {
		estadoRepository.deleteById(id);
	}

	public List<EstadoResponseDTO> findByNome(String nome) {
		List<EstadoEntity> list = estadoRepository.findByNome(nome);
		return list.stream().map(EstadoResponseDTO::new).collect(Collectors.toList());
	}

	public long count() {
		return estadoRepository.count();
	}

}