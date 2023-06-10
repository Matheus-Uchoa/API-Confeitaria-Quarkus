package unitins.topicos.converterjpa;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import unitins.topicos.entity.Sexo;

@Converter(autoApply = true)
public class SexoConverter implements AttributeConverter<Sexo, Integer>{

    @Override
    public Integer convertToDatabaseColumn(Sexo sexo) {
        return sexo == null ? null : sexo.getId();
    }

    @Override
    public Sexo convertToEntityAttribute(Integer id) {
        return Sexo.valueOf(id);
    }
    
}