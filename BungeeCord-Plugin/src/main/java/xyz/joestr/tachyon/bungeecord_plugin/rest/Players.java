/* Private License

Copyright (c) 2019 Joel Strasser

Only the owner is allowed to use this software.
*/
package xyz.joestr.tachyon.bungeecord_plugin.rest;

import java.util.UUID;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Joel
 */


public class Players {
    
    @GET
    @Path("/players")
    @Produces(MediaType.TEXT_PLAIN)
    public String getCoordinates() {
        
        return "";
    }
}
