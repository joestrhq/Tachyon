package at.or.joestr.tachyon.information_exchange_server.webservice.controllers.v1;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.sql.SQLException;
import java.util.UUID;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.HeaderParam;
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
import xyz.joestr.tachyon.api.rest.RestPlayerSettings;
import at.or.joestr.tachyon.information_exchange_server.managers.PlayerSettingsManager;
import at.or.joestr.tachyon.information_exchange_server.utils.APIKeyChecker;
import at.or.joestr.tachyon.information_exchange_server.utils.ETagChecker;

@Path("/players/{uuid}/settings")
public class PlayersSettingsController {
    
    @HEAD
    @Produces(MediaType.APPLICATION_JSON)
    @ManagedAsync
    public void head(
        @Suspended final AsyncResponse asyncResponse,
        @HeaderParam("authorization") String bearerToken,
        @HeaderParam("if-none-match") String ifNoneMatch,
        @PathParam("uuid") String uuid
    ) {
        RestPlayerSettings result = null;
        
        try {
            result = PlayerSettingsManager.getInstance().getSettings(UUID.fromString(uuid));
        } catch (SQLException ex) {
            throw new WebApplicationException(
                ex,
                Response.Status.INTERNAL_SERVER_ERROR
            );
        }
        
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
        
        APIKeyChecker.isPermitted(bearerToken, "players.settings.get");
        
        RestPlayerSettings result = null;
        
        try {
            result = PlayerSettingsManager.getInstance().getSettings(UUID.fromString(uuid));
        } catch (SQLException ex) {
            throw new WebApplicationException(
                ex,
                Response.Status.INTERNAL_SERVER_ERROR
            );
        }
        
        asyncResponse.resume(
            Response
                .ok(result)
                .tag(String.valueOf(result.hashCode()))
                .build()
        );
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @ManagedAsync
    public void put(
        @Suspended final AsyncResponse asyncResponse,
        @HeaderParam("authorization") String bearerToken,
        @PathParam("uuid") String uuid, String json
    ) {
        
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
    }
}
