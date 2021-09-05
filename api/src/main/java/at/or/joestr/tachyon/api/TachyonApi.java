/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.or.joestr.tachyon.api;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.UUID;
import java.util.WeakHashMap;
import at.or.joestr.tachyon.api.classes.ChatFilter;
import at.or.joestr.tachyon.api.rest.RestPlayerSettings;

/**
 *
 * @author Joel
 * @version ${project.version}
 */
public abstract class TachyonApi {
    
    private static TachyonApi instance = null;
    private static URL informationExchangeServerURL = null;
    
    private WeakHashMap<UUID, RestPlayerSettings> playerSettingsCache = new WeakHashMap<>();
    private WeakHashMap<UUID, String> playerSettingsCacheTags = new WeakHashMap<>();
    
    /**
     * Sets the instance of the API. This method may only be called once per an
     * application.
     *
     * @param instance A class which extends the {@link at.or.joestr.tachyon.api.TachyonApi}
     */
    public static void setInstance(TachyonApi instance) {
        
        // If the argument is null
        if(instance == null) {
            throw new IllegalArgumentException("Argument 'instance' cannot be null!");
        }
        
        // If the instance is not null anymore
        if(TachyonApi.instance != null) {
            throw new IllegalStateException("'instance' already set!");
        }
        
        // Set the instance
        TachyonApi.instance = instance;
    }
    
    /**
     * Set the Information Exchange Server URL.
     * @param informationExchangeServerURL The URL of the Information Exchange Server.
     */
    public static void setInformationExchangeServerURL(URL informationExchangeServerURL) {
        
        if(informationExchangeServerURL == null) {
            throw new IllegalArgumentException("Argument 'informationExchangeServerHost' cannot be null!");
        }
        
        if(TachyonApi.informationExchangeServerURL != null) {
             throw new IllegalStateException("'informationExchangeServerHost' already set!");
        }
        
        TachyonApi.informationExchangeServerURL = informationExchangeServerURL;
    }
    
    /**
     * Singleton access for the API to prevent multiple instances of it.
     * @return The {@link at.or.joestr.tachyon.api.TachyonApi API} itself.
     */
    public static TachyonApi getInstance() {
        
        // Return the instance
        return TachyonApi.instance;
    }
    
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
     * Get the settings for a player.
     * 
     * @param uuid The UUID of the player.
     * @return The settings for the instance
     * @throws MalformedURLException If the provided URL is wrong.
     * @throws ProtocolException If an error occours in the protocol.
     * @throws IOException  If a general I/O error ocours.
     */
    public RestPlayerSettings getPlayerSettings(UUID uuid) throws MalformedURLException, ProtocolException, IOException {
        
        URL obj;
        HttpURLConnection con;
        
        if(this.playerSettingsCache.containsKey(uuid) && this.playerSettingsCacheTags.containsKey(uuid)) {
            obj = new URL(
            informationExchangeServerURL.toString()
                + "/players/"
                + uuid.toString()
                + "/settings"
            );
            
            con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("HEAD");
            con.setRequestProperty("User-Agent", "TachyonAPI/1.0");
            con.setRequestProperty("Authentication", "Bearer ");
            con.setRequestProperty(
                "If-None-Match",
                this.playerSettingsCacheTags.get(uuid)
            );
            
            if(con.getResponseCode() == 304) {
                return this.playerSettingsCache.get(uuid);
            }
        }
        
        obj = new URL(
            informationExchangeServerURL.toString()
                + "/players/"
                + uuid.toString()
                + "/settings"
        );
		con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", "TachyonAPI/1.0");
        con.setRequestProperty("Authentication", "Bearer ");

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream())
        );
		String inputLine;
		StringBuilder response = new StringBuilder();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
        
		in.close();
        
        return new Gson().fromJson(response.toString(), RestPlayerSettings.class);
    }
    
    /**
     * Get a specific setting for a player or {@code null} if there is no such
     * setting.
     * 
     * This method calls {@link #getPlayerSettings(java.util.UUID)} and returns
     * and looks for the specified setting.
     * 
     * @param uuid The UUID of the player.
     * @param setting The specific setting
     * @return The settings for the instance
     * @throws MalformedURLException If the provided URL is woring.
     * @throws ProtocolException If an error occours in the protocol.
     * @throws IOException  If a general I/O error ocours.
     */
    public String getPlayerSetting(UUID uuid, String setting) throws MalformedURLException, ProtocolException, IOException {
        return instance.getPlayerSettings(uuid).getSetting(setting);
    }
    
    /**
     * Generic demo method.
     * 
     * @param type Generic demo type.
     * @return Generic demo type.
     * @deprecated Generic demo method.
     */
    public abstract Type demoMethod(Type type);
}
