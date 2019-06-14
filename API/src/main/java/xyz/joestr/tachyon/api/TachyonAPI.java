/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.joestr.tachyon.api;

import java.lang.reflect.Type;
import xyz.joestr.tachyon.api.chatfilter.ChatFilter;


/**
 *
 * @author Joel
 * @version ${project.version}
 */
public abstract class TachyonAPI {
    
    private static TachyonAPI instance = null;
    
    /**
     * Sets the instance of the API. This method may only be called once per an
     * application.
     *
     * @param instance A class which extends the {@link xyz.joestr.tachyon.api.TachyonAPI}
     */
    public static void setInstance(TachyonAPI instance) {
        
        // If the argument is null
        if(instance == null) {
            throw new IllegalArgumentException("Argument cannot be null!");
        }
        
        // If the instance is not null anymore
        if(TachyonAPI.instance != null) {
            throw new IllegalStateException("API instance already set!");
        }
        
        // Set the instance
        TachyonAPI.instance = instance;
    }
    
    /**
     * Singleton access for the API to prevent multiple instances of it.
     * @return The {@link xyz.joestr.tachyon.api.TachyonAPI API} itself.
     */
    public TachyonAPI getInstance() {
        
        // Return the instance
        return TachyonAPI.instance;
    }
    
    /**
     * Get the amount of players on a server.
     * 
     * @param serverName The name of the server you want to query.
     * @return The amount of players on a server.
     */
    public abstract int getOnlineCountForServer(String serverName);
    
    /**
     * Register a new chat filter. (Is only available in BungeeCord
     * implementations of this API.)
     * 
     * @param chatFilter The chat filter to register
     */
    public abstract void registerChatFilter(ChatFilter chatFilter);
    
    /**
     * Unregister a chat filter. (Is only available in BungeeCord
     * implementations of this API.)
     * 
     * @param chatFilter Unregisters the specified chat filter.
     */
    public abstract void unregisterChatFilter(ChatFilter chatFilter);
    
    /**
     * Generic demo method.
     * 
     * @param type Generic demo type.
     * @return Generic demo type.
     * @deprecated Generic demo method.
     */
    public abstract Type demoMethod(Type type);
}
