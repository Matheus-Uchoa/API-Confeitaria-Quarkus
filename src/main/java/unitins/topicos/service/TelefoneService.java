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

import unitins.topicos.dto.TelefoneDTO;
import unitins.topicos.dto.TelefoneResponseDTO;
import unitins.topicos.dto.TelefoneResponseDTO;
import unitins.topicos.entity.TelefoneEntity;
import unitins.topicos.entity.TelefoneEntity;
import unitins.topicos.repository.TelefoneRepository;

@ApplicationScoped
public class TelefoneService {

	@Inject
	TelefoneRepository telefoneRepository;
	@Inject
	Validator validator;

	public List<TelefoneResponseDTO> getAll() {
		List<TelefoneEntity> list = telefoneRepository.listAll();
		return list.stream().map(TelefoneResponseDTO::new).collect(Collectors.toList());
	}

	public TelefoneResponseDTO findById(Long id) {
		TelefoneEntity telefone = telefoneRepository.findById(id);
		if (telefone == null)
			throw new NotFoundException("telefone não encontrado.");
		return new TelefoneResponseDTO(telefone);
	}

	@Transactional
	public TelefoneResponseDTO create(TelefoneDTO telefoneDTO) throws ConstraintViolationException {
		validar(telefoneDTO);

		TelefoneEntity entity = new TelefoneEntity();
		entity.setCodigoArea(telefoneDTO.codigoArea());
		entity.setNumero(telefoneDTO.numero());

		telefoneRepository.persist(entity);

		return new TelefoneResponseDTO(entity);
	}

	@Transactional
	public TelefoneResponseDTO update(Long id, TelefoneDTO TelefoneDTO) throws ConstraintViolationException {
		validar(TelefoneDTO);

		TelefoneEntity entity = telefoneRepository.findById(id);

		entity.setCodigoArea(TelefoneDTO.codigoArea());
		entity.setNumero(TelefoneDTO.numero());

		return new TelefoneResponseDTO(entity);
	}

	private void validar(TelefoneDTO TelefoneDTO) throws ConstraintViolationException {
		Set<ConstraintViolation<TelefoneDTO>> violations = validator.validate(TelefoneDTO);
		if (!violations.isEmpty())
			throw new ConstraintViolationException(violations);

	}

	@Transactional
	public void delete(Long id) {
		telefoneRepository.deleteById(id);
	}

	public List<TelefoneResponseDTO> findByNome(String nome) {
		List<TelefoneEntity> list = telefoneRepository.findByNome(nome);
		return list.stream().map(TelefoneResponseDTO::new).collect(Collectors.toList());
	}

	public long count() {
		return telefoneRepository.count();
	}

}