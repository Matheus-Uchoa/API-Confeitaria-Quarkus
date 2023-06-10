package unitins.topicos.repository;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import unitins.topicos.entity.UsuarioEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class UsuarioRepository implements PanacheRepository<UsuarioEntity> {
    
      public List<UsuarioEntity> findByNome(String nome){
        if (nome == null)
            return null;
        return find("UPPER(PessoaFisica.nome) LIKE ?1 ", "%"+nome.toUpperCase()+"%").list();
    }

    public UsuarioEntity findByLoginAndSenha(String login, String senha) {

        if (login == null || senha == null)
            return null;

        return find("login = ?1 AND senha = ?2 ", login, senha).firstResult();
    }

    public UsuarioEntity findByLogin(String login) {

        if (login == null)
            return null;

        return find("login = ?1 ", login).firstResult();
    }
}
