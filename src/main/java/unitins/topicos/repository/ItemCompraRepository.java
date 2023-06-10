package unitins.topicos.repository;

import java.util.List;

import unitins.topicos.entity.ConfeitariaEntity;
import unitins.topicos.entity.ItemCompra;
import unitins.topicos.entity.UsuarioEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ItemCompraRepository implements PanacheRepository<ItemCompra> {
    
    public List<ItemCompra> findByProduto (ConfeitariaEntity produto) {

        if (produto == null)
            return null;

        return find("FROM ItemCompra WHERE produto = ?1", produto).list();
    }

    public List<ItemCompra> findItemCompraByUsuario (UsuarioEntity usuario) {

        if (usuario == null)
            return null;

            boolean item = false;

        return find("FROM ItemCompra WHERE usuario = ?1 AND idComprado = ?2", usuario,item).list();
    }

}