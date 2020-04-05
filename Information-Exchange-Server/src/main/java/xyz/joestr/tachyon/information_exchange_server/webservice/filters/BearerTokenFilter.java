/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.joestr.tachyon.information_exchange_server.webservice.filters;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import xyz.joestr.tachyon.information_exchange_server.managers.ApiKeyManager;

/**
 *
 * @author Joel
 */
@Provider
@Priority(Priorities.AUTHENTICATION)
public class BearerTokenFilter implements ContainerRequestFilter  {
    
    @Override
    public void filter(ContainerRequestContext crc) throws IOException {
        
        if(crc.getRequest() == null) {
            return;
        }
        
        if(!crc.getUriInfo().getPath().startsWith("v1/")) {
            return;
        }
        
        if(!crc.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
            throw new WebApplicationException(
                Response.status(Response.Status.UNAUTHORIZED)
                    .encoding(StandardCharsets.UTF_8.name())
                    .entity("401 - Unauthorized: Bearer Token missing")
                    .build()
            );
        }
        
        boolean isPermitted = false;
        try {
            String permission = crc.getUriInfo()
                .getPathSegments()
                .stream()
                .map(PathSegment::toString)
                .collect(
                    Collectors.joining(".")
                );
            isPermitted = ApiKeyManager
                .getInstance()
                .isPermitted(
                    crc.getHeaders().getFirst(
                        HttpHeaders.AUTHORIZATION
                    ).split(" ")[1],
                    permission
                );
        } catch (SQLException ex) {
            Logger.getLogger(BearerTokenFilter.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(!isPermitted) {
            crc.abortWith(
                Response.status(Response.Status.UNAUTHORIZED)
                .encoding(StandardCharsets.UTF_8.name())
                .entity("401 - Unauthorized: This token is not authorized to use this ressource")
                .build()
            );
        }
    }
}
