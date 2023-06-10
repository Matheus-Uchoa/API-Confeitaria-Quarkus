package unitins.topicos.resource;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;

import unitins.topicos.application.Result;
import unitins.topicos.dto.usuario.AuthUsuarioDTO;
import unitins.topicos.dto.usuario.CadastroBasicoDTO;
import unitins.topicos.dto.usuario.UsuarioResponseDTO;
import unitins.topicos.entity.UsuarioEntity;
import unitins.topicos.service.HashService;
import unitins.topicos.service.TokenJwtService;
import unitins.topicos.service.UsuarioService;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    HashService hashService;

    @Inject
    UsuarioService usuarioService;

    @Inject
    TokenJwtService tokenService;

    @Inject
    JsonWebToken jwt;
    private static final Logger LOG = Logger.getLogger(UsuarioLogadoResource.class);

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public Response login(AuthUsuarioDTO authDTO) {

        try {
            String hash = hashService.getHashSenha(authDTO.senha());

            UsuarioEntity usuario = usuarioService.findByLoginAndSenha(authDTO.login(), hash);

            if (usuario == null) {
                LOG.warn("Usuário não encontrado: " + authDTO.login());
                return Response.status(Status.NO_CONTENT)
                        .entity("Usuario não encontrado").build();
            }
            LOG.info("Login do usuário bem-sucedido: " + authDTO.login());
            return Response.ok()
                    .header("Authorization", tokenService.generateJwt(usuario))
                    .build();
        } catch (Exception e) {
            LOG.error("Erro durante o login do usuário: " + authDTO.login(), e);
            throw e;
        }

    }

    @POST
    @Path("/cadastro")
    @Produces(MediaType.TEXT_PLAIN)
    public Response cadastro(CadastroBasicoDTO cadastro) {
        try {
            UsuarioResponseDTO Usuario = usuarioService.create(cadastro);
            LOG.info("Cadastro básico bem sucedido: " + cadastro.login());

            return Response.status(Status.CREATED).entity(Usuario).build();

        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao incluir um Cadastro básico.");
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }
}
