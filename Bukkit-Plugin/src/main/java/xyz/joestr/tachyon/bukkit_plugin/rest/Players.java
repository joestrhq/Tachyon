/* Private License

Copyright (c) 2019 Joel Strasser

Only the owner is allowed to use this software.
*/
package xyz.joestr.tachyon.bukkit_plugin.rest;

/*import com.google.gson.Gson;
import com.google.gson.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.bukkit.Bukkit;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.server.ManagedAsync;

/**
 *
 * @author Joel
 *
@Path("/players")
public class Players {
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ManagedAsync
    public void get(@Suspended final AsyncResponse asyncResponse) {
        
        JsonObject result = new JsonObject();

        Bukkit.getOnlinePlayers().forEach((player) -> {
            result.addProperty(player.getUniqueId().toString(), player.getName());
        });

        asyncResponse.resume(
            Response
                .ok(new Gson().toJson(result))
                .build()
        );
    }
}
*/