package unitins.topicos.resource;

import java.util.List;

import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import unitins.topicos.application.Result;
import unitins.topicos.dto.IngredienteDTO;
import unitins.topicos.dto.IngredienteResponseDTO;

import unitins.topicos.service.IngredienteService;

@Path("/ingredientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class IngredienteResource {

	@Inject
	IngredienteService ingredienteService;

	@GET
	public List<IngredienteResponseDTO> getAll() {
		return ingredienteService.getAll();
	}

	@GET
	@Path("/{id}")
	public IngredienteResponseDTO findById(@PathParam("id") Long id) {
		return ingredienteService.findById(id);
	}

	@POST
	public Response insert(IngredienteDTO dto) {
		try {
			IngredienteResponseDTO ingrediente = ingredienteService.create(dto);
			return Response.status(Status.CREATED).entity(ingrediente).build();
		} catch (ConstraintViolationException e) {
			Result result = new Result(e.getConstraintViolations());
			return Response.status(Status.NOT_FOUND).entity(result).build();
		}
	}

	@PUT
	@Path("/{id}")
	public IngredienteResponseDTO update(@PathParam("id") Long id, IngredienteDTO ingredienteDTO) {
		return ingredienteService.update(id, ingredienteDTO);
	}

	@DELETE
	@Path("/{id}")
	public Response delete(@PathParam("id") Long id) {
		ingredienteService.delete(id);
		return Response.noContent().build();
	}

	@GET
	@Path("/search/{nome}")
	public List<IngredienteResponseDTO> findByNome(@PathParam("nome") String nome) {
		return ingredienteService.findByNome(nome);
	}

	@GET
	@Path("/count")
	public long count() {
		return ingredienteService.count();
	}

}
