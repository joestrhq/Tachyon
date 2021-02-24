/* Private License

Copyright (c) 2019 Joel Strasser

Only the owner is allowed to use this software.
*/
package at.or.joestr.tachyon.bukkit_plugin.rest;

import com.google.common.base.Charsets;
import com.google.common.net.MediaType;
import com.google.gson.Gson;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import at.or.joestr.tachyon.api.rest.RestPlayerPosition;
import at.or.joestr.tachyon.bukkit_plugin.TachyonBukkitPlugin;

public class EndPointPlayersPosition implements HttpHandler {

    @Override
    public void handleRequest(HttpServerExchange hse) throws Exception {
        
        String accept = hse.getRequestHeaders().get(Headers.ACCEPT).peekFirst();
        
        if(accept == null) {
            hse.setStatusCode(406); // Not Acceptable
            return;
        }
        
        if(accept.equals(MediaType.JSON_UTF_8.toString())) {
            hse.setStatusCode(406); // Not Acceptable
            return;
        }
        
        String paramUuid = hse.getPathParameters().get("uuid").peekFirst();
        
        if(paramUuid == null) {
            hse.setStatusCode(404); // Not found
            return;
        }
        
        UUID uuid = UUID.fromString(paramUuid);
        
        RestPlayerPosition result = new RestPlayerPosition();
        
        Bukkit.getServer().getScheduler().callSyncMethod(
            TachyonBukkitPlugin.getInstance(),
            () -> {
                
                Location l = Bukkit.getServer().getPlayer(uuid).getLocation();
                
                result.setWorld(l.getWorld().getUID());
                result.setX(l.getX());
                result.setY(l.getY());
                result.setZ(l.getZ());
                
                return true;
            }
        );
        
        hse.getResponseHeaders().put(
            Headers.CONTENT_TYPE,
            MediaType.JSON_UTF_8.toString()
        );
        hse.getResponseSender().send(new Gson().toJson(result), Charsets.UTF_8);
    }
    
}