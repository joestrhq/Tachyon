/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.joestr.tachyon.api.utils;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;

/**
 *
 * @author Joel
 */
public abstract class RestHttpHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String authBT = exchange.getRequestHeaders().getFirst("authorization");
        if(authBT == null) {
            exchange.sendResponseHeaders(401, 0);
            return;
        }
        
        String body = new String(exchange.getRequestBody().readAllBytes());
        
        String result;
        int code;
        
        switch(exchange.getRequestMethod()) {
            case("GET"): {
                result = this.get();
                code = 200;
                break;
            }
            case("POST"): {
                result = this.post();
                code = 201;
                break;
            }
            case("PUT"): {
                result = this.put();
                code = 204;
                break;
            }
            case("PATCH"): {
                result = this.patch();
                code = 204;
                break;
            }
            case("DELETE"): {
                result = this.delete();
                code = 204;
                break;
            }
            default: {
                exchange.sendResponseHeaders(501, 0);
                return;
            }
        }
        
        OutputStream os = exchange.getResponseBody();
        os.write(result.getBytes());
        os.close();
    }
    
    public abstract boolean useHandleStandard();
    
    public abstract void handleStandard(HttpExchange exchange);
    
    public abstract boolean verifyBearerToken(String bearerToken);
    
    public abstract String get();
    
    public abstract String post();
    
    public abstract String put();
    
    public abstract String patch();
    
    public abstract String delete();
}
