/* Private License

Copyright (c) 2019 Joel Strasser

Only the owner is allowed to use this software.
*/
package xyz.joestr.tachyon.bukkit_plugin.rest;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.bukkit.Bukkit;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import java.util.concurrent.Executor;
import javax.inject.Inject;
import org.bukkit.entity.Player;

/**
 *
 * @author Joel
 */
public class Players {
    
    @Inject
    private Executor executor;
    
    @GET
    @Path("/players")
    @Produces(MediaType.APPLICATION_JSON)
    public void getCoordinates(@Suspended final AsyncResponse asyncResponse) {
        
        executor.execute(() -> {
            
            JsonObject result = new JsonObject();
            
            for(Player player : Bukkit.getOnlinePlayers()) {
                
                result.addProperty(player.getUniqueId().toString(), player.getName());
            }
            
            asyncResponse.resume(new Gson().toJson(result));
        });
    }
}
