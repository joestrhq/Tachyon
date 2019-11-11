package xyz.joestr.tachyon.information_exchange_server.rest;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.sql.SQLException;
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
import xyz.joestr.tachyon.api.rest.RestPlayerSettings;
import xyz.joestr.tachyon.information_exchange_server.managers.PlayerSettingsManager;

@Path("/players/{uuid}/settings")
public class PlayersSettingsController {
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ManagedAsync
    public void get(@Suspended final AsyncResponse asyncResponse, @HeaderParam("authorization") String bearerToken, @PathParam("uuid") String uuid) {
        
        RestPlayerSettings result = null;
        try {
            result = PlayerSettingsManager.getInstance().getSettings(UUID.fromString(uuid));
        } catch (SQLException ex) {
            asyncResponse.resume(
                Response.serverError().build()
            );
        }

        asyncResponse.resume(result);
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @ManagedAsync
    public void put(@Suspended final AsyncResponse asyncResponse, @HeaderParam("authorization") String bearerToken, @PathParam("uuid") String uuid, String json) {
        
        JsonObject o = new Gson().fromJson(json, JsonObject.class);
        
        boolean result = false;
        
        try {
            PlayerSettingsManager.getInstance().setSetting(
                UUID.fromString(uuid),
                o.get("key").getAsString(),
                o.get("value").getAsString()
            );
        } catch (SQLException ex) {
            asyncResponse.resume(
                Response.serverError().build()
            );
        }
        
        asyncResponse.resume(
            Response.noContent().build()
        );
    }
}
