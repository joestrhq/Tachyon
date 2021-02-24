/* Private License

Copyright (c) 2019 Joel Strasser

Only the owner is allowed to use this software.
*/
package at.or.joestr.tachyon.bungeecord_plugin.rest;

import com.google.common.base.Charsets;
import com.google.common.net.MediaType;
import com.google.gson.Gson;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import java.util.UUID;
import net.md_5.bungee.api.ProxyServer;
import at.or.joestr.tachyon.api.rest.RestPlayerPosition;
import at.or.joestr.tachyon.bungeecord_plugin.TachyonBungeeCordPlugin;

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
        
        ProxyServer.getInstance().getScheduler().runAsync(
            TachyonBungeeCordPlugin.getInstance(),
            () -> {
                
                String server =
                    ProxyServer.getInstance().getPlayer(uuid).getServer().getInfo().getName();
                
                result.setServer(server);
            }
        );
        
        hse.getResponseHeaders().put(
            Headers.CONTENT_TYPE,
            MediaType.JSON_UTF_8.toString()
        );
        hse.getResponseSender().send(new Gson().toJson(result), Charsets.UTF_8);
    }
    
}