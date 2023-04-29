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

import unitins.topicos.dto.IngredienteDTO;
import unitins.topicos.dto.IngredienteResponseDTO;

import unitins.topicos.entity.IngredienteEntity;

import unitins.topicos.repository.IngredienteRepository;

@ApplicationScoped
public class IngredienteService {

	@Inject
	IngredienteRepository ingredienteRepository;

	@Inject
	Validator validator;

	public List<IngredienteResponseDTO> getAll() {
		List<IngredienteEntity> list = ingredienteRepository.listAll();
		return list.stream().map(IngredienteResponseDTO::new).collect(Collectors.toList());
	}

	public IngredienteResponseDTO findById(Long id) {
		IngredienteEntity ingrediente = ingredienteRepository.findById(id);
		if (ingrediente == null)
			throw new NotFoundException("Ingrediente não encontrado.");
		return new IngredienteResponseDTO(ingrediente);
	}

	public List<IngredienteResponseDTO> findByNome(String nome) {
		List<IngredienteEntity> list = ingredienteRepository.findByNome(nome);
		return list.stream().map(IngredienteResponseDTO::new).collect(Collectors.toList());
	}

	@Transactional
	public IngredienteResponseDTO create(IngredienteDTO ingredienteDTO) throws ConstraintViolationException {
		validar(ingredienteDTO);

		IngredienteEntity entity = new IngredienteEntity();
		entity.setNome(ingredienteDTO.nome());
		ingredienteRepository.persist(entity);

		return new IngredienteResponseDTO(entity);
	}

	@Transactional
	public IngredienteResponseDTO update(Long id, IngredienteDTO ingredienteDTO) throws ConstraintViolationException {
		validar(ingredienteDTO);

		IngredienteEntity entity = ingredienteRepository.findById(id);

		entity.setNome(ingredienteDTO.nome());

		return new IngredienteResponseDTO(entity);
	}

	private void validar(IngredienteDTO ingredienteDTO) throws ConstraintViolationException {
		Set<ConstraintViolation<IngredienteDTO>> violations = validator.validate(ingredienteDTO);
		if (!violations.isEmpty())
			throw new ConstraintViolationException(violations);

	}

	@Transactional
	public void delete(Long id) {
		ingredienteRepository.deleteById(id);
	}

	public long count() {
		return ingredienteRepository.count();
	}

}
