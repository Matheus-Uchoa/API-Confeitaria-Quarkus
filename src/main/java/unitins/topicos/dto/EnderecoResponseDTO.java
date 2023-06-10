package unitins.topicos.dto;

import java.util.HashMap;
import java.util.Map;

import unitins.topicos.entity.EnderecoEntity;

public record EnderecoResponseDTO(Long id,boolean principal, String logradouro, String bairro, String numero,
		String complemento, String cep, Map<String, Object> municipio) {

	public EnderecoResponseDTO(EnderecoEntity endereco) {
		this(endereco.getId(),endereco.isPrincipal(), endereco.getLogradouro(), endereco.getBairro(), endereco.getNumero(),
				endereco.getComplemento(), endereco.getCep(), verMunicipio(endereco.getMunicipio().getNome(), endereco.getMunicipio().getEstado().getNome()));
	}
	public static Map<String, Object> verMunicipio(String nome, String sigla) {

		Map<String, Object> estado = new HashMap<>();

		estado.put("nome", nome);
		estado.put("estado", sigla);

		return estado;
	}
}
