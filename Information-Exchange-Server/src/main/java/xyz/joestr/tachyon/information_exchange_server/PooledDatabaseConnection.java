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


HikariDataSource ds = new HikariDataSource(config);

    private PooledDatabaseConnection(HikariConfig hikariConfig) {
        
        this.config = new HikariConfig();
        
        config.setJdbcUrl("jdbc:mysql://localhost:3306/simpsons");
        config.setUsername("bart");
        config.setPassword("51mp50n");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        this.dataSource = new HikariDataSource(hikariConfig);
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
