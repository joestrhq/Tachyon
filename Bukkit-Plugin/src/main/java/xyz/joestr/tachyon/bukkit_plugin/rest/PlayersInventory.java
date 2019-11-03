/* Private License

Copyright (c) 2019 Joel Strasser

Only the owner is allowed to use this software.
*/
package xyz.joestr.tachyon.bukkit_plugin.rest;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.bukkit.Bukkit;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.joestr.tachyon.bukkit_plugin.TachyonBukkitPlugin;

/**
 *
 * @author Joel
 */
@Path("/players/{uuid}/iventory")
public class PlayersInventory {
    
    @Inject
    private Executor executor;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public void get(@Suspended final AsyncResponse asyncResponse, @PathParam("uuid") String uuid) {
        
        UUID uid = UUID.fromString(uuid);
        
        executor.execute(() -> {
            
            String json = "";
            
            Future<String> t = Bukkit.getScheduler().callSyncMethod(JavaPlugin.getPlugin(TachyonBukkitPlugin.class), new Callable<String>() {
                @Override
                public String call() throws Exception {
                    return new Gson().toJson(Bukkit.getPlayer(uid).getInventory());
                }
            });
            
            try {
                json = t.get();
            } catch (InterruptedException ex) {
                Logger.getLogger(PlayersInventory.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ExecutionException ex) {
                Logger.getLogger(PlayersInventory.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            asyncResponse.resume(json);
        });
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void put(@Suspended final AsyncResponse asyncResponse, @PathParam("uuid") String uuid, final String json) {
        
        UUID uid = UUID.fromString(uuid);
        
        executor.execute(() -> {
            
            HashMap<Integer, ItemStack> result = null;
            
            Future<HashMap<Integer, ItemStack>> t = Bukkit.getScheduler().callSyncMethod(JavaPlugin.getPlugin(TachyonBukkitPlugin.class), new Callable<HashMap<Integer, ItemStack>>() {
                @Override
                public HashMap<Integer, ItemStack> call() throws Exception {
                    return Bukkit.getPlayer(uid).getInventory().addItem(new Gson().fromJson(json, ItemStack.class));
                }
            });
            
            try {
                result = t.get();
            } catch (InterruptedException ex) {
                Logger.getLogger(PlayersInventory.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ExecutionException ex) {
                Logger.getLogger(PlayersInventory.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            asyncResponse.resume(new Gson().toJson(result));
        });
    }
}
