/* Private License

Copyright (c) 2019 Joel Strasser

Only the owner is allowed to use this software.
*/
package xyz.joestr.tachyon.bukkit_plugin.rest;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.util.UUID;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import java.util.concurrent.Executor;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import org.bukkit.entity.Player;
import xyz.joestr.tachyon.api.rest.RestPlayerCoordinates;

/**
 *
 * @author Joel
 */
@Path("/players/{uuid}/coordinates")
public class PlayersCoordinates {
    
    @Inject
    private Executor executor;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public void getCoordinates(@Suspended final AsyncResponse asyncResponse, @PathParam("uuid") String uuid) {
        
        executor.execute(() -> {
            Player player = Bukkit.getPlayer(UUID.fromString(uuid));
            
            if(player == null)
                throw new WebApplicationException(
                    String.format(
                        "Player with UUID '{0}' not found.",
                        uuid
                    ),
                    Response.Status.NOT_FOUND
                );
            
            Location location = player.getLocation();
            
            RestPlayerCoordinates result = new RestPlayerCoordinates();
            result.setX(location.getX());
            result.setY(location.getY());
            result.setZ(location.getZ());
            
            asyncResponse.resume(new Gson().toJson(result));
        });
    }
}
