/* Private License

Copyright (c) 2019 Joel Strasser

Only the owner is allowed to use this software.
*/
package xyz.joestr.tachyon.bukkit_plugin.rest;

import com.google.gson.Gson;
import java.util.UUID;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import org.bukkit.entity.Player;
import org.glassfish.jersey.server.ManagedAsync;
import xyz.joestr.tachyon.api.rest.RestPlayerPosition;
import xyz.joestr.tachyon.bukkit_plugin.TachyonBukkitPlugin;

/**
 *
 * @author Joel
 */
@Path("/players/{uuid}/position")
public class PlayersPosition {
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ManagedAsync
    public void get(@Suspended final AsyncResponse asyncResponse, @PathParam("uuid") String uuid) {
        
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

        RestPlayerPosition result = new RestPlayerPosition();
        result.setWorld(location.getWorld().getUID());
        result.setX(location.getX());
        result.setY(location.getY());
        result.setZ(location.getZ());

        asyncResponse.resume(
            Response
                .ok(new Gson().toJson(result))
                .build()
        );
    }
    
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @ManagedAsync
    public void put(@Suspended final AsyncResponse asyncResponse, @PathParam("uuid") String uuid, String json) {
        
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

        RestPlayerPosition newPosition = new Gson().fromJson(json, RestPlayerPosition.class);
        
        Bukkit.getScheduler().runTask(
            TachyonBukkitPlugin.getInstance(),
            () -> {
                Location l = new Location(
                    Bukkit.getWorld(newPosition.getWorld()),
                    newPosition.getX(),
                    newPosition.getY(),
                    newPosition.getZ()
                );
                player.teleport(l);
            }
        );
        
        asyncResponse.resume(
            Response
                .ok()
                .build()
        );
    }
}
