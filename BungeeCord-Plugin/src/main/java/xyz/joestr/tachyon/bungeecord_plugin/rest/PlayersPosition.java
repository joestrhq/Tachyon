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
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;
import org.glassfish.jersey.server.ManagedAsync;
import xyz.joestr.tachyon.api.rest.RestPlayerPosition;
import xyz.joestr.tachyon.api.rest.RestPlayerServer;
import xyz.joestr.tachyon.bungeecord_plugin.TachyonBungeeCordPlugin;

/**
 *
 * @author Joel
 */
@Path("/players/{uuid}/position")
public class PlayersPosition {
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ManagedAsync
    public void get(@Suspended final AsyncResponse asyncResponse, @PathParam("uuid") UUID uuid) {
        
        ProxiedPlayer player = ProxyServer.getInstance().getPlayer(uuid);

        if(player == null)
            throw new WebApplicationException(
                String.format(
                    "Player with UUID '{0}' not found.",
                    uuid.toString()
                ),
                Response.Status.NOT_FOUND
            );

        Server server = player.getServer();

        RestPlayerServer result = new RestPlayerServer();
        result.setServer(server.getInfo().getName());

        asyncResponse.resume(
            Response
                .ok(new Gson().toJson(result))
                .build()
        );
    }
    
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @ManagedAsync
    public void put(@Suspended final AsyncResponse asyncResponse, @PathParam("uuid") UUID uuid, String json) {
        
        ProxiedPlayer player = ProxyServer.getInstance().getPlayer(uuid);

        if(player == null)
            throw new WebApplicationException(
                String.format(
                    "Player with UUID '{0}' not found.",
                    uuid.toString()
                ),
                Response.Status.NOT_FOUND
            );

        Server server = player.getServer();

        RestPlayerPosition newPosition =
            new Gson().fromJson(json, RestPlayerPosition.class);
        
        
        ProxyServer.getInstance().getScheduler().runAsync(
            TachyonBungeeCordPlugin.getInstance(),
            () -> {
                player.connect(
                    ProxyServer.getInstance().getServerInfo(
                        newPosition.getServer()
                    )
                );
            }
        );

        asyncResponse.resume(
            Response
                .ok()
                .build()
        );
    }
}
