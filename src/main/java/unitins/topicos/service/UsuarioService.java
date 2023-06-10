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
import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.NotFoundException;
import unitins.topicos.dto.EnderecoDTO;
import unitins.topicos.dto.TelefoneDTO;
import unitins.topicos.dto.usuario.CadastroBasicoDTO;
import unitins.topicos.dto.usuario.CadastroCompletoDTO;
import unitins.topicos.dto.usuario.DadosPessoaisDTO;
import unitins.topicos.dto.usuario.ListaDesejoDTO;
import unitins.topicos.dto.usuario.ListaDesejoResponseDTO;
import unitins.topicos.dto.usuario.PessoaFisicaDTO;
import unitins.topicos.dto.usuario.SenhaDTO;
import unitins.topicos.dto.usuario.UsuarioDTO;
import unitins.topicos.dto.usuario.UsuarioResponseDTO;
import unitins.topicos.entity.EnderecoEntity;
import unitins.topicos.entity.Perfil;
import unitins.topicos.entity.PessoaFisica;
import unitins.topicos.entity.Sexo;
import unitins.topicos.entity.TelefoneEntity;
import unitins.topicos.entity.UsuarioEntity;
import unitins.topicos.repository.ConfeitariaRepository;
import unitins.topicos.repository.EnderecoRepository;
import unitins.topicos.repository.MunicipioRepository;
import unitins.topicos.repository.PessoaFisicaRepository;
import unitins.topicos.repository.TelefoneRepository;
import unitins.topicos.repository.UsuarioRepository;

@ApplicationScoped
public class UsuarioService {

    @Inject
    UsuarioRepository usuarioRepository;
    @Inject
    MunicipioRepository municipioRepository;

    @Inject
    HashService hashService;

    @Inject
    EnderecoRepository enderecoRepository;

    @Inject
    TelefoneRepository telefoneRepository;

    @Inject
    PessoaFisicaRepository pessoaFisicaRepository;

    @Inject
    PessoaFisicaService pessoaFisicaService;
    @Inject
    Validator validator;
    @Inject
    ConfeitariaRepository confeitariaRepository;

    public List<UsuarioResponseDTO> getAll() {
        List<UsuarioEntity> list = usuarioRepository.listAll();
        return list.stream().map(u -> UsuarioResponseDTO.valueOf(u)).collect(Collectors.toList());
    }

    public UsuarioResponseDTO findById(Long id) {
        UsuarioEntity pessoafisica = usuarioRepository.findById(id);
        if (pessoafisica == null)
            throw new NotFoundException("Usuário não encontrado.");
        return UsuarioResponseDTO.valueOf(pessoafisica);
    }

    @Transactional
    public UsuarioResponseDTO create(UsuarioDTO usuarioDTO) throws ConstraintViolationException {
        validar(usuarioDTO);

        UsuarioEntity entity = new UsuarioEntity();
        entity.setLogin(usuarioDTO.login());
        entity.setSenha(hashService.getHashSenha(usuarioDTO.senha()));
        entity.setPessoaFisica(insertPessoaFisica(usuarioDTO.pessoa()));
        entity.setTelefone(createTelefone(usuarioDTO.telefonePrincipal()));
        entity.setEndereco(createEndereco(usuarioDTO.endereco()));

        usuarioRepository.persist(entity);

        return UsuarioResponseDTO.valueOf(entity);
    }

    @Transactional
    public UsuarioResponseDTO update(Long id, UsuarioDTO usuarioDTO) throws ConstraintViolationException {
        validar(usuarioDTO);

        UsuarioEntity entity = usuarioRepository.findById(id);
        entity.setLogin(usuarioDTO.login());
        entity.setSenha(usuarioDTO.senha());
        entity.setTelefone(createTelefone(usuarioDTO.telefonePrincipal()));
        entity.setEndereco(createEndereco(usuarioDTO.endereco()));
        entity.setPessoaFisica(insertPessoaFisica(usuarioDTO.pessoa()));

        return UsuarioResponseDTO.valueOf(entity);
    }

    @Transactional
    public void delete(Long id) {
        usuarioRepository.deleteById(id);
    }

    public List<UsuarioResponseDTO> getByNome(String nome) throws NullPointerException {

        List<UsuarioEntity> list = usuarioRepository.findByNome(nome);

        if (list == null)
            throw new NullPointerException("nenhum usuario encontrado");

        return list.stream().map(u -> UsuarioResponseDTO.valueOf(u)).collect(Collectors.toList());

    }

    public long count() {
        return usuarioRepository.count();
    }

    public UsuarioEntity findByLoginAndSenha(String login, String senha) {
        return usuarioRepository.findByLoginAndSenha(login, senha);
    }

    public UsuarioResponseDTO findByLogin(String login) {
        UsuarioEntity usuario = usuarioRepository.findByLogin(login);
        if (usuario == null)
            throw new NotFoundException("Usuário não encontrado.");
        return UsuarioResponseDTO.valueOf(usuario);
    }

    @Transactional
    public UsuarioResponseDTO update(Long id, String nomeImagem) {

        UsuarioEntity entity = usuarioRepository.findById(id);
        entity.setNomeImagem(nomeImagem);

        return UsuarioResponseDTO.valueOf(entity);
    }
    @Transactional
    public void update(Long id, DadosPessoaisDTO dadosPessoaisDTO) {

        UsuarioEntity entity = usuarioRepository.findById(id);

        entity.getPessoaFisica().setEmail(dadosPessoaisDTO.email());

        entity.getPessoaFisica().setSexo(Sexo.valueOf(dadosPessoaisDTO.sexo()));
    }
    private void validar(UsuarioDTO usuarioDTO) throws ConstraintViolationException {
        Set<ConstraintViolation<UsuarioDTO>> violations = validator.validate(usuarioDTO);
        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);

    }
public void update(Long id, SenhaDTO senhaDTO) {

        UsuarioEntity entity = usuarioRepository.findById(id);

        if (entity.getSenha().equals(hashService.getHashSenha(senhaDTO.senhaAntiga())))
            entity.setSenha(hashService.getHashSenha(senhaDTO.senhaNova()));

        else
            throw new NotAuthorizedException("As senha apresentada não corresponde com a sua senha atual, tente novamente");
    }
      
    @Transactional
    public UsuarioResponseDTO update(Long id, CadastroCompletoDTO cadastro) throws ConstraintViolationException {

        UsuarioEntity entity = usuarioRepository.findById(id);
        entity.setEndereco(createEndereco(cadastro.endereco()));
        entity.setPessoaFisica(insertPessoaFisica(cadastro.pessoa()));
        entity.setTelefone(createTelefone(cadastro.telefone()));

        return UsuarioResponseDTO.valueOf(entity);
    }
    private PessoaFisica insertPessoaFisica(PessoaFisicaDTO pessoaFisicaDTO) throws ConstraintViolationException {

        return pessoaFisicaService.createPessoaFisica(pessoaFisicaDTO);
    }

    private TelefoneEntity createTelefone(TelefoneDTO telefone2) throws ConstraintViolationException {
        validarTel(telefone2);

        TelefoneEntity telefone = new TelefoneEntity();

        telefone.setCodigoArea(telefone2.codigoArea());
        telefone.setNumero(telefone2.numero());

        telefoneRepository.persist(telefone);

        return telefone;
    }

    private void validarTel(TelefoneDTO telefoneDTO) throws ConstraintViolationException {
        Set<ConstraintViolation<TelefoneDTO>> violations = validator.validate(telefoneDTO);
        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);

    }
    @Transactional
    public void updateTelefonePrincipal(Long id, TelefoneDTO telefonePrincipalDTO) {

        UsuarioEntity entity = usuarioRepository.findById(id);

        Long idTelefone = entity.getTelefone().getId();

        entity.setTelefone(createTelefone(telefonePrincipalDTO));

        deleteTelefone(idTelefone);
    }
private void deleteTelefone(Long id) throws NotFoundException, IllegalArgumentException {

        if (id == null)
            throw new IllegalArgumentException("Número inválido");

        TelefoneEntity telefone = telefoneRepository.findById(id);

        if (telefoneRepository.isPersistent(telefone))
            telefoneRepository.delete(telefone);

        else
            throw new NotFoundException("Nenhum Telefone encontrado");
    }
    private EnderecoEntity createEndereco(EnderecoDTO enderecoDTO) throws ConstraintViolationException {

        validarEnd(enderecoDTO);

        EnderecoEntity endereco = new EnderecoEntity();

        endereco.setLogradouro(enderecoDTO.logradouro());

        endereco.setBairro(enderecoDTO.bairro());

        endereco.setCep(enderecoDTO.cep());

        endereco.setNumero(enderecoDTO.numero());

        endereco.setComplemento(enderecoDTO.complemento());

        endereco.setMunicipio(municipioRepository.findById(enderecoDTO.id_municipio()));

        enderecoRepository.persist(endereco);

        return endereco;
    }

    private void validarEnd(EnderecoDTO enderecoDTO) throws ConstraintViolationException {
        Set<ConstraintViolation<EnderecoDTO>> violations = validator.validate(enderecoDTO);
        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);

    }
     @Transactional
    public void updateEndereco(Long id, EnderecoDTO enderecoDTO) {

        UsuarioEntity entity = usuarioRepository.findById(id);
        Long idEndereco = entity.getEndereco().getId();

        entity.setEndereco(createEndereco(enderecoDTO));
        deleteEndereco(idEndereco);

    }

    private void deleteEndereco(Long id) throws NotFoundException, IllegalArgumentException {

        if (id == null)
            throw new IllegalArgumentException("Número inválido");

        EnderecoEntity endereco = enderecoRepository.findById(id);

        if (enderecoRepository.isPersistent(endereco))
            enderecoRepository.delete(endereco);

        else
            throw new NotFoundException("Nenhum Endereco encontrado");
    }
 public ListaDesejoResponseDTO getListaDesejo(Long id) throws NullPointerException {

        UsuarioEntity usuario = usuarioRepository.findById(id);

        if (usuario == null)
            throw new NullPointerException("usuario não encontrado");

        return new ListaDesejoResponseDTO(usuario);
    }
    public void insertListaDesejo(ListaDesejoDTO listaDto) throws NullPointerException {

        validarListDe(listaDto);

        UsuarioEntity usuario = usuarioRepository.findById(listaDto.idUsuario());

        if (usuario == null)
            throw new NullPointerException("usuario não encontrado");

        usuario.setProduto(confeitariaRepository.findById(listaDto.idProduto()));
    }

    private void validarListDe(ListaDesejoDTO listaDesejoDto) throws ConstraintViolationException {

        Set<ConstraintViolation<ListaDesejoDTO>> violations = validator.validate(listaDesejoDto);

        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);
   
    }

     public Integer countListaDesejo(Long id) throws NullPointerException {
        
        UsuarioEntity usuario = usuarioRepository.findById(id);

        if (usuario == null)
            throw new NullPointerException("usuario não encontrado");

        if (usuario.getProduto() == null)
            return null;

        return usuario.getProduto().size();
    }

   
    @Transactional
    public void deleteProdutoFromListaDesejo(Long id, Long idProduto) throws NullPointerException {
        
        UsuarioEntity usuario = usuarioRepository.findById(id);

        if (usuario == null)
            throw new NullPointerException("usuario não encontrado");

        usuario.getProduto().remove(confeitariaRepository.findById(idProduto));
    }

     @Transactional
    public UsuarioResponseDTO create(CadastroBasicoDTO cadastro) throws ConstraintViolationException {

        UsuarioEntity entity = new UsuarioEntity();
       
        entity.setLogin(cadastro.login());
        entity.setSenha(hashService.getHashSenha(cadastro.senha()));
        entity.addPerfis(Perfil.USER);

        usuarioRepository.persist(entity);

        return UsuarioResponseDTO.valueOf(entity);
    }
}