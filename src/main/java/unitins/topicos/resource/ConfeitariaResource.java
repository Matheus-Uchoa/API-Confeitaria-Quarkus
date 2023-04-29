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
import unitins.topicos.dto.ConfeitariaDTO;
import unitins.topicos.dto.ConfeitariaResponseDTO;
import unitins.topicos.service.ConfeitariaService;

@Path("/confeitaria")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ConfeitariaResource {

	@Inject
	ConfeitariaService confeitariaService;

	@GET
	public List<ConfeitariaResponseDTO> getAll() {
		return confeitariaService.getAll();
	}

	@GET
	@Path("/{id}")
	public ConfeitariaResponseDTO findById(@PathParam("id") Long id) {
		return confeitariaService.findById(id);
	}

	@POST
	public Response insert(ConfeitariaDTO dto) {
		try {
			ConfeitariaResponseDTO confeitaria = confeitariaService.create(dto);
			return Response.status(Status.CREATED).entity(confeitaria).build();
		} catch (ConstraintViolationException e) {
			Result result = new Result(e.getConstraintViolations());
			return Response.status(Status.NOT_FOUND).entity(result).build();
		}
	}

	@PUT
	@Path("/{id}")
	public Response update(@PathParam("id") Long id, ConfeitariaDTO dto) {
		try {
			ConfeitariaResponseDTO municipio = confeitariaService.update(id, dto);
			return Response.ok(municipio).build();
		} catch (ConstraintViolationException e) {
			Result result = new Result(e.getConstraintViolations());
			return Response.status(Status.NOT_FOUND).entity(result).build();
		}
	}

@DELETE
@Path("/{id}")
	public Response delete(@PathParam("id") Long id) {
		confeitariaService.delete(id);
		return Response.status(Status.NO_CONTENT).build();
	}

	@GET
	@Path("/count")
	public long count() {
		return confeitariaService.count();
	}

	@GET
	@Path("/search/{nome}")
	public List<ConfeitariaResponseDTO> search(@PathParam("nome") String nome) {
		return confeitariaService.findByNome(nome);

	}
}