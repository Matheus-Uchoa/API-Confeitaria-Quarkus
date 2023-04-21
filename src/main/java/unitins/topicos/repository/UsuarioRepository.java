package unitins.topicos.repository;

import javax.enterprise.context.ApplicationScoped;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import unitins.topicos.entity.UsuarioEntity;
@ApplicationScoped
public class UsuarioRepository implements PanacheRepository<UsuarioEntity>  {

}
