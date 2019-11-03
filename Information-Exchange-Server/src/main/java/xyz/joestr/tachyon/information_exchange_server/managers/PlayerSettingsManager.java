package xyz.joestr.tachyon.information_exchange_server.managers;

import java.util.UUID;
import xyz.joestr.tachyon.api.rest.RestPlayerSettings;
import xyz.joestr.tachyon.information_exchange_server.configurations.YamlConfiguration;

/**
 *
 * @author Joel
 */
public class PlayerSettingsManager {
    
    private static PlayerSettingsManager instance;
    
    private final YamlConfiguration yamlConfiguration;
    
    private PlayerSettingsManager(YamlConfiguration yamlConfiguration) {
        this.yamlConfiguration = yamlConfiguration;
    }
    
    public static PlayerSettingsManager getInstance() {
        if(instance == null)
            throw new IllegalStateException(
                "PlayerSettingsManager has not been initialized yet!"
            );
        
        return instance;
    }
    
    public static PlayerSettingsManager getInstance(YamlConfiguration yamlConfiguration) {
        if(instance == null)
            instance = new PlayerSettingsManager(yamlConfiguration);
        
        return instance;
    }
    
    public RestPlayerSettings get(UUID uuid) {
        
        return null;
    }
}
