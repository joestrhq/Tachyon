/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.joestr.tachyon.information_exchange_server.configurations;

import java.util.Map;

/**
 * Represents the configuration saved in a YAML file.<br>
 * <br>
 * The file contains <b>must</b> contain following entries:
 * <ul>
 * <li>The <b>listen uri</b></li>
 * <li>The <b>url</b> for the BungeeCord instance</li>
 * <li>The <b>urls</b> for the Bukkit instance</li>
 * </ul>
 * 
 * <br>
 * The listen uri <b>must</b> be provided in following format:
 * {@code listenuri: 'http://127.0.0.1:58900'}<br>
 * <br>
 * The URL for the BungeeCord instance <b>must</b> be provided in following
 * format: {@code bungeecordinstanceurl: 'http://127.0.0.1:59801'}<br>
 * <br>
 * The URLs for the Bukkit instance <b>must</b> be provided in following format:
 * {@code bukkitinstanceurls:}<br />
 * &nbsp;&nbsp;{@code server_a: 'http://127.0.0.1:59802'}<br />
 * &nbsp;&nbsp;{@code server_b: 'http://127.0.0.1:59803'}
 * 
 * @author Joel
 */
public class YamlConfiguration {
    
    private String listenuri;
    private String bungeecordInstanceURL;
    private Map<String, String> bukkitInstanceURLs;

    public YamlConfiguration() {
    }

    public String getListenuri() {
        return listenuri;
    }

    public void setListenuri(String listenuri) {
        this.listenuri = listenuri;
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
