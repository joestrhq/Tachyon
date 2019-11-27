package xyz.joestr.tachyon.information_exchange_server.managers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;
import xyz.joestr.tachyon.api.rest.RestPlayerSettings;
import xyz.joestr.tachyon.information_exchange_server.PooledDatabaseConnection;
import xyz.joestr.tachyon.information_exchange_server.configurations.YamlConfiguration;

/**
 *
 * @author Joel
 */
public class PlayerSettingsManager {
    
    private static PlayerSettingsManager instance;
    
    private final YamlConfiguration yamlConfiguration;
    private final PooledDatabaseConnection pDC;
    
    private PlayerSettingsManager(YamlConfiguration yamlConfiguration, PooledDatabaseConnection pDC) {
        this.yamlConfiguration = yamlConfiguration;
        this.pDC = pDC;
    }
    
    public static PlayerSettingsManager getInstance() {
        if(instance == null)
            throw new IllegalStateException(
                "PlayerSettingsManager has not been initialized yet!"
            );
        
        return instance;
    }
    
    public static PlayerSettingsManager getInstance(YamlConfiguration yamlConfiguration, PooledDatabaseConnection pDC) throws SQLException {
        if(instance != null) {
            throw new IllegalStateException(
                "PlayerSettingsManager has already been initialized!"
            );
        }
        
        Statement s = pDC.getConnection().createStatement();
        ResultSet r = s.executeQuery("SHOW TABLES LIKE 'tachyon_playersettings';");
        
        if(!r.first()) {
            throw new IllegalStateException(
                "The table tachyon_playersettings has not been found!"
            );
        }
        
        instance = new PlayerSettingsManager(yamlConfiguration, pDC);
        
        return instance;
    }
    
    public RestPlayerSettings getSettings(UUID uuid) throws SQLException {
        
        RestPlayerSettings result = new RestPlayerSettings();
        
        PreparedStatement pS = pDC.getConnection().prepareStatement(
            "SELECT * FROM `tachyon_playersettings` WHERE `uuid` = ?;"
        );
        
        pS.setString(0, uuid.toString());
        ResultSet rs = pS.executeQuery();
        
        while(rs.next()) {
            result.setSetting(
                rs.getString(2),
                rs.getString(3)
            );
        }
        
        return result;
    }
    
    /**
     * Inserts or overwrites a setting of a player.
     * @param uuid
     * @param key
     * @param value
     * @return 
     */
    public boolean setSetting(UUID uuid, String key, String value) throws SQLException {
        
        PreparedStatement pS = pDC.getConnection().prepareStatement(
            "REPLACE INTO `tachyon_playersettings` VALUES(?, ?, ?);"
        );
        
        pS.setString(0, uuid.toString());
        pS.setString(1, key);
        pS.setString(2, value);
        
        int result = pS.executeUpdate();
        
        return result == 1;
    }
}
