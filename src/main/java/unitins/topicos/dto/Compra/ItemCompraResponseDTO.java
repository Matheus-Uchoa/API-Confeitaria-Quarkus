package unitins.topicos.dto.Compra;

import java.util.HashMap;
import java.util.Map;

import unitins.topicos.entity.ItemCompra;

public record ItemCompraResponseDTO(
    Long id,
    int quant,
    double totalItem,
    Map<String, Object> produto,
    Map<String, Object> usuario
) {
    public ItemCompraResponseDTO(ItemCompra item) {
        this(item.getId(),item.getQuantidade(),item.getTotalItem(),verProduto(item.getProduto().getNome()),verUsuario(item.getUsuario().getLogin()));
    }

    public static Map<String, Object> verUsuario(String nome) {

        Map<String, Object> usuario = new HashMap<>();

        usuario.put("loginUsu", nome);

        return usuario;
    }

    public static Map<String, Object> verProduto(String nome) {

        Map<String, Object> produto = new HashMap<>();

        produto.put("nome", nome);

        return produto;
    }
}
