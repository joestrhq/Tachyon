package xyz.joestr.tachyon.information_exchange_server.webservice.controllers.v1;

import java.util.UUID;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.server.ManagedAsync;
import xyz.joestr.tachyon.api.rest.RestPlayerPosition;
import xyz.joestr.tachyon.information_exchange_server.managers.PlayerPositionManager;
import xyz.joestr.tachyon.information_exchange_server.utils.APIKeyChecker;
import xyz.joestr.tachyon.information_exchange_server.utils.ETagChecker;

@Path("/players/{uuid}/position")
public class PlayersPositionController {
    
    @HEAD
    @Produces(MediaType.APPLICATION_JSON)
    @ManagedAsync
    public void head(
        @Suspended final AsyncResponse asyncResponse,
        @HeaderParam("authorization") String bearerToken,
        @HeaderParam("if-none-match") String ifNoneMatch,
        @PathParam("uuid") String uuid
    ) {
        APIKeyChecker.isPermitted(bearerToken, "players.position.head");
        
        RestPlayerPosition result = null;
        
        result = PlayerPositionManager.getInstance().getPosition(UUID.fromString(uuid));
        
        Response response;
        
        boolean eTagResult =
            ETagChecker.isCurrent(
                ifNoneMatch,
                String.valueOf(result.hashCode())
            );
        if(eTagResult) {
            response =
                Response
                    .notModified()
                    .tag(String.valueOf(result.hashCode()))
                    .build();
        } else {
            response =
                Response
                    .ok()
                    .tag(String.valueOf(result.hashCode()))
                    .build();
        }
        
        asyncResponse.resume(response);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ManagedAsync
    public void get(
        @Suspended final AsyncResponse asyncResponse,
        @HeaderParam("authorization") String bearerToken,
        @PathParam("uuid") String uuid
    ) {
        
        APIKeyChecker.isPermitted(bearerToken, "players.position.get");
        
        RestPlayerPosition result = null;
        
        result = PlayerPositionManager.getInstance().getPosition(UUID.fromString(uuid));
        
        asyncResponse.resume(
            Response
                .ok(result)
                .tag(String.valueOf(result.hashCode()))
                .build()
        );
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @ManagedAsync
    public void put(
        @Suspended final AsyncResponse asyncResponse,
        @HeaderParam("authorization") String bearerToken,
        @PathParam("uuid") String uuid, String json
    ) {
        
        throw new WebApplicationException(
            "Not implemented!",
            Response.Status.NOT_IMPLEMENTED
        );
        
        /*
        APIKeyChecker.isPermitted(bearerToken, "players.settings.put");
        
        JsonObject o = new Gson().fromJson(json, JsonObject.class);
        
        try {
            PlayerSettingsManager.getInstance().setSetting(
                UUID.fromString(uuid),
                o.get("key").getAsString(),
                o.get("value").getAsString()
            );
        } catch (SQLException ex) {
            throw new WebApplicationException(
                ex,
                Response.Status.INTERNAL_SERVER_ERROR
            );
        }
        
        asyncResponse.resume(
            Response.noContent().build()
        );
        */
    }
}
