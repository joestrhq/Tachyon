package xyz.joestr.tachyon.information_exchange_server.rest;

import java.util.UUID;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.server.ManagedAsync;
import xyz.joestr.tachyon.api.rest.RestPlayerCoordinates;
import xyz.joestr.tachyon.information_exchange_server.managers.PlayerCoordinatesManager;
import xyz.joestr.tachyon.information_exchange_server.utils.APIKeyChecker;

@Path("/players/{uuid}/coordinates")
public class PlayersCoordinatesController {
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ManagedAsync
    public void get(
        @Suspended final AsyncResponse asyncResponse,
        @HeaderParam("authorization") String bearerToken,
        @PathParam("uuid") String uuid
    ) {
        
        APIKeyChecker.isPermitted(bearerToken, "players.coordinates.get");
        
        RestPlayerCoordinates result =
            PlayerCoordinatesManager.getInstance()
        .get(UUID.fromString(uuid));

        asyncResponse.resume(result);
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response put(
        @HeaderParam("authorization") String bearerToken,
        @PathParam("uuid") String uuid,
        String json
    ) {
        
        APIKeyChecker.isPermitted(bearerToken, "players.coordinates.put");
        
        return Response.status(
            Response.Status.NOT_IMPLEMENTED
        ).build();
    }
}
