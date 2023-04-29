package unitins.topicos.repository;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import unitins.topicos.entity.AlergenicoEntity;

@ApplicationScoped
public class AlergenicoRepository implements PanacheRepository<AlergenicoEntity> {

	public List<AlergenicoEntity> findByNome(String descricao) {
		if (descricao == null)
			return null;
		return find("UPPER(descricao) LIKE ?1 ", "%" + descricao.toUpperCase() + "%").list();
	}

}