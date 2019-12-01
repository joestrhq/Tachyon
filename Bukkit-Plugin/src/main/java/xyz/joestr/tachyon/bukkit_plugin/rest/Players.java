/* Private License

Copyright (c) 2019 Joel Strasser

Only the owner is allowed to use this software.
*/
package xyz.joestr.tachyon.bukkit_plugin.rest;

import com.google.common.base.Charsets;
import com.google.common.net.MediaType;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import io.undertow.util.PathTemplateMatch;
import org.bukkit.Bukkit;

public class Players implements HttpHandler {

    @Override
    public void handleRequest(HttpServerExchange hse) throws Exception {
        
        String accept = hse.getRequestHeaders().get(Headers.ACCEPT).peekFirst();
        
        if(accept == null) {
            hse.setResponseCode(406); // Not Acceptable
            return;
        }
        
        if(accept.equals(MediaType.JSON_UTF_8.toString())) {
            hse.setResponseCode(406); // Not Acceptable
            return;
        }
        
        String uuid = hse.getPathParameters().get("uuid").peekFirst();
        
        if(uuid == null) {
            hse.setResponseCode(404); // Not found
            return;
        }
        
        JsonObject result = new JsonObject();

        Bukkit.getServer().getOnlinePlayers().forEach((player) -> {
            result.addProperty(player.getUniqueId().toString(), player.getName());
        });

        hse.getResponseHeaders().put(
            Headers.CONTENT_TYPE,
            MediaType.JSON_UTF_8.toString()
        );
        hse.getResponseSender().send(new Gson().toJson(result), Charsets.UTF_8);
    }
    
}