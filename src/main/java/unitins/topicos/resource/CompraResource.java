package unitins.topicos.resource;

import java.util.List;
// import org.jboss.logging.Logger;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;

import unitins.topicos.dto.Compra.CartaoCreditoDTO;
import unitins.topicos.dto.Compra.CompraResponseDTO;
import unitins.topicos.dto.usuario.UsuarioResponseDTO;
import unitins.topicos.service.CompraService;
import unitins.topicos.service.UsuarioService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/compras")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CompraResource {

    @Inject
    CompraService compraService;

    @Inject
    JsonWebToken jwt;

    @Inject
    UsuarioService usuarioService;

    private static final Logger LOG = Logger.getLogger(CompraResource.class);

    @GET
    public List<CompraResponseDTO> getAll() {
        LOG.infof("Buscando todos os alergênicos");
        LOG.debug("ERRO DE DEBUG.");
        return compraService.getAll();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({ "Admin", "User" })
    public CompraResponseDTO findById(@PathParam("id") Long id) {
        LOG.infof("Buscando compras pelo id");
        LOG.debug("ERRO DE DEBUG.");
        return compraService.findById(id);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ "Admin", "User" })
    @Path("/Comprapix")
    public Response comprarItensPix() {
        LOG.infof("Comprando por pix");

        String login = jwt.getSubject();

        UsuarioResponseDTO usuario = usuarioService.findByLogin(login);

        compraService.comprarItensPix(usuario.id());
        LOG.info("Pagamento com PIX efetuado com sucesso.");

        return Response.status(Status.CREATED).build();

    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ "Admin", "User" })
    @Path("/pagamentocartao")
    public Response comprarItensCartaoCredito(CartaoCreditoDTO cartaoCreditoDTO) {
        LOG.infof("Comprando por Cartão de Crédito");
        String login = jwt.getSubject();

        UsuarioResponseDTO usuario = usuarioService.findByLogin(login);

        compraService.comprarItensCartaoCredito(usuario.id(), cartaoCreditoDTO);
        LOG.info("Compra por cartão efetuada com sucesso");
        return Response.status(Status.CREATED).build();

    }

}