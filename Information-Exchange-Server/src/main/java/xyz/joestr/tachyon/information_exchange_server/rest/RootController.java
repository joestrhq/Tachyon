package xyz.joestr.tachyon.information_exchange_server.rest;

import com.google.gson.Gson;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.server.ManagedAsync;

@Path("/")
public class RootController {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ManagedAsync
    public void get(@Suspended final AsyncResponse asyncResponse, @HeaderParam("authorization") String bearerToken) {
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(RootController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        asyncResponse.resume(new Gson().toJson("Hi"));
    }
}
