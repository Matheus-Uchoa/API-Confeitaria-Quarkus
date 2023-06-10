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

import unitins.topicos.dto.usuario.PessoaFisicaDTO;
import unitins.topicos.dto.usuario.PessoaFisicaResponseDTO;
import unitins.topicos.entity.PessoaFisica;
import unitins.topicos.entity.Sexo;
import unitins.topicos.repository.PessoaFisicaRepository;

@ApplicationScoped
public class PessoaFisicaService {

    @Inject
    PessoaFisicaRepository pessoaFisicaRepository;

    @Inject
    Validator validator;

    
    public List<PessoaFisicaResponseDTO> getAll() {
        List<PessoaFisica> list = pessoaFisicaRepository.listAll();
        return list.stream().map(PessoaFisicaResponseDTO::new).collect(Collectors.toList());
    }

    
    public PessoaFisicaResponseDTO findById(Long id) {
        PessoaFisica pessoafisica = pessoaFisicaRepository.findById(id);
        if (pessoafisica == null)
            throw new NotFoundException("Pessoa Física não encontrada.");
        return new PessoaFisicaResponseDTO(pessoafisica);
    }

    
    @Transactional
    public PessoaFisicaResponseDTO create(PessoaFisicaDTO pessoaFisicaDTO) throws ConstraintViolationException {
        validar(pessoaFisicaDTO);

        PessoaFisica entity = new PessoaFisica();
        entity.setCpf(pessoaFisicaDTO.cpf());
        entity.setNome(pessoaFisicaDTO.nome());
        entity.setSexo(Sexo.valueOf(pessoaFisicaDTO.sexo()));

        pessoaFisicaRepository.persist(entity);

        return new PessoaFisicaResponseDTO(entity);
    }
    public PessoaFisica createPessoaFisica(PessoaFisicaDTO pessoaFisicaDTO) throws ConstraintViolationException {
        validar(pessoaFisicaDTO);

        PessoaFisica entity = new PessoaFisica();
       
       
       entity.setNome(pessoaFisicaDTO.nome());
       
        entity.setEmail(pessoaFisicaDTO.email());
        entity.setCpf(pessoaFisicaDTO.cpf());
       entity.setSexo(Sexo.valueOf(pessoaFisicaDTO.sexo()));
        pessoaFisicaRepository.persist(entity);

        return entity;
    }
    
    @Transactional
    public PessoaFisicaResponseDTO update(Long id, PessoaFisicaDTO pessoaFisicaDTO) throws ConstraintViolationException{
        validar(pessoaFisicaDTO);
   
        PessoaFisica entity = pessoaFisicaRepository.findById(id);
        entity.setCpf(pessoaFisicaDTO.cpf());
        entity.setNome(pessoaFisicaDTO.nome());
        entity.setSexo(Sexo.valueOf(pessoaFisicaDTO.sexo()));

        return new PessoaFisicaResponseDTO(entity);
    }

    private void validar(PessoaFisicaDTO pessoaFisicaDTO) throws ConstraintViolationException {
        Set<ConstraintViolation<PessoaFisicaDTO>> violations = validator.validate(pessoaFisicaDTO);
        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);


    }

    
    @Transactional
    public void delete(Long id) {
        pessoaFisicaRepository.deleteById(id);
    }

    
    public List<PessoaFisicaResponseDTO> findByNome(String nome) {
        List<PessoaFisica> list = pessoaFisicaRepository.findByNome(nome);
        return list.stream().map(PessoaFisicaResponseDTO::new).collect(Collectors.toList());
    }

    
    public long count() {
        return pessoaFisicaRepository.count();
    }

}