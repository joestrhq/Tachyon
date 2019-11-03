/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.joestr.tachyon.information_exchange_server;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Joel
 */
public class PooledDatabaseConnection {
    
    private static PooledDatabaseConnection instance = null;
    
    private HikariConfig config = null;
    private HikariDataSource dataSource = null;

    private PooledDatabaseConnection(HikariConfig hikariConfig) {
        
        this.config = hikariConfig;

        this.dataSource = new HikariDataSource(this.config);
    }
    
    public static PooledDatabaseConnection getInstance() {
        
        if(instance == null) {
            throw new IllegalStateException("This pooled connection is not configured!");
        }
        
        return instance;
    }
    
    public static PooledDatabaseConnection getInstance(HikariConfig hikariConfig) {
        
        if(instance == null) {
            instance = new PooledDatabaseConnection(hikariConfig);
        }
        
        return instance;
    }
    
   public Connection getConnection() {
       
        Connection c = null;
        
        try {
            c = this.dataSource.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(PooledDatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

        return c;
   }
}
