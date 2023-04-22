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

import unitins.topicos.dto.ConfeitariaDTO;
import unitins.topicos.dto.ConfeitariaResponseDTO;
import unitins.topicos.entity.ConfeitariaEntity;
import unitins.topicos.repository.AlergenicoRepository;
import unitins.topicos.repository.ConfeitariaRepository;
import unitins.topicos.repository.IngredienteRepository;

@ApplicationScoped
public class ConfeitariaService {

	@Inject
	ConfeitariaRepository confeitariaRepository;
	@Inject
	IngredienteRepository ingredienteRepository;
	@Inject
	AlergenicoRepository alergenicoRepository;

	@Inject
	Validator validator;

	public List<ConfeitariaResponseDTO> getAll() {
		List<ConfeitariaEntity> list = confeitariaRepository.listAll();
		return list.stream().map(ConfeitariaResponseDTO::new).collect(Collectors.toList());
	}

	public ConfeitariaResponseDTO findById(Long id) {
		ConfeitariaEntity produto = confeitariaRepository.findById(id);
		if (produto == null)
			throw new NotFoundException("Produto não encontrado.");
		return new ConfeitariaResponseDTO(produto);
	}

	@Transactional
	public ConfeitariaResponseDTO create(ConfeitariaDTO confeitariaDTO) throws ConstraintViolationException {
		validar(confeitariaDTO);

		ConfeitariaEntity entity = new ConfeitariaEntity();
		entity.setNome(confeitariaDTO.nome());
		entity.setDescricao(confeitariaDTO.descricao());
		entity.setPreco(confeitariaDTO.preco());
		entity.setEstoque(confeitariaDTO.estoque());
		entity.setIngredientes(ingredienteRepository.findAllByIds(confeitariaDTO.ingredientes()));
		entity.setAlergenico(alergenicoRepository.findById(confeitariaDTO.idAlergenico()));

		confeitariaRepository.persist(entity);

		return new ConfeitariaResponseDTO(entity);
	}

	@Transactional
	public ConfeitariaResponseDTO update(Long id, ConfeitariaDTO confeitariaDTO) throws ConstraintViolationException {
		validar(confeitariaDTO);

		ConfeitariaEntity entity = confeitariaRepository.findById(id);

		entity.setNome(confeitariaDTO.nome());
		entity.setDescricao(confeitariaDTO.descricao());
		entity.setPreco(confeitariaDTO.preco());
		entity.setEstoque(confeitariaDTO.estoque());
		entity.setIngredientes(ingredienteRepository.findAllByIds(confeitariaDTO.ingredientes()));
		entity.setAlergenico(alergenicoRepository.findById(confeitariaDTO.idAlergenico()));

		confeitariaRepository.persist(entity);

		return new ConfeitariaResponseDTO(entity);
	}

	private void validar(ConfeitariaDTO ConfeitariaDTO) throws ConstraintViolationException {
		Set<ConstraintViolation<ConfeitariaDTO>> violations = validator.validate(ConfeitariaDTO);
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