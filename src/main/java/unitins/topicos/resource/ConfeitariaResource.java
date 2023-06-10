package unitins.topicos.resource;

import java.util.List;

import org.jboss.logging.Logger;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

import unitins.topicos.application.Result;
import unitins.topicos.dto.ConfeitariaDTO;
import unitins.topicos.dto.ConfeitariaResponseDTO;
import unitins.topicos.service.ConfeitariaService;



@Path("/confeitaria")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ConfeitariaResource {

	@Inject
	ConfeitariaService confeitariaService;
    private static final Logger LOG = Logger.getLogger(EstadoResource.class);

	@GET
	    @RolesAllowed({"Admin"})

	public List<ConfeitariaResponseDTO> getAll() {
		  LOG.infof("Buscando todos os Produtos");
        LOG.debug("ERRO DE DEBUG.");
		return confeitariaService.getAll();
	}

	@GET
	@Path("/{id}")
	    @RolesAllowed({"Admin"})

	public ConfeitariaResponseDTO findById(@PathParam("id") Long id) {
		  LOG.infof("Buscando os Produtos por Id", id);
        LOG.debug("ERRO DE DEBUG.");
		return confeitariaService.findById(id);
	}

	@POST
	    @RolesAllowed({"Admin"})

	public Response insert(ConfeitariaDTO dto) {
		Result result = null;
		try {
			ConfeitariaResponseDTO alergenico = confeitariaService.create(dto);
			LOG.infof("Produto criado com sucesso.");
			return Response.status(Status.CREATED).entity(alergenico).build();
		} catch (ConstraintViolationException e) {
			  LOG.error("Erro ao incluir um estado.");
            LOG.debug(e.getMessage());
			 result = new Result(e.getConstraintViolations());
			return Response.status(Status.NOT_FOUND).entity(result).build();
		}catch(Exception e){
			LOG.fatal("Erro sem identificação"+ e.getMessage());
			 result  = new Result(e.getMessage(), false);
		}
		 return Response
                .status(Status.NOT_FOUND)
                .entity(result)
                .build();
	}

	@PUT
	@Path("/{id}")
	    @RolesAllowed({"Admin"})

	public Response update(@PathParam("id") Long id, ConfeitariaDTO dto) {
		Result result = null;
		try {
			ConfeitariaResponseDTO municipio = confeitariaService.update(id, dto);
			LOG.infof("Produto (%d) atualizado com sucesso.", id);
			return Response.ok(municipio).build();
		} catch (ConstraintViolationException e) {
		 LOG.errorf("Erro ao atualizar um Estado. ", id, e);
            LOG.debug(e.getMessage());

            result = new Result(e.getConstraintViolations());
			return Response.status(Status.NOT_FOUND).entity(result).build();
		}
		catch(Exception e){
			 LOG.fatal("Erro sem identificacao: " + e.getMessage());
            result = new Result(e.getMessage(), false);
		}
		        return Response
                .status(Status.NOT_FOUND)
                .entity(result)
                .build();

	}

	@DELETE
	@Path("/{id}")
	    @RolesAllowed({"Admin"})

	public Response delete(@PathParam("id") Long id) throws IllegalArgumentException {
		try {
			confeitariaService.delete(id);
		   LOG.infof("Produto excluído com sucesso.", id);
		return Response.status(Status.NO_CONTENT).build();
		} catch (IllegalArgumentException e) {
			 LOG.error("Erro ao deletar um estado: parâmetros inválidos.", e);
            throw e;
		}
		
	}

	@GET
	@Path("/count")
	    @RolesAllowed({"Admin"})

	public long count() {
		 LOG.infof("Contando todos os Produtos");
        LOG.debug("ERRO DE DEBUG.");
		return confeitariaService.count();
	}

	@GET
	@Path("/search/{descricao}")
	    @RolesAllowed({"Admin"})

	public List<ConfeitariaResponseDTO> search(@PathParam("descricao") String descricao) {
		 LOG.infof("Buscando produtos pela descrição "+ descricao);
        LOG.debug("ERRO DE DEBUG.");
		return confeitariaService.findByNome(descricao);

	}
}