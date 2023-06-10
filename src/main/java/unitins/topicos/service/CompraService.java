package unitins.topicos.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import unitins.topicos.dto.Compra.CartaoCreditoDTO;
import unitins.topicos.dto.Compra.CompraResponseDTO;
import unitins.topicos.entity.BandeiraCartao;
import unitins.topicos.entity.PagamentoCartaoCredito;
import unitins.topicos.entity.Compra;
import unitins.topicos.entity.ItemCompra;
import unitins.topicos.entity.Pix;
import unitins.topicos.entity.UsuarioEntity;
import unitins.topicos.repository.ConfeitariaRepository;
import unitins.topicos.repository.CartaoCreditoRepository;
import unitins.topicos.repository.CompraRepository;
import unitins.topicos.repository.ItemCompraRepository;
import unitins.topicos.repository.PixRepository;
import unitins.topicos.repository.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Validator;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class CompraService {

    @Inject
    ItemCompraRepository itemCompraRepository;

    @Inject
    ItemCompraService itemCompraService;

    @Inject
    CartaoCreditoRepository cartaoCreditoRepository;

    @Inject
    CompraRepository compraRepository;

    @Inject
    PixRepository pixRepository;

    @Inject
    ConfeitariaRepository confeitariaRepository;

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    Validator validator;

    public List<CompraResponseDTO> getAll() {
        List<Compra> list = compraRepository.listAll();
        return list.stream().map(CompraResponseDTO::new).collect(Collectors.toList());
    }

    
    public CompraResponseDTO findById(Long id) {
        Compra compra = compraRepository.findById(id);
        if (compra == null)
            throw new NotFoundException("compra não encontrada.");
        return new CompraResponseDTO(compra);
    }

    
    @Transactional
    public CompraResponseDTO comprarItensPix(Long idUsuario) {
        double total = 0;
        Compra entity = new Compra();
        UsuarioEntity usuario = usuarioRepository.findById(idUsuario);
        List<ItemCompra> listaItens = new ArrayList<ItemCompra>();
        listaItens = itemCompraRepository.findItemCompraByUsuario(usuario);

        if(listaItens.isEmpty() == true) throw new NotFoundException("sem itens");

        if(usuario.getEndereco() == null || usuario.getTelefone() == null || usuario.getPessoaFisica() == null) throw new NotFoundException("Complete seu cadastro");

        for(int i=0; i<listaItens.size();i++){
            total = total + listaItens.get(i).getTotalItem();
            listaItens.get(i).setIdComprado(true);
        }

        entity.setListaDeItens(listaItens);
        entity.setTotalCompra(total);
        entity.setUsuario(usuarioRepository.findById(idUsuario));

        Pix pagamento = new Pix(entity.getTotalCompra(), entity.getUsuario().getPessoaFisica().getNome(), entity.getUsuario().getPessoaFisica().getCpf());
        pixRepository.persist(pagamento);

        entity.setPagamento(pagamento);
        compraRepository.persist(entity);

        return new CompraResponseDTO(entity);
    }

    @Transactional
    public CompraResponseDTO comprarItensCartaoCredito(Long idUsuario, CartaoCreditoDTO cartaoCreditoDTO) {
        double total = 0;
        Compra entity = new Compra();
        UsuarioEntity usuario = usuarioRepository.findById(idUsuario);
        List<ItemCompra> listaItens = new ArrayList<ItemCompra>();
        listaItens = itemCompraRepository.findItemCompraByUsuario(usuario);

        if(listaItens.isEmpty() == true) throw new NotFoundException("sem itens");

        for(int i=0; i<listaItens.size();i++){
            total = total + listaItens.get(i).getTotalItem();
            listaItens.get(i).setIdComprado(true);
        }

        entity.setListaDeItens(listaItens);
        entity.setTotalCompra(total);
        entity.setUsuario(usuarioRepository.findById(idUsuario));

        PagamentoCartaoCredito pagamento = new PagamentoCartaoCredito(entity.getTotalCompra(),
                                            cartaoCreditoDTO.numeroCartao(),
                                            cartaoCreditoDTO.titular(),
                                            BandeiraCartao.valueOf(cartaoCreditoDTO.bandeiraCartao()),cartaoCreditoDTO.dataValidade());
        
        cartaoCreditoRepository.persist(pagamento);

        entity.setPagamento(pagamento);
        compraRepository.persist(entity);

        return new CompraResponseDTO(entity);
    }
    
}