/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.joestr.tachyon.information_exchange_server.configurations;

import java.util.List;

/**
 *
 * @author Joel
 */
public class YamlConfiguration {
    
    private String address;
    private int port;
    private String bungeecordInstanceURL;
    private List<String> bukkitInstanceURLs;

    public YamlConfiguration() {
    }

    public String getAddress() {
        return address;
    }

    public void getAddress(String address) {
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

    public List<String> getBukkitInstanceURLs() {
        return bukkitInstanceURLs;
    }

    public void setBukkitInstanceURLs(List<String> bukkitInstanceURLs) {
        this.bukkitInstanceURLs = bukkitInstanceURLs;
    }
}
