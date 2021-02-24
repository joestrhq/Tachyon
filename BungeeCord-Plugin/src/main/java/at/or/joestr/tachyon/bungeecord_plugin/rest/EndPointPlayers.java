/* Private License

Copyright (c) 2019 Joel Strasser

Only the owner is allowed to use this software.
*/
package at.or.joestr.tachyon.bungeecord_plugin.rest;

import com.google.common.base.Charsets;
import com.google.common.net.MediaType;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import net.md_5.bungee.api.ProxyServer;

public class EndPointPlayers implements HttpHandler {

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
        
        JsonObject result = new JsonObject();

        ProxyServer.getInstance().getPlayers().parallelStream().forEach((player) -> {
            result.addProperty(player.getUniqueId().toString(), player.getName());
        });

        hse.getResponseHeaders().put(
            Headers.CONTENT_TYPE,
            MediaType.JSON_UTF_8.toString()
        );
        hse.getResponseSender().send(new Gson().toJson(result), Charsets.UTF_8);
    }
    
}