/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.joestr.tachyon.information_exchange_server.managers;

import java.util.UUID;
import xyz.joestr.tachyon.api.rest.RestPlayerPosition;
import xyz.joestr.tachyon.api.rest.RestPlayerServer;
import xyz.joestr.tachyon.information_exchange_server.configurations.YamlConfiguration;
import xyz.joestr.tachyon.information_exchange_server.utils.RestRequest;

/**
 *
 * @author Joel
 */
public class PlayerPositionManager {
    
    private static PlayerPositionManager instance;
    
    private final YamlConfiguration yamlConfiguration;
    
    private PlayerPositionManager(YamlConfiguration yamlConfiguration) {
        this.yamlConfiguration = yamlConfiguration;
    }
    
    public static PlayerPositionManager getInstance() {
        if(instance == null)
            throw new IllegalStateException(
                "PlayerPositionManager has not been initialized yet!"
            );
        
        return instance;
    }
    
    public static PlayerPositionManager getInstance(YamlConfiguration yamlConfiguration) {
        if(instance == null)
            instance = new PlayerPositionManager(yamlConfiguration);
        
        return instance;
    }
    
    public RestPlayerPosition getPosition(UUID uuid) {
        
        RestPlayerPosition bungeecordPosition =
            RestRequest.make(
                this.yamlConfiguration.getBungeecordInstanceURL()
                + "/player/"
                + uuid.toString()
                + "/position",
                RestPlayerPosition.class
            );
        RestPlayerPosition bukkitPosition =
            RestRequest.make(
                this.yamlConfiguration.getBukkitInstanceURLs().get(
                    bungeecordPosition.getServer()
                )
                + "/player/"
                + uuid.toString()
                + "/position",
                RestPlayerPosition.class
            );
        
        RestPlayerPosition result
            = new RestPlayerPosition(
                bungeecordPosition.getServer(),
                bukkitPosition.getWorld(),
                bukkitPosition.getX(),
                bukkitPosition.getY(),
                bukkitPosition.getZ()
            );
        
        return result;
    }
}
