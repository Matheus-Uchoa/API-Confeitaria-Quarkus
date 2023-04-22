package unitins.topicos.repository;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import unitins.topicos.dto.IngredienteDTO;
import unitins.topicos.entity.IngredienteEntity;

@ApplicationScoped
public class IngredienteRepository  implements PanacheRepository<IngredienteEntity>  {

	public List<IngredienteEntity> findByNome(String nome) {
		if (nome == null)
			return null;
		return find("UPPER(nome) LIKE ?1 ", "%" + nome.toUpperCase() + "%").list();
	}
	
	public List<IngredienteEntity> findAllByIds(List<IngredienteDTO> list) {
	    return list("id in ?1", list);
	}

}