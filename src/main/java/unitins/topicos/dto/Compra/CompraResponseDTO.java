package unitins.topicos.dto.Compra;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import unitins.topicos.entity.Compra;
import unitins.topicos.entity.MetodoPagamento;
import unitins.topicos.entity.ItemCompra;

public record CompraResponseDTO(
    Long id,
    double totalCompra,
    List<Map<String, Object>> listaDeItens,
    MetodoPagamento formaPagamento
) {
    public CompraResponseDTO(Compra compra) {
        this(compra.getId(),compra.getTotalCompra(), vizualizarProdutos(compra.getListaDeItens()),compra.getPagamento());
    }

    public static Map<String, Object> encontrarProduto(String nome, double valor, int quantidade) {

        Map<String, Object> produto = new HashMap<>();

        produto.put("nome", nome);
        produto.put("valor", valor);
        produto.put("quantidade", quantidade);

        return produto;
    }

    private static List<Map<String, Object>> vizualizarProdutos(List<ItemCompra> lista) {

        List<Map<String, Object>> listaProdutos = new ArrayList<>();

        for (ItemCompra produtos : lista) {

            Map<String, Object> produto = new HashMap<>();

            produto = encontrarProduto(produtos.getProduto().getNome(), produtos.getProduto().getPreco(), produtos.getQuantidade());

            listaProdutos.add(produto);
        }

        return listaProdutos;
    }
}