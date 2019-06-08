/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.joestr.tachyon.api;

import java.lang.reflect.Type;


/**
 *
 * @author Joel
 * @version ${project.version}
 */
public abstract class TachyonAPI {
    
    /**
     * Singleton access for the API to prevent multiple instances of it.
     * @return The {@link xyz.joestr.tachyon.api.TachyonAPI API} itself.
     */
    public abstract TachyonAPI getInstance();
    
    /**
     * Get the amount of players on a server.
     * 
     * @param serverName The name of the server you want to query.
     * @return The amount of players on a server.
     */
    public abstract int getOnlineCountForServer(String serverName);
    
    /**
     * Generic demo method.
     * 
     * @param type Generic demo type.
     * @return Generic demo type.
     * @deprecated Generic demo method.
     */
    public abstract Type demoMethod(Type type);
}
