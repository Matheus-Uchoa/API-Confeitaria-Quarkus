package unitins.topicos.repository;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import unitins.topicos.entity.EnderecoEntity;

@ApplicationScoped
public class EnderecoRepository implements PanacheRepository<EnderecoEntity> {

	public List<EnderecoEntity> findByCep(String cep) {
		if (cep == null)
			return null;
		return find("UPPER(cep) LIKE ?1 ", "%" + cep.toUpperCase() + "%").list();
	}

}