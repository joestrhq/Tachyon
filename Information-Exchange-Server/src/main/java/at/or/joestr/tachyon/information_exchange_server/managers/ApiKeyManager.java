/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.or.joestr.tachyon.information_exchange_server.managers;

import at.or.joestr.tachyon.information_exchange_server.models.ApiKeyModel;
import io.ebean.DB;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles interactions for API keys.
 * 
 * @author Joel
 */
public class ApiKeyManager {
    private static ApiKeyManager INSTANCE;
    
    private ApiKeyManager() {
    }
    
    public static ApiKeyManager getInstance() {
        if(INSTANCE == null)
            throw new IllegalStateException(
                "APIKeyManager has not been initialized yet!"
            );
        
        return INSTANCE;
    }
    
    public boolean isPermitted(String apiKey, String category) throws SQLException {
        
        ApiKeyModel result =
          DB.find(ApiKeyModel.class)
            .where()
              .eq("id", apiKey)
              .and()
                .eq("category", category)
                .or().eq("category", "*").endOr()
              .endAnd()
            .findOne();
        
        return result != null;
    }
}
