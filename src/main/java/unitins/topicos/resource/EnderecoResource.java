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
import unitins.topicos.dto.EnderecoResponseDTO;
import unitins.topicos.dto.EnderecoDTO;
import unitins.topicos.dto.EnderecoResponseDTO;
import unitins.topicos.service.EnderecoService;

@Path("/endereco")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EnderecoResource {

	@Inject
	EnderecoService enderecoService;

	@GET
	public List<EnderecoResponseDTO> getAll() {
		return enderecoService.getAll();
	}

	@GET
	@Path("/{id}")
	public EnderecoResponseDTO findById(@PathParam("id") Long id) {
		return enderecoService.findById(id);
	}

	@POST
	public Response insert(EnderecoDTO dto) {
		try {
			EnderecoResponseDTO municipio = enderecoService.create(dto);
			return Response.status(Status.CREATED).entity(municipio).build();
		} catch (ConstraintViolationException e) {
			Result result = new Result(e.getConstraintViolations());
			return Response.status(Status.NOT_FOUND).entity(result).build();
		}
	}

	@PUT
	@Path("/{id}")
	public Response update(@PathParam("id") Long id, EnderecoDTO dto) {
		try {
			EnderecoResponseDTO municipio = enderecoService.update(id, dto);
			return Response.ok(municipio).build();
		} catch (ConstraintViolationException e) {
			Result result = new Result(e.getConstraintViolations());
			return Response.status(Status.NOT_FOUND).entity(result).build();
		}
	}

	@Delete
	@Path("/{id}")
	public Response delete(@PathParam("id") Long id) {
		enderecoService.delete(id);
		return Response.status(Status.NO_CONTENT).build();
	}

	@GET
	@Path("/count")
	public long count() {
		return enderecoService.count();
	}

	@GET
	@Path("/search/{nome}")
	public List<EnderecoResponseDTO> search(@PathParam("nome") String nome) {
		return enderecoService.findByNome(nome);

	}
}