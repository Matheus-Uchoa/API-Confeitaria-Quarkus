package unitins.topicos.resource;

import java.util.List;

import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.oracle.svm.core.annotate.Delete;

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

	@GET
	public List<EstadoResponseDTO> getAll() {
		return estadoService.getAll();
	}

	@GET
	@Path("/{id}")
	public EstadoResponseDTO findById(@PathParam("id") Long id) {
		return estadoService.findById(id);
	}

	@POST
	public Response insert(EstadoDTO dto) {
		try {
			EstadoResponseDTO estado = estadoService.create(dto);
			return Response.status(Status.CREATED).entity(estado).build();
		} catch (ConstraintViolationException e) {
			Result result = new Result(e.getConstraintViolations());
			return Response.status(Status.NOT_FOUND).entity(result).build();
		}
	}

	@PUT
	@Path("/{id}")
	public Response update(@PathParam("id") Long id, EstadoDTO dto) {
		try {
			EstadoResponseDTO municipio = estadoService.update(id, dto);
			return Response.ok(municipio).build();
		} catch (ConstraintViolationException e) {
			Result result = new Result(e.getConstraintViolations());
			return Response.status(Status.NOT_FOUND).entity(result).build();
		}
	}

	@Delete
	@Path("/{id}")
	public Response delete(@PathParam("id") Long id) {
		estadoService.delete(id);
		return Response.status(Status.NO_CONTENT).build();
	}

	@GET
	@Path("/count")
	public long count() {
		return estadoService.count();
	}

	@GET
	@Path("/search/{nome}")
	public List<EstadoResponseDTO> search(@PathParam("nome") String nome) {
		return estadoService.findByNome(nome);

	}
}