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
import unitins.topicos.dto.AlergenicoDTO;
import unitins.topicos.dto.AlergenicoResponseDTO;
import unitins.topicos.service.AlergenicoService;

@Path("/alergenicos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AlergenicoResource {

	@Inject
	AlergenicoService alergenicoService;
	private static final Logger LOG = Logger.getLogger(UsuarioLogadoResource.class);

	@GET
	@RolesAllowed({ "Admin" })

	public List<AlergenicoResponseDTO> getAll() {
		LOG.infof("Buscando todos os alergênicos");
		LOG.debug("ERRO DE DEBUG.");
		return alergenicoService.getAll();
	}

	@GET
	@Path("/{id}")
	@RolesAllowed({ "Admin" })

	public AlergenicoResponseDTO findById(@PathParam("id") Long id) {
		LOG.infof("Buscando alergênicos por ID. ", id);
		LOG.debug("ERRO DE DEBUG.");
		return alergenicoService.findById(id);
	}

	@POST
	@RolesAllowed({ "Admin" })

	public Response insert(AlergenicoDTO dto) {
		LOG.infof("Inserindo um alergênico %s", dto.descricao());
		Result result = null;
		try {
			AlergenicoResponseDTO alergenico = alergenicoService.create(dto);
			return Response.status(Status.CREATED).entity(alergenico).build();
		} catch (ConstraintViolationException e) {
			LOG.error("Erro ao incluir um estado.");
			LOG.debug(e.getMessage());
			result = new Result(e.getConstraintViolations());
			return Response.status(Status.NOT_FOUND).entity(result).build();
		} catch (Exception e) {
			LOG.fatal("Erro sem identificação" + e.getMessage());
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

	public Response update(@PathParam("id") Long id, AlergenicoDTO dto) {
		Result result = null;
		try {
			AlergenicoResponseDTO alergenico = alergenicoService.update(id, dto);
			LOG.infof("Alergenico (%d) atualizado com sucesso.", id);
			return Response.ok(alergenico).build();

		} catch (ConstraintViolationException e) {
			LOG.errorf("Erro ao atualizar um Estado. ", id, e);
			LOG.debug(e.getMessage());
			result = new Result(e.getConstraintViolations());
			return Response.status(Status.NOT_FOUND).entity(result).build();
		} catch (Exception e) {
			LOG.fatal("Erro sem identificação" + e.getMessage());
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
			alergenicoService.delete(id);
			LOG.infof("Alergenico excluído com sucesso",id);
				return Response.status(Status.NO_CONTENT).build();
		} catch (IllegalArgumentException e) {
			LOG.error("Erro ao deletar um estado: parâmetros inválidos.", e);
			throw e;
		}
	
	}

	@GET
	@Path("/count")
	@RolesAllowed({ "Admin" })

	public long count() {
		  LOG.infof("Contando todos os alerênicos");
        LOG.debug("ERRO DE DEBUG.");
		return alergenicoService.count();
	}

	@GET
	@Path("/search/{descricao}")
	@RolesAllowed({ "Admin" })

	public List<AlergenicoResponseDTO> search(@PathParam("descricao") String descricao) {
		 LOG.infof("Buscando alergênico pela descrição. ", descricao);
        LOG.debug("ERRO DE DEBUG");
		return alergenicoService.findByNome(descricao);

	}
}