package unitins.topicos.resource;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import unitins.topicos.dto.UsuarioResponseDto;
import unitins.topicos.service.UsuarioService;

@Path("/usuario")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioResource {

	@Inject
	UsuarioService usuarioService;
	
	@GET
	public List<UsuarioResponseDto> getAll(){
		return usuarioService.getAll();
	}
	
	
}
