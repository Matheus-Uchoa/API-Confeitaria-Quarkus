package unitins.topicos.repository;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import unitins.topicos.entity.ConfeitariaEntity;

@ApplicationScoped
public class ConfeitariaRepository implements PanacheRepository<ConfeitariaEntity> {

	public List<ConfeitariaEntity> findByNome(String nome) {
		if (nome == null)
			return null;
		return find("UPPER(nome) LIKE ?1 ", "%" + nome.toUpperCase() + "%").list();
	}

}