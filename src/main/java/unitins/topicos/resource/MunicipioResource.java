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

import unitins.topicos.dto.MunicipioDto;
import unitins.topicos.dto.MunicipioResponseDTO;
import unitins.topicos.service.MunicipioService;
import unitins.topicos.application.Result;

@Path("/municipios")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MunicipioResource {

	@Inject
	MunicipioService municipioService;

	@GET
	public List<MunicipioResponseDTO> getAll() {
		return municipioService.getAll();
	}

	@GET
	@Path("/{id}")
	public MunicipioResponseDTO findById(@PathParam("id") Long id) {
		return municipioService.findById(id);
	}

	@POST
	public Response insert(MunicipioDto dto) {
		try {
			MunicipioResponseDTO municipio = municipioService.create(dto);
			return Response.status(Status.CREATED).entity(municipio).build();
		} catch (ConstraintViolationException e) {
			Result result = new Result(e.getConstraintViolations());
			return Response.status(Status.NOT_FOUND).entity(result).build();
		}
	}

	@PUT
	@Path("/{id}")
	public Response update(@PathParam("id") Long id, MunicipioDto dto) {
		try {
			MunicipioResponseDTO municipio = municipioService.update(id, dto);
			return Response.ok(municipio).build();
		} catch (ConstraintViolationException e) {
			Result result = new Result(e.getConstraintViolations());
			return Response.status(Status.NOT_FOUND).entity(result).build();
		}
	}

	@Delete
	@Path("/{id}")
	public Response delete(@PathParam("id") Long id) {
		municipioService.delete(id);
		return Response.status(Status.NO_CONTENT).build();
	}

	@GET
	@Path("/count")
	public long count() {
		return municipioService.count();
	}

	@GET
	@Path("/search/{nome}")
	public List<MunicipioResponseDTO> search(@PathParam("nome") String nome) {
		return municipioService.findByNome(nome);

	}
}
