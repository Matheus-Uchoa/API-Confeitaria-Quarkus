package unitins.topicos.resource;

import java.io.IOException;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import unitins.topicos.application.Result;
//import unitins.topicos.dto.CompletarCadastroDTO;
//import unitins.topicos.dto.DadosPessoaisDTO;
//import unitins.topicos.dto.DadosPessoaisResponseDTO;
import unitins.topicos.dto.EnderecoDTO;
import unitins.topicos.dto.Compra.ItemCompraDTO;
//import unitins.topicos.dto.SenhaDTO;
import unitins.topicos.dto.TelefoneDTO;
import unitins.topicos.dto.usuario.CadastroCompletoDTO;
import unitins.topicos.dto.usuario.DadosPessoaisDTO;
import unitins.topicos.dto.usuario.DadosPessoaisResponseDTO;
import unitins.topicos.dto.usuario.SenhaDTO;
import unitins.topicos.dto.usuario.UsuarioResponseDTO;
import unitins.topicos.form.ImageForm;
import unitins.topicos.service.FileService;
import unitins.topicos.service.ItemCompraService;
import unitins.topicos.service.UsuarioService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.ResponseBuilder;
import jakarta.ws.rs.core.Response.Status;

@Path("/usuariologado")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioLogadoResource {

    @Inject
    JsonWebToken jwt;

    @Inject
    UsuarioService usuarioService;

    @Inject
    ItemCompraService itemCompraSevice;

    @Inject
    FileService fileService;
    private static final Logger LOG = Logger.getLogger(UsuarioLogadoResource.class);

    @GET
    @Path("/dadospessoais")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ "Admin", "User" })
    public Response getDadosPessoais() {

        String login = jwt.getSubject();

        DadosPessoaisResponseDTO dadosPessoaisUsuario = new DadosPessoaisResponseDTO(usuarioService.findByLogin(login));
        LOG.infof("Buscando o dados pessoais do usuário: ", login);
        LOG.debug("ERRO DE DEBUG.");
        return Response.ok(dadosPessoaisUsuario).build();
    }

    @PATCH
    @Path("/atualizardadospessoais")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ "Admin", "User" })
    public Response updateDadosPessoais(DadosPessoaisDTO dadosPessoaisDTO) {
        try {
            String login = jwt.getSubject();

            UsuarioResponseDTO usuario = usuarioService.findByLogin(login);
            LOG.info("Dados pessoais atualizados com sucesso.");
            usuarioService.update(usuario.id(), dadosPessoaisDTO);

            return Response.status(Status.NO_CONTENT).build();
        } catch (Exception e) {
            LOG.error("Erro ao atualizar dados pessoais do usuário.", e);

            return Response
                    .status(Status.INTERNAL_SERVER_ERROR)
                    .build();
        }

    }

    @POST
    @Path("/adicionaritemcarrinho")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ "Admin", "User" })
    public Response adicionandoItem(ItemCompraDTO itemDTO) {
        try {
            String login = jwt.getSubject();

            UsuarioResponseDTO usuario = usuarioService.findByLogin(login);
            itemCompraSevice.create(usuario.id(), itemDTO);
            LOG.info("Produto adicionado com sucesso no carrinho");

            return Response.status(Status.CREATED).build();
        } catch (Exception e) {
            LOG.errorf("Erro ao adicionar item no carrinho", e);

            return Response
                    .status(Status.INTERNAL_SERVER_ERROR)
                    .build();
        }

    }

    @DELETE
    @Path("/deletaritemcarrinho")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ "Admin", "User" })
    public Response deletarItem(Long idItemCarrinho) {

        String login = jwt.getSubject();

        UsuarioResponseDTO usuario = usuarioService.findByLogin(login);

        itemCompraSevice.delete(usuario.id(), idItemCarrinho);
        LOG.info("Item excluído com sucesso do carrinho");
        return Response.status(Status.CREATED).build();
    }

    @PATCH
    @Path("/senhausuario")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ "Admin", "User" })
    public Response updateSenha(SenhaDTO senhaDTO) {

        String login = jwt.getSubject();

        UsuarioResponseDTO usuario = usuarioService.findByLogin(login);

        usuarioService.update(usuario.id(), senhaDTO);
        LOG.info("Senha alterada com sucesso");

        return Response.status(Status.NO_CONTENT).build();
    }

    @PATCH
    @Path("/completarcadastro")
    @RolesAllowed({ "User" })
    public Response update(CadastroCompletoDTO dto) {
        try {
            String login = jwt.getSubject();

            UsuarioResponseDTO usuario = usuarioService.findByLogin(login);
            usuarioService.update(usuario.id(), dto);
                    LOG.info("Cadastro completado com sucesso");

            return Response.ok(usuario).status(Status.NO_CONTENT).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @PATCH
    @Path("/telefoneusuario")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ "Admin", "User" })
    public Response updateTelefonePrincipal(TelefoneDTO telefonePrincipalDTO) {

        String login = jwt.getSubject();

        UsuarioResponseDTO usuario = usuarioService.findByLogin(login);

        usuarioService.updateTelefonePrincipal(usuario.id(), telefonePrincipalDTO);

        return Response.status(Status.NO_CONTENT).build();
    }

    @PATCH
    @Path("/enderecousuario")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ "Admin", "User" })
    public Response updateEndereco(EnderecoDTO enderecoDTO) {

        String login = jwt.getSubject();

        UsuarioResponseDTO usuario = usuarioService.findByLogin(login);

        usuarioService.updateEndereco(usuario.id(), enderecoDTO);

        return Response.status(Status.NO_CONTENT).build();
    }

    @PATCH
    @Path("/novaimagem")
    @RolesAllowed({ "Admin", "User" })
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response salvarImagem(@MultipartForm ImageForm form) {
        String nomeImagem = "";

        try {
            nomeImagem = fileService.salvarImagemUsuario(form.getImagem(), form.getNomeImagem());
        } catch (IOException e) {
            Result result = new Result(e.getMessage());
            return Response.status(Status.CONFLICT).entity(result).build();
        }

        // obtendo o login a partir do token
        String login = jwt.getSubject();
        UsuarioResponseDTO usuario = usuarioService.findByLogin(login);

        usuario = usuarioService.update(usuario.id(), nomeImagem);

        return Response.ok(usuario).build();

    }

    @GET
    @Path("/download/{nomeImagem}")
    @RolesAllowed({ "Admin", "User" })
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response download(@PathParam("nomeImagem") String nomeImagem) {
        ResponseBuilder response = Response.ok(fileService.download(nomeImagem));
        response.header("Content-Disposition", "attachment;filename=" + nomeImagem);
        return response.build();
    }
}