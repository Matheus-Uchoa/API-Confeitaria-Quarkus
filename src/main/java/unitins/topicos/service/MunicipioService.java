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

import unitins.topicos.dto.MunicipioResponseDTO;
import unitins.topicos.dto.MunicipioDto;
import unitins.topicos.entity.MunicipioEntity;
import unitins.topicos.repository.EstadoRepository;
import unitins.topicos.repository.MunicipioRepository;

@ApplicationScoped
public class MunicipioService {

	@Inject
	MunicipioRepository municipioRepository;

	@Inject
	EstadoRepository estadoRepository;

	@Inject
	Validator validator;

	public List<MunicipioResponseDTO> getAll() {
		List<MunicipioEntity> list = municipioRepository.listAll();
		return list.stream().map(MunicipioResponseDTO::new).collect(Collectors.toList());
	}

	public MunicipioResponseDTO findById(Long id) {
		MunicipioEntity municipio = municipioRepository.findById(id);
		if (municipio == null)
			throw new NotFoundException("Município não encontrado.");
		return new MunicipioResponseDTO(municipio);
	}

	@Transactional
	public MunicipioResponseDTO create(MunicipioDto municipioDto) throws ConstraintViolationException {
		validar(municipioDto);

		MunicipioEntity entity = new MunicipioEntity();
		entity.setNome(municipioDto.nome());
		entity.setEstado(estadoRepository.findById(municipioDto.idEstado()));
		municipioRepository.persist(entity);

		return new MunicipioResponseDTO(entity);
	}

	@Transactional
	public MunicipioResponseDTO update(Long id, MunicipioDto municipioDTO) throws ConstraintViolationException {
		validar(municipioDTO);

		MunicipioEntity entity = municipioRepository.findById(id);

		entity.setNome(municipioDTO.nome());
		entity.setEstado(estadoRepository.findById(municipioDTO.idEstado()));

		return new MunicipioResponseDTO(entity);
	}

	private void validar(MunicipioDto municipioDTO) throws ConstraintViolationException {
		Set<ConstraintViolation<MunicipioDto>> violations = validator.validate(municipioDTO);
		if (!violations.isEmpty())
			throw new ConstraintViolationException(violations);

	}

	@Transactional
	public void delete(Long id) {
		municipioRepository.deleteById(id);
	}

	public List<MunicipioResponseDTO> findByNome(String nome) {
		List<MunicipioEntity> list = municipioRepository.findByNome(nome);
		return list.stream().map(MunicipioResponseDTO::new).collect(Collectors.toList());
	}

	public long count() {
		return municipioRepository.count();
	}

}