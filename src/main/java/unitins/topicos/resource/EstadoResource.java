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
import unitins.topicos.dto.EstadoDTO;
import unitins.topicos.dto.EstadoResponseDTO;
import unitins.topicos.service.EstadoService;

@Path("/estados")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EstadoResource {

	@Inject
	EstadoService estadoService;
	private static final Logger LOG = Logger.getLogger(EstadoResource.class);

	@GET
	@RolesAllowed({ "Admin" })
	public List<EstadoResponseDTO> getAll() {
		LOG.infof("Buscando todos os estados");
		LOG.debug("ERRO DE DEBUG.");
		return estadoService.getAll();
	}

	@GET
	@Path("/{id}")
	@RolesAllowed({ "Admin" })

	public EstadoResponseDTO findById(@PathParam("id") Long id) {
		LOG.infof("Buscando estados por ID. ", id);
		LOG.debug("ERRO DE DEBUG.");
		return estadoService.findById(id);
	}

	@POST
	@RolesAllowed({ "Admin" })

	public Response insert(EstadoDTO dto) {
		LOG.infof("Inserindo um estado: %s", dto.nome());
		Result result = null;
		try {
			EstadoResponseDTO estado = estadoService.create(dto);
			LOG.infof("Estado inserido na base de dados.");

			return Response.status(Status.CREATED).entity(estado).build();
		} catch (ConstraintViolationException e) {
			LOG.fatal("Erro sem identificacao: " + e.getMessage());
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
	public Response update(@PathParam("id") Long id, EstadoDTO dto) {
		Result result = null;
		try {
			estadoService.update(id, dto);
			LOG.infof("Estado (%d) atualizado com sucesso.", id);
			return Response
					.status(Status.NO_CONTENT) // 204
					.build();

		} catch (ConstraintViolationException e) {
			LOG.errorf("Erro ao atualizar um Estado. ", id, e);
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
	@Path("/{id}")
	public Response delete(@PathParam("id") Long id) throws IllegalArgumentException {
		try {
			estadoService.delete(id);
			LOG.infof("Estado excluído com sucesso.", id);
			return Response
					.status(Status.NO_CONTENT)
					.build();
		} catch (IllegalArgumentException e) {
			LOG.error("Erro ao deletar um estado: parâmetros inválidos.", e);
			throw e;
		}
	}

	@GET
	@Path("/count")
	@RolesAllowed({ "Admin" })

	public long count() {
		LOG.infof("Contando todos os estados");
		LOG.debug("ERRO DE DEBUG.");
		return estadoService.count();
	}

	@GET
	@Path("/search/{nome}")
	@RolesAllowed({ "Admin" })

	public List<EstadoResponseDTO> search(@PathParam("nome") String nome) {
		LOG.infof("Buscando estado pelo  nome. ", nome);
		LOG.debug("ERRO DE DEBUG.");
		return estadoService.findByNome(nome);

	}
}