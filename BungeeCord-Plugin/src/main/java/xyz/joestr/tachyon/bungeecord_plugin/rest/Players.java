/* Private License

Copyright (c) 2019 Joel Strasser

Only the owner is allowed to use this software.
*/
package xyz.joestr.tachyon.bungeecord_plugin.rest;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.util.UUID;
import java.util.concurrent.Executor;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import net.md_5.bungee.api.ProxyServer;

/**
 *
 * @author Joel
 */
@Path("/players")
public class Players {
    
    @Inject
    private Executor executor;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public void getCoordinates(@Suspended final AsyncResponse asyncResponse) {
        
        executor.execute(() -> {
            
            JsonObject result = new JsonObject();
            
            ProxyServer.getInstance().getPlayers().forEach((player) -> {
                result.addProperty(player.getUniqueId().toString(), player.getName());
            });
            
            asyncResponse.resume(new Gson().toJson(result));
        });
    }
}
