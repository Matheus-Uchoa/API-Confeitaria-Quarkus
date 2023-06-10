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
import unitins.topicos.dto.MunicipioDTO;
import unitins.topicos.dto.MunicipioResponseDTO;
import unitins.topicos.service.MunicipioService;

@Path("/municipios")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MunicipioResource {

	@Inject
	MunicipioService municipioService;

	private static final Logger LOG = Logger.getLogger(EstadoResource.class);

	@GET
	@RolesAllowed({ "Admin" })

	public List<MunicipioResponseDTO> getAll() {
		LOG.infof("Buscando todos os municipios");
		LOG.debug("ERRO DE DEBUG.");
		return municipioService.getAll();
	}

	@GET
	@Path("/{id}")
	@RolesAllowed({ "Admin" })

	public MunicipioResponseDTO findById(@PathParam("id") Long id) {
		LOG.infof("Buscando municipios por id");
		LOG.debug("ERRO DE DEBUG.");
		return municipioService.findById(id);
	}

	@POST
	@RolesAllowed({ "Admin" })

	public Response insert(MunicipioDTO dto) {
		LOG.infof("Inserindo um municipio: %s", dto.nome());
		Result result = null;
		try {
			MunicipioResponseDTO municipio = municipioService.create(dto);
			LOG.infof("Municipio adicionado na base de dados.");
			return Response.status(Status.CREATED).entity(municipio).build();
		} catch (ConstraintViolationException e) {
			LOG.error("Erro ao incluir um municipio.");
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

	public Response update(@PathParam("id") Long id, MunicipioDTO dto) {
		Result result = null;
		try {
			MunicipioResponseDTO municipio = municipioService.update(id, dto);
			LOG.infof("Municipio (%d) atualizado com sucesso.", id);
			return Response.ok(municipio).build();
		} catch (ConstraintViolationException e) {
			LOG.errorf("Erro ao atualizar um Estado. ", id, e);
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

	@DELETE
	@Path("/{id}")
	@RolesAllowed({ "Admin" })

	public Response delete(@PathParam("id") Long id) throws IllegalArgumentException {
		try {
			municipioService.delete(id);
			  LOG.infof("Municipio excluído com sucesso.", id);
		return Response.status(Status.NO_CONTENT).build();
		} catch (IllegalArgumentException e) {
 LOG.error("Erro ao deletar um municipio: parâmetros inválidos.", e);
            throw e;		}
		
	}

	@GET
	@Path("/count")
	@RolesAllowed({ "Admin" })

	public long count() {
		    LOG.infof("Contando todos os municipios");
        LOG.debug("ERRO DE DEBUG.");
		return municipioService.count();
	}

	@GET
	@Path("/search/{nome}")
	@RolesAllowed({ "Admin" })

	public List<MunicipioResponseDTO> search(@PathParam("nome") String nome) {
		 LOG.infof("Buscando municipio pelo nome. ", nome);
        LOG.debug("ERRO DE DEBUG.");
		return municipioService.findByNome(nome);

	}
}
