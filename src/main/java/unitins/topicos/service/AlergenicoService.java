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

import unitins.topicos.dto.AlergenicoDTO;
import unitins.topicos.dto.AlergenicoResponseDTO;
import unitins.topicos.entity.AlergenicoEntity;
import unitins.topicos.repository.AlergenicoRepository;

@ApplicationScoped
public class AlergenicoService {

	@Inject
	AlergenicoRepository alergenicoRepository;
	@Inject
	Validator validator;

	public List<AlergenicoResponseDTO> getAll() {
		List<AlergenicoEntity> list = alergenicoRepository.listAll();
		return list.stream().map(AlergenicoResponseDTO::new).collect(Collectors.toList());
	}

	public AlergenicoResponseDTO findById(Long id) {
		AlergenicoEntity alergenico = alergenicoRepository.findById(id);
		if (alergenico == null)
			throw new NotFoundException("alergenico não encontrado.");
		return new AlergenicoResponseDTO(alergenico);
	}

	@Transactional
	public AlergenicoResponseDTO create(AlergenicoDTO alergenicoDTO) throws ConstraintViolationException {
		validar(alergenicoDTO);

		AlergenicoEntity entity = new AlergenicoEntity();
		entity.setDescricao(alergenicoDTO.descricao());

		alergenicoRepository.persist(entity);

		return new AlergenicoResponseDTO(entity);
	}

	@Transactional
	public AlergenicoResponseDTO update(Long id, AlergenicoDTO alergenicoDTO) throws ConstraintViolationException {
		validar(alergenicoDTO);

		AlergenicoEntity entity = alergenicoRepository.findById(id);

		entity.setDescricao(alergenicoDTO.descricao());

		return new AlergenicoResponseDTO(entity);
	}

	private void validar(AlergenicoDTO alergenicoDTO) throws ConstraintViolationException {
		Set<ConstraintViolation<AlergenicoDTO>> violations = validator.validate(alergenicoDTO);
		if (!violations.isEmpty())
			throw new ConstraintViolationException(violations);

	}

	@Transactional
	public void delete(Long id) {
		alergenicoRepository.deleteById(id);
	}

	public List<AlergenicoResponseDTO> findByNome(String nome) {
		List<AlergenicoEntity> list = alergenicoRepository.findByNome(nome);
		return list.stream().map(AlergenicoResponseDTO::new).collect(Collectors.toList());
	}

	public long count() {
		return alergenicoRepository.count();
	}

}