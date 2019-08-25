package xyz.joestr.tachyon.information_exchange_server.managers;

import java.util.UUID;
import xyz.joestr.tachyon.api.settings.PlayerSettings;

/**
 *
 * @author Joel
 */
public class SettingsManager {
    
    public static PlayerSettings of(UUID uuid) {
        
        return new PlayerSettings();
    }
    
    public static void save(UUID uuid, PlayerSettings settings) {
        
        return;
    }
}
