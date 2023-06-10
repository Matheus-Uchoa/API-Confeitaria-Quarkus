package unitins.topicos.service;

import java.util.List;
import java.util.stream.Collectors;

import unitins.topicos.dto.Compra.ItemCompraDTO;
import unitins.topicos.dto.Compra.ItemCompraResponseDTO;
import unitins.topicos.entity.ConfeitariaEntity;
import unitins.topicos.entity.ItemCompra;
import unitins.topicos.repository.ConfeitariaRepository;
import unitins.topicos.repository.ItemCompraRepository;
import unitins.topicos.repository.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class ItemCompraService {
    
    @Inject
    ItemCompraRepository itemCompraRepository;

    @Inject
    ConfeitariaRepository confeitariaRepository;

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    Validator validator;

    
    public List<ItemCompraResponseDTO> getAll() {
        List<ItemCompra> list = itemCompraRepository.listAll();
        return list.stream().map(ItemCompraResponseDTO::new).collect(Collectors.toList());
    }

    public ItemCompraResponseDTO findById(Long id) {
        ItemCompra ItemCompra = itemCompraRepository.findById(id);
        if (ItemCompra == null)
            throw new NotFoundException("ItemCompra não encontrada.");
        return new ItemCompraResponseDTO(ItemCompra);
    }

    
    @Transactional
    public ItemCompraResponseDTO create(Long idUsuario, ItemCompraDTO itemCompraDTO) throws ConstraintViolationException {

        ItemCompra entity = new ItemCompra();
        ConfeitariaEntity produto = confeitariaRepository.findById(itemCompraDTO.idProduto());
        if(itemCompraDTO.quant() > produto.getEstoque()){
            throw new NotFoundException("Produto sem Estoque");
        }

        produto.setEstoque(produto.getEstoque()-itemCompraDTO.quant());

        entity.setQuantidade(itemCompraDTO.quant());
        entity.setProduto(confeitariaRepository.findById(itemCompraDTO.idProduto()));
        entity.setUsuario(usuarioRepository.findById(idUsuario));
        entity.setTotalItem(entity.getProduto().getPreco() * entity.getQuantidade());

        itemCompraRepository.persist(entity);

        return new ItemCompraResponseDTO(entity);
    }

    @Transactional
    public void delete(Long idUsuario, Long idItem) {

        ItemCompra entity = itemCompraRepository.findById(idItem);
        ConfeitariaEntity produto = confeitariaRepository.findById(entity.getProduto().getId());

        if(entity.isIdComprado() == true){
            throw new NotAuthorizedException("Esse Item ja foi comprado");
        }

        if(entity.getUsuario().getId() == idUsuario){
            produto.setEstoque(produto.getEstoque() + entity.getQuantidade());
            itemCompraRepository.deleteById(idItem);
        }
        else
            throw new NotAuthorizedException("Você não pode excluir items de outros usuarios");
    }

    public long count() {
        return itemCompraRepository.count();
    }

    
    }
