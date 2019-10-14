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

/**
 *
 * @author Joel
 */
public class PlayersCoordinates {
    
    @Inject
    private Executor executor;
    
    @GET
    @Path("/players/{uuid}/coordinates")
    @Produces(MediaType.TEXT_PLAIN)
    public void getCoordinates(@Suspended final AsyncResponse asyncResponse, @PathParam("uuid") UUID uuid) {
        
        executor.execute(() -> {
            Location location = Bukkit.getPlayer(uuid).getLocation();
            
            JsonObject result = new JsonObject();
            
            result.addProperty("x", location.getX());
            result.addProperty("y", location.getY());
            result.addProperty("z", location.getZ());
            
            asyncResponse.resume(new Gson().toJson(result));
        });
    }
}
