package unitins.topicos.repository;

import java.util.List;


import javax.enterprise.context.ApplicationScoped;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import unitins.topicos.entity.TelefoneEntity;

@ApplicationScoped
public class TelefoneRepository implements PanacheRepository<TelefoneEntity> {

	public List<TelefoneEntity> findByNome(String nome) {
		if (nome == null)
			return null;
		return find("UPPER(nome) LIKE ?1 ", "%" + nome.toUpperCase() + "%").list();
	}

}