package unitins.topicos.repository;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import unitins.topicos.entity.EnderecoEntity;

@ApplicationScoped
public class EnderecoRepository implements PanacheRepository<EnderecoEntity> {

	public List<EnderecoEntity> findByNome(String nome) {
		if (nome == null)
			return null;
		return find("UPPER(nome) LIKE ?1 ", "%" + nome.toUpperCase() + "%").list();
	}

}