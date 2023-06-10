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
import unitins.topicos.dto.usuario.PessoaFisicaDTO;
import unitins.topicos.dto.usuario.PessoaFisicaResponseDTO;
import unitins.topicos.service.PessoaFisicaService;

@Path("/pessoasfisicas")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PessoaFisicaResource {
    
    @Inject
    PessoaFisicaService pessoaFisicaService;
    private static final Logger LOG = Logger.getLogger(EstadoResource.class);

    @GET
     @RolesAllowed({"Admin"})
    public List<PessoaFisicaResponseDTO> getAll() {
           LOG.infof("Buscando todas as Pessoas");
        LOG.debug("ERRO DE DEBUG.");
        return pessoaFisicaService.getAll();
    }

    @GET
    @Path("/{id}")
     @RolesAllowed({"Admin"})
    public PessoaFisicaResponseDTO findById(@PathParam("id") Long id) {
           LOG.infof("Buscando pessoas por ID. ", id);
        LOG.debug("ERRO DE DEBUG.");
        return pessoaFisicaService.findById(id);
    }

    @POST
     @RolesAllowed({"Admin"})
    public Response insert(PessoaFisicaDTO dto) {
        try {
            PessoaFisicaResponseDTO pessoafisica = pessoaFisicaService.create(dto);
            return Response.status(Status.CREATED).entity(pessoafisica).build();
        } catch(ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @PUT
    @Path("/{id}")
     @RolesAllowed({"Admin"})
    public Response update(@PathParam("id") Long id, PessoaFisicaDTO dto) {
        try {
            pessoaFisicaService.update(id, dto);
            return Response.status(Status.NO_CONTENT).build();
        } catch(ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }      
    }

    @DELETE
    @Path("/{id}")
     @RolesAllowed({"Admin"})
    public Response delete(@PathParam("id") Long id)throws IllegalArgumentException {
        try {
            pessoaFisicaService.delete(id);
              LOG.infof("Pessoa excluída com sucesso.", id);
              return Response.status(Status.NO_CONTENT).build();
        } catch (IllegalArgumentException e) {
            LOG.error("Erro ao deletar um estado: parâmetros inválidos.", e);
            throw e;
        }
        
    }


    @GET
    @Path("/count")

    public long count(){
           LOG.infof("contando todas as pessoas");
        LOG.debug("ERRO DE DEBUG.");
        return pessoaFisicaService.count();
    }

    @GET
    @Path("/search/{nome}")
     @RolesAllowed({"Admin"})
    public List<PessoaFisicaResponseDTO> search(@PathParam("nome") String nome){
           LOG.infof("Buscando pessoas por nome. ", nome);
        LOG.debug("ERRO DE DEBUG.");
        return pessoaFisicaService.findByNome(nome);
        
    }
}