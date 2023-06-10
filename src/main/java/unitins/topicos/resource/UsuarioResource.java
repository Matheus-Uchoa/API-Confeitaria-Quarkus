package unitins.topicos.resource;

import java.util.List;

import org.jboss.logging.Logger;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
//import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
//import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import unitins.topicos.application.Result;
import unitins.topicos.dto.usuario.ListaDesejoDTO;
import unitins.topicos.dto.usuario.ListaDesejoResponseDTO;
import unitins.topicos.dto.usuario.UsuarioDTO;
//import unitins.topicos.application.Result;
import unitins.topicos.dto.usuario.UsuarioResponseDTO;
import unitins.topicos.service.UsuarioService;

@Path("/usuarios")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioResource {

    @Inject
    UsuarioService usuarioService;
    private static final Logger LOG = Logger.getLogger(UsuarioResource.class);

    @GET
    @RolesAllowed({ "Admin" })

    public List<UsuarioResponseDTO> getAll() {
        LOG.info("Buscando todos os Usuários.");
        LOG.debug("ERRO DE DEBUG.");
        return usuarioService.getAll();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({ "Admin" })

    public UsuarioResponseDTO findById(@PathParam("id") Long id) {
        LOG.info("Buscando usuários por id.");
        LOG.debug("ERRO DE DEBUG.");
        return usuarioService.findById(id);
    }

    @POST
    @RolesAllowed({ "Admin" })
    public Response insert(UsuarioDTO dto) {
        Result result = null;

        try {
            UsuarioResponseDTO Usuario = usuarioService.create(dto);
            LOG.infof("Usuário criado com sucesso.");
            return Response.status(Status.CREATED).entity(Usuario).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao incluir Usuário.");
            LOG.debug(e.getMessage());
            result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        } catch (Exception e) {
            LOG.fatal("Erro sem identificacao: " + e.getMessage());
            result = new Result(e.getMessage(), false);

        }
        return Response
                .status(Status.NOT_FOUND)
                .entity(result)
                .build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({ "Admin" })
    public Response update(@PathParam("id") Long id, UsuarioDTO dto) {
        Result result = null;
        try {
            UsuarioResponseDTO usuario = usuarioService.update(id, dto);
            return Response.ok(usuario).status(Status.NO_CONTENT).build();
        } catch (ConstraintViolationException e) {
            result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({ "Admin" })

    public Response delete(@PathParam("id") Long id) throws IllegalArgumentException, NotFoundException {
        try {
            usuarioService.delete(id);
            return Response.status(Status.NO_CONTENT).build();
        } catch (Exception e) {
            LOG.error("Erro ao deletar usuário, parâmetros inválidos.", e);
            throw e;
        }
    }

    @GET
    @Path("/count")
    @RolesAllowed({ "Admin" })

    public long count() {
        LOG.infof("Quantitativo usuários");
        LOG.debug("ERRO DE DEBUG.");
        return usuarioService.count();
    }

    @GET
    @Path("/searchByNome/{nome}")
    @RolesAllowed({ "Admin" })
    public List<UsuarioResponseDTO> getByNome(@PathParam("nome") String nome) throws NullPointerException {
        LOG.info("Buscando usuário por nome");
        LOG.debug("ERRO DE DEBUG.");

        return usuarioService.getByNome(nome);
    }

    @GET
    @Path("/listadesejo/{id}")
    @RolesAllowed({ "Admin" })
    public ListaDesejoResponseDTO getListaDesejo(@PathParam("id") Long id) {

        return usuarioService.getListaDesejo(id);
    }

    @POST
    @Path("/listadesejo")
    @Transactional
    @RolesAllowed({ "Admin", "User" })
    public Response insertListaDesejo(ListaDesejoDTO listaDto) {
        Result result = null;

        try {

            usuarioService.insertListaDesejo(listaDto);
            LOG.info("Produto adicionado na lista de desejo");
            return Response
                    .status(Status.CREATED)
                    .build();
        } catch (ConstraintViolationException e) {

            LOG.error("Erro ao incluir um produto na lista de desejos.");
            LOG.debug(e.getMessage());

            result = new Result(e.getConstraintViolations());
        } catch (Exception e) {
            LOG.fatal("Erro sem identificacao: " + e.getMessage());
            result = new Result(e.getMessage(), false);

        }
        return Response
                .status(Status.NOT_FOUND)
                .entity(result)
                .build();
    }

    @DELETE
    @Path("/lista_desejo/{idUsuario}/{idProduto}")
    @RolesAllowed({ "Admin", "User" })
    public Response deleteProdutoFromListaDesejo(@PathParam("idUsuario") Long idUsuario,
            @PathParam("idProduto") Long idProdutoListaDesejo) {
        try {
            usuarioService.deleteProdutoFromListaDesejo(idUsuario, idProdutoListaDesejo);
            LOG.infof("Produto (%d) removido da lista de desejos do usuário (%d).", idProdutoListaDesejo, idUsuario);

            return Response
                    .status(Status.NO_CONTENT)
                    .build();

        } catch (Exception e) {
            LOG.error("Erro ao remover produto da lista de desejos.", e);

            return Response
                    .status(Status.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @GET
    @Path("/lista_desejo/count/{id}")
    @RolesAllowed({ "Admin", "User" })
    public Integer countListaDesejo(@PathParam("id") Long id) {
        LOG.infof("Contando todos os produtos do usuário ", id);
        LOG.debug("ERRO DE DEBUG.");
        return usuarioService.countListaDesejo(id);
    }
}