package xyz.joestr.tachyon.information_exchange_server.webservice.controllers.v1;

import javax.ws.rs.HEAD;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.server.ManagedAsync;

/**
 *
 * @author Joel
 */
public class PenaltiesController {
    
    @HEAD
    @Produces(MediaType.APPLICATION_JSON)
    @ManagedAsync
    public void head(
        @Suspended final AsyncResponse asyncResponse,
        @HeaderParam("if-none-match") String ifNoneMatch
    ) {
        
    }
}
