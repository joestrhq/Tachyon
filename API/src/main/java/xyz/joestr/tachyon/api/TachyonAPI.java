/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.joestr.tachyon.api;

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
import xyz.joestr.tachyon.api.chatfilter.ChatFilter;
import xyz.joestr.tachyon.api.settings.PlayerSettings;
import xyz.joestr.tachyon.api.settings.InstanceSettings;


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
            throw new IllegalArgumentException("Argument 'instance' cannot be null!");
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
    public static TachyonAPI getInstance() {
        
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
     * Get the settings for an instance.
     * 
     * @param instanceName The name of the instance
     * @return The settings for the instance
     * @throws MalformedURLException If the provided URL is woring.
     * @throws ProtocolException If an error occours in the protocol.
     * @throws IOException  If a general I/O error ocours.
     */
    public InstanceSettings getInstanceSettings(String instanceName) throws MalformedURLException, ProtocolException, IOException {
        
        URL obj = new URL("http://www.google.com/search?q=mkyong");
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		con.setRequestMethod("GET");

		con.setRequestProperty("User-Agent", "TachyonAPI/1.0");

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream())
        );
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
        
		in.close();

		//print result
		System.out.println(response.toString());
        
        return new Gson().fromJson(response.toString(), InstanceSettings.class);
    }
    
     /**
     * Get the settings for a players.
     * 
     * @param uuid The UUID of the player.
     * @return The settings for the instance
     * @throws MalformedURLException If the provided URL is woring.
     * @throws ProtocolException If an error occours in the protocol.
     * @throws IOException  If a general I/O error ocours.
     */
    public PlayerSettings getPlayerSettings(UUID uuid) throws MalformedURLException, ProtocolException, IOException {
        
        URL obj = new URL("/player/" + uuid.toString());
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		con.setRequestMethod("GET");

		con.setRequestProperty("User-Agent", "TachyonAPI/1.0");

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream())
        );
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
        
		in.close();

		//print result
		System.out.println(response.toString());
        
        return new Gson().fromJson(response.toString(), PlayerSettings.class);
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
