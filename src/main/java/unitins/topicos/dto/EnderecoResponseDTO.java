package unitins.topicos.dto;

import unitins.topicos.entity.EnderecoEntity;

public record EnderecoResponseDTO(Long id,boolean principal, String logradouro, String bairro, String numero,
		String complemento, String cep, Long id_municipio) {

	public EnderecoResponseDTO(EnderecoEntity endereco) {
		this(endereco.getId(),endereco.isPrincipal(), endereco.getLogradouro(), endereco.getBairro(), endereco.getNumero(),
				endereco.getComplemento(), endereco.getCep(), endereco.getMunicipio().getId());
	}

}
