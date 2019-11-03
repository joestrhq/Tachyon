/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.joestr.tachyon.information_exchange_server.managers;

import java.util.UUID;
import xyz.joestr.tachyon.api.rest.RestPlayerCoordinates;
import xyz.joestr.tachyon.api.rest.RestPlayerServer;
import xyz.joestr.tachyon.information_exchange_server.configurations.YamlConfiguration;
import xyz.joestr.tachyon.information_exchange_server.utils.RestRequest;

/**
 *
 * @author Joel
 */
public class PlayerCoordinatesManager {
    
    private static PlayerCoordinatesManager instance;
    
    private final YamlConfiguration yamlConfiguration;
    
    private PlayerCoordinatesManager(YamlConfiguration yamlConfiguration) {
        this.yamlConfiguration = yamlConfiguration;
    }
    
    public static PlayerCoordinatesManager getInstance() {
        if(instance == null)
            throw new IllegalStateException(
                "PlayerCoordinatesManager has not been initialized yet!"
            );
        
        return instance;
    }
    
    public static PlayerCoordinatesManager getInstance(YamlConfiguration yamlConfiguration) {
        if(instance == null)
            instance = new PlayerCoordinatesManager(yamlConfiguration);
        
        return instance;
    }
    
    public RestPlayerCoordinates get(UUID uuid) {
        
        RestPlayerServer playerServer =
            RestRequest.make(
                this.yamlConfiguration.getBungeecordInstanceURL()
                + "/player/"
                + uuid.toString()
                + "/server",
                RestPlayerServer.class
            );
        RestPlayerCoordinates playerCoordinates =
            RestRequest.make(
                this.yamlConfiguration.getBukkitInstanceURLs().get(
                    playerServer.getServer()
                )
                + "/player/"
                + uuid.toString()
                + "/coordinates",
                RestPlayerCoordinates.class
            );
        
        return playerCoordinates;
    }
}
