package resourcesRest;

import entities.Etudiant;
import entities.EtudiantList;
import entities.EtudiantXml;
import entities.Option;
import metiers.EtudiantBusiness;
import metiers.OptionBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Path("etudiants")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class restEtudiant {
    private final EtudiantBusiness etB = new EtudiantBusiness();
    private final OptionBusiness optB = new OptionBusiness();

    // 1) POST /etudiants  -> 201 Created | 404 Not Found (option inexistante)
    @POST
    public Response addEtudiant(Etudiant etudiant, @Context UriInfo uriInfo) {
        if (etudiant == null || etudiant.getIdentifiant() == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        if (etB.getEtudiantByIdentifiant(etudiant.getIdentifiant()) != null) {
            return Response.status(Response.Status.CONFLICT).build(); // identifiant déjà utilisé
        }
        if (!etB.addEtudiant(etudiant)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        URI location = uriInfo.getAbsolutePathBuilder().path(etudiant.getIdentifiant()).build();
        return Response.created(location).entity(etudiant).build();
    }

    // 2) GET /etudiants  -> 200
    @GET
    public Response getAllEtudiants() {
        return Response.status(Response.Status.OK).entity(etB.getAllEtudiants()).build();
    }

    // 6) GET /etudiants/option?codeOption=1  -> XML (sans l'option) | 404
    //    Chemin littéral "option" : prioritaire sur "{id}" pour JAX-RS
    @GET
    @Path("option")
    @Produces(MediaType.APPLICATION_XML)
    public Response getEtudiantsByOption(@QueryParam("codeOption") int codeOption) {
        Option option = optB.getOptionByCode(codeOption);
        if (option == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        List<EtudiantXml> vue = new ArrayList<>();
        for (Etudiant e : etB.getEtudiantsByOption(option)) {
            vue.add(new EtudiantXml(e));
        }
        return Response.ok(new EtudiantList(vue)).build();
    }

    // 3) GET /etudiants/{id}  -> 200 | 404
    @GET
    @Path("{id}")
    public Response getEtudiant(@PathParam("id") String id) {
        Etudiant e = etB.getEtudiantByIdentifiant(id);
        if (e == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(e).build();
    }

    // 4) DELETE /etudiants/{id}  -> 200 | 404
    @DELETE
    @Path("{id}")
    public Response deleteEtudiant(@PathParam("id") String id) {
        return etB.deleteEtudiant(id)
                ? Response.status(Response.Status.OK).build()
                : Response.status(Response.Status.NOT_FOUND).build();
    }

    // 5) PUT /etudiants/{id}  -> 200 | 404
    @PUT
    @Path("{id}")
    public Response updateEtudiant(@PathParam("id") String id, Etudiant etudiant) {
        if (etudiant == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return etB.updateEtudiant(id, etudiant)
                ? Response.ok(etudiant).build()
                : Response.status(Response.Status.NOT_FOUND).build();
    }
}
