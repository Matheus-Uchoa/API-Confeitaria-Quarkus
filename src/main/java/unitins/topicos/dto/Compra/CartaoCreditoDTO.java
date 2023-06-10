package unitins.topicos.dto.Compra;

import java.util.Date;

public record CartaoCreditoDTO(
    String numeroCartao,
    String titular,
    Date dataValidade,
    String codigoSeguranca,
    Integer bandeiraCartao
) {
    
}
