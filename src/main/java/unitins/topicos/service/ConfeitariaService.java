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

import unitins.topicos.dto.ConfeitariaDTO;
import unitins.topicos.dto.ConfeitariaResponseDTO;
import unitins.topicos.entity.Categoria;
import unitins.topicos.entity.ConfeitariaEntity;
import unitins.topicos.repository.ConfeitariaRepository;
import unitins.topicos.repository.AlergenicoRepository;

@ApplicationScoped
public class ConfeitariaService {

	@Inject
	ConfeitariaRepository confeitariaRepository;
	@Inject
	Validator validator;
	@Inject
	AlergenicoRepository alergenicoRepository;

	public List<ConfeitariaResponseDTO> getAll() {
		List<ConfeitariaEntity> list = confeitariaRepository.listAll();
		return list.stream().map(ConfeitariaResponseDTO::new).collect(Collectors.toList());
	}

	public ConfeitariaResponseDTO findById(Long id) {
		ConfeitariaEntity alergenico = confeitariaRepository.findById(id);
		if (alergenico == null)
			throw new NotFoundException("alergenico não encontrado.");
		return new ConfeitariaResponseDTO(alergenico);
	}

	@Transactional
	public ConfeitariaResponseDTO create(ConfeitariaDTO confeitariaDTO) throws ConstraintViolationException {
		validar(confeitariaDTO);

		ConfeitariaEntity entity = new ConfeitariaEntity();
		entity.setDescricao(confeitariaDTO.descricao());
		entity.setNome(confeitariaDTO.nome());
		entity.setAlergenico(alergenicoRepository.findById(confeitariaDTO.idAlergenico()));
entity.setCategoria(Categoria.valueOf(confeitariaDTO.categoria()));
entity.setEstoque(confeitariaDTO.estoque());
entity.setPeso(confeitariaDTO.peso());
entity.setPreco(confeitariaDTO.preco());
		confeitariaRepository.persist(entity);

		return new ConfeitariaResponseDTO(entity);
	}

	@Transactional
	public ConfeitariaResponseDTO update(Long id, ConfeitariaDTO confeitariaDTO) throws ConstraintViolationException {
		validar(confeitariaDTO);
		ConfeitariaEntity entity = new ConfeitariaEntity();

		entity.setDescricao(confeitariaDTO.descricao());
		entity.setNome(confeitariaDTO.nome());
		entity.setAlergenico(alergenicoRepository.findById(confeitariaDTO.idAlergenico()));
entity.setCategoria(Categoria.valueOf(confeitariaDTO.categoria()));
entity.setEstoque(confeitariaDTO.estoque());
entity.setPeso(confeitariaDTO.peso());
entity.setPreco(confeitariaDTO.preco());
		confeitariaRepository.persist(entity);

		return new ConfeitariaResponseDTO(entity);
	}

	private void validar(ConfeitariaDTO confeitariaDTO) throws ConstraintViolationException {
		Set<ConstraintViolation<ConfeitariaDTO>> violations = validator.validate(confeitariaDTO);
		if (!violations.isEmpty())
			throw new ConstraintViolationException(violations);

	}

	@Transactional
	public void delete(Long id) {
		confeitariaRepository.deleteById(id);
	}

	public List<ConfeitariaResponseDTO> findByNome(String nome) {
		List<ConfeitariaEntity> list = confeitariaRepository.findByNome(nome);
		return list.stream().map(ConfeitariaResponseDTO::new).collect(Collectors.toList());
	}

	public long count() {
		return confeitariaRepository.count();
	}

}