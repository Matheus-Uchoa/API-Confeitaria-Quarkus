package unitins.topicos.converterjpa;

import unitins.topicos.entity.BandeiraCartao;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class BandeiraConverter implements AttributeConverter<BandeiraCartao, Integer> {

    @Override
    public Integer convertToDatabaseColumn(BandeiraCartao sexo) {
        
        return sexo == null ? null : sexo.getId();
    }

    @Override
    public BandeiraCartao convertToEntityAttribute(Integer id) {
        
        return BandeiraCartao.valueOf(id);
    }
}
