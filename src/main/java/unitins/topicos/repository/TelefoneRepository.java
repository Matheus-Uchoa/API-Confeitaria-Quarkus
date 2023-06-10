package unitins.topicos.repository;

import java.util.List;


import jakarta.enterprise.context.ApplicationScoped;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import unitins.topicos.entity.TelefoneEntity;

@ApplicationScoped
public class TelefoneRepository implements PanacheRepository<TelefoneEntity> {

	public List<TelefoneEntity> findByNumero(String numero) {
		if (numero == null)
			return null;
		return find("UPPER(numero) LIKE ?1 ", "%" + numero.toUpperCase() + "%").list();
	}

}