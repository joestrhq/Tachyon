package xyz.joestr.tachyon.information_exchange_server.rest;

import java.net.URI;
import java.util.ArrayList;
import java.util.Random;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/op")
public class OpController {
 
    ArrayList<String> randomVids = new ArrayList<>();
    
    @GET
    public Response get() {
        if(this.randomVids.isEmpty()) {
            this.randomVids.add("https://www.youtube.com/watch?v=LCSVR4ZfFoc");
            this.randomVids.add("https://www.youtube.com/watch?v=aWJfLstlWxg");
            this.randomVids.add("https://www.youtube.com/watch?v=CpmDAu_47OE");
        }
        
        return Response.temporaryRedirect(URI.create(
            this.randomVids.get(
                new Random().nextInt(this.randomVids.size())
            )
        )).build();
    }
}
