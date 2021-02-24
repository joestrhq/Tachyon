package at.or.joestr.tachyon.information_exchange_server.webservice.controllers.v1;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        
        JsonObject root = new JsonObject();
        JsonObject application = new JsonObject();
        application.addProperty("name", "Tachyon Information-Exchange-Server");
        application.addProperty("version", "0.1.7-SNAPSHOT");
        root.add("application", application);
        
        asyncResponse.resume(new Gson().toJson(root));
    }
}
