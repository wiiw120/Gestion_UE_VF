package resourcesRest;

import entities.Option;
import metiers.OptionBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;

@Path("options")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class restOption {
    private final OptionBusiness optB = new OptionBusiness();

    // 1) POST /options  -> 201 Created
    @POST
    public Response addOption(Option option, @Context UriInfo uriInfo) {
        if (option == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        if (optB.getOptionByCode(option.getCodeOption()) != null) {
            return Response.status(Response.Status.CONFLICT).build(); // code déjà utilisé
        }
        optB.addOption(option);
        URI location = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(option.getCodeOption())).build();
        return Response.created(location).entity(option).build();
    }

    // 2) GET /options   et   3) GET /options?domaine=Mathématiques
    @GET
    public Response getOptions(@QueryParam("domaine") String domaine) {
        List<Option> l = (domaine == null || domaine.isEmpty())
                ? optB.getListeOptions()
                : optB.getOptionsByDomaine(domaine);
        return Response.status(Response.Status.OK).entity(l).build();
    }

    // 6) GET /options/{code}  -> 200 | 404
    @GET
    @Path("{code}")
    public Response getOptionByCode(@PathParam("code") int code) {
        Option option = optB.getOptionByCode(code);
        if (option == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(option).build();
    }

    // 4) DELETE /options/{code}  -> 200 | 404
    @DELETE
    @Path("{code}")
    public Response deleteOption(@PathParam("code") int code) {
        return optB.deleteOption(code)
                ? Response.status(Response.Status.OK).build()
                : Response.status(Response.Status.NOT_FOUND).build();
    }

    // 5) PUT /options/{code}  -> 200 | 404
    @PUT
    @Path("{code}")
    public Response updateOption(@PathParam("code") int code, Option option) {
        if (option == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        option.setCodeOption(code); // le code de l'URL fait foi
        return optB.updateOption(code, option)
                ? Response.ok(option).build()
                : Response.status(Response.Status.NOT_FOUND).build();
    }
}
