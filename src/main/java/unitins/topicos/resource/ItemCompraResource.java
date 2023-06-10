package unitins.topicos.resource;

import java.util.List;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;

import unitins.topicos.dto.Compra.ItemCompraResponseDTO;
import unitins.topicos.service.ItemCompraService;
import unitins.topicos.service.UsuarioService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/carrinho")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ItemCompraResource {

    @Inject
    ItemCompraService itemCompraSevice;

    @Inject
    JsonWebToken jwt;

    @Inject
    UsuarioService usuarioService;
        private static final Logger LOG = Logger.getLogger(EstadoResource.class);

    @GET
    public List<ItemCompraResponseDTO> getAll() {
          LOG.infof("Buscando todos os itens do carrinho");
        LOG.debug("ERRO DE DEBUG.");
        return itemCompraSevice.getAll();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"Admin","User"})
    public ItemCompraResponseDTO findById(@PathParam("id") Long id) {
        LOG.infof("Buscando item do carrinho por id");
        LOG.debug("ERRO DE DEBUG.");
        return itemCompraSevice.findById(id);
    }

  

    @GET
    @Path("/contaItensCarrinho")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"Admin","User"})
    public long contaItensCarrinho(){
         LOG.infof("Contando os itens do carrinho");
        LOG.debug("ERRO DE DEBUG.");
        return itemCompraSevice.count();
    }

  }