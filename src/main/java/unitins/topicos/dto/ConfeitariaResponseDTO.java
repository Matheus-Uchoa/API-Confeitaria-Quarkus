package unitins.topicos.dto;



import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.HashMap;
import java.util.Map;
import unitins.topicos.entity.Categoria;
import unitins.topicos.entity.ConfeitariaEntity;

public record ConfeitariaResponseDTO(long id,String nome,

		String descricao,

		Double preco, Integer estoque, @JsonInclude(JsonInclude.Include.NON_NULL) Categoria categoria,
		 Double peso,  Map<String, Object> alergenico, String nomeImagem

) {

	public ConfeitariaResponseDTO(ConfeitariaEntity confeitaria) {
		this(confeitaria.getId(),confeitaria.getNome(), confeitaria.getDescricao(), confeitaria.getPreco(), confeitaria.getEstoque(),
				confeitaria.getCategoria(), confeitaria.getPeso(), verAlergenico(confeitaria.getAlergenico().getDescricao()),
				confeitaria.getNomeImagem()
				);
	}
	public static Map<String, Object> verAlergenico(String descricao) {

		Map<String, Object> alergenico = new HashMap<>();

		alergenico.put("descricao", descricao);

		return alergenico;
}
}
