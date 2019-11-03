/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.joestr.tachyon.information_exchange_server.utils;

import com.google.gson.Gson;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import xyz.joestr.tachyon.api.rest.RestBearer;

/**
 *
 * @author Joel
 */
public class RestRequest {
    
    public static <T extends Object> T make(String url, Class<T> classOfT) {
        HttpURLConnection connection;
        try {
            connection = (HttpURLConnection) new URL(
                url
            ).openConnection();
        } catch (IOException ex) {
            throw new WebApplicationException(
                ex,
                Response.Status.INTERNAL_SERVER_ERROR
            );
        }
        try {
            connection.setRequestMethod("GET");
        } catch (ProtocolException ex) {
            throw new WebApplicationException(
                ex,
                Response.Status.INTERNAL_SERVER_ERROR
            );
        }
        connection.setRequestProperty("Authentication", RestBearer.TOKEN);
        try {
            connection.connect();
        } catch (IOException ex) {
            throw new WebApplicationException(
                ex,
                Response.Status.INTERNAL_SERVER_ERROR
            );
        }
        int responseCode = -1;
        try {
            responseCode = connection.getResponseCode();
        } catch (IOException ex) {
            throw new WebApplicationException(
                ex,
                Response.Status.INTERNAL_SERVER_ERROR
            );
        }
        if(responseCode == 400) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        if(responseCode == 404) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
		T result;
        try {
            result = new Gson().fromJson(
                new String(
                    connection.getInputStream().readAllBytes()
                ),
                classOfT
            );
        } catch (IOException ex) {
            throw new WebApplicationException(
                ex,
                Response.Status.INTERNAL_SERVER_ERROR
            );
        }
        connection.disconnect();
        
        return result;
    }
}
