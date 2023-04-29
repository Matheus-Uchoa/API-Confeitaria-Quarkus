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

import unitins.topicos.dto.EnderecoDTO;
import unitins.topicos.dto.EnderecoResponseDTO;

import unitins.topicos.entity.EnderecoEntity;

import unitins.topicos.repository.EnderecoRepository;
import unitins.topicos.repository.MunicipioRepository;

@ApplicationScoped
public class EnderecoService {

	@Inject
	MunicipioRepository municipioRepository;

	@Inject
	EnderecoRepository enderecoRepository;

	@Inject
	Validator validator;

	public List<EnderecoResponseDTO> getAll() {
		List<EnderecoEntity> list = enderecoRepository.listAll();
		return list.stream().map(EnderecoResponseDTO::new).collect(Collectors.toList());
	}

	public EnderecoResponseDTO findById(Long id) {
		EnderecoEntity endereco = enderecoRepository.findById(id);
		if (endereco == null)
			throw new NotFoundException("Município não encontrado.");
		return new EnderecoResponseDTO(endereco);
	}

	@Transactional
	public EnderecoResponseDTO create(EnderecoDTO enderecoDTO) throws ConstraintViolationException {
		validar(enderecoDTO);

		EnderecoEntity entity = new EnderecoEntity();
		entity.setPrincipal(enderecoDTO.principal());
		entity.setLogradouro(enderecoDTO.logradouro());
		entity.setBairro(enderecoDTO.bairro());
		entity.setNumero(enderecoDTO.numero());
		entity.setComplemento(enderecoDTO.complemento());
		entity.setCep(enderecoDTO.cep());
		entity.setMunicipio(municipioRepository.findById(enderecoDTO.id_municipio()));
		enderecoRepository.persist(entity);

		return new EnderecoResponseDTO(entity);
	}

	@Transactional
	public EnderecoResponseDTO update(Long id, EnderecoDTO enderecoDTO) throws ConstraintViolationException {
		validar(enderecoDTO);

		EnderecoEntity entity = enderecoRepository.findById(id);
		entity.setPrincipal(enderecoDTO.principal());
		entity.setLogradouro(enderecoDTO.logradouro());
		entity.setBairro(enderecoDTO.bairro());
		entity.setNumero(enderecoDTO.numero());
		entity.setComplemento(enderecoDTO.complemento());
		entity.setCep(enderecoDTO.cep());
		entity.setMunicipio(municipioRepository.findById(enderecoDTO.id_municipio()));

		return new EnderecoResponseDTO(entity);
	}

	private void validar(EnderecoDTO enderecoDTO) throws ConstraintViolationException {
		Set<ConstraintViolation<EnderecoDTO>> violations = validator.validate(enderecoDTO);
		if (!violations.isEmpty())
			throw new ConstraintViolationException(violations);

	}

	@Transactional
	public void delete(Long id) {
		enderecoRepository.deleteById(id);
	}

	public List<EnderecoResponseDTO> findByCep(String cep) {
		List<EnderecoEntity> list = enderecoRepository.findByCep(cep);
		return list.stream().map(EnderecoResponseDTO::new).collect(Collectors.toList());
	}

	public long count() {
		return enderecoRepository.count();
	}

}