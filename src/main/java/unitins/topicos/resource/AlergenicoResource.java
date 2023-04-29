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
import unitins.topicos.dto.AlergenicoDTO;
import unitins.topicos.dto.AlergenicoResponseDTO;
import unitins.topicos.service.AlergenicoService;

@Path("/alergenico")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AlergenicoResource {

	@Inject
	AlergenicoService alergenicoService;

	@GET
	public List<AlergenicoResponseDTO> getAll() {
		return alergenicoService.getAll();
	}

	@GET
	@Path("/{id}")
	public AlergenicoResponseDTO findById(@PathParam("id") Long id) {
		return alergenicoService.findById(id);
	}

	@POST
	public Response insert(AlergenicoDTO dto) {
		try {
			AlergenicoResponseDTO alergenico = alergenicoService.create(dto);
			return Response.status(Status.CREATED).entity(alergenico).build();
		} catch (ConstraintViolationException e) {
			Result result = new Result(e.getConstraintViolations());
			return Response.status(Status.NOT_FOUND).entity(result).build();
		}
	}

	@PUT
	@Path("/{id}")
	public Response update(@PathParam("id") Long id, AlergenicoDTO dto) {
		try {
			AlergenicoResponseDTO municipio = alergenicoService.update(id, dto);
			return Response.ok(municipio).build();
		} catch (ConstraintViolationException e) {
			Result result = new Result(e.getConstraintViolations());
			return Response.status(Status.NOT_FOUND).entity(result).build();
		}
	}

	@DELETE
	@Path("/{id}")
	public Response delete(@PathParam("id") Long id) {
		alergenicoService.delete(id);
		return Response.status(Status.NO_CONTENT).build();
	}

	@GET
	@Path("/count")
	public long count() {
		return alergenicoService.count();
	}

	@GET
	@Path("/search/{descricao}")
	public List<AlergenicoResponseDTO> search(@PathParam("descricao") String descricao) {
		return alergenicoService.findByNome(descricao);

	}
}