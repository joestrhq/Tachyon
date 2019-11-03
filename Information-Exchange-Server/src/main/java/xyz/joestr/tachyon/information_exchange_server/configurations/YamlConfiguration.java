/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.joestr.tachyon.information_exchange_server.configurations;

import java.util.List;
import java.util.Map;

/**
 * Represents the configuration saved in a YAML file.<br>
 * <br>
 * The file contains <b>must</b> contain following entries:
 * <ul>
 * <li>The <b>listen address</b></li>
 * <li>The <b>listen Port</b></li>
 * <li>The <b>url</b> for the BungeeCord instance</li>
 * <li>The <b>urls</b> for the Bukkit instance</li>
 * </ul>
 * 
 * The listen address <b>must</b> be provided in following format:
 * {@code address: "127.0.0.1"}<br>
 * <br>
 * The listen port <b>must</b> be provided in following format:
 * {@code port: 8080}<br>
 * <br>
 * The URL for the BungeeCord instance <b>must</b> be provided in following
 * format: {@code bungeecordinstanceurl: "http://127.0.0.1:59801"}<br>
 * <br>
 * The URLs for the Bukkit instance <b>must</b> be provided in following format:
 * {@code bukkitinstanceurls: ["http://127.0.0.1:59802", "http://127.0.0.1:59803"]}
 * 
 * @author Joel
 */
public class YamlConfiguration {
    
    private String address;
    private int port;
    private String bungeecordInstanceURL;
    private Map<String, String> bukkitInstanceURLs;

    public YamlConfiguration() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getBungeecordInstanceURL() {
        return bungeecordInstanceURL;
    }

    public void setBungeecordInstanceURL(String bungeecordInstanceURL) {
        this.bungeecordInstanceURL = bungeecordInstanceURL;
    }

    public Map<String, String> getBukkitInstanceURLs() {
        return bukkitInstanceURLs;
    }

    public void setBukkitInstanceURLs(Map<String, String> bukkitInstanceURLs) {
        this.bukkitInstanceURLs = bukkitInstanceURLs;
    }
}
