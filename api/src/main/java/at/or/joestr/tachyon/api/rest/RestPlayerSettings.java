/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.or.joestr.tachyon.api.rest;

import com.google.common.collect.ImmutableMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author Joel
 */
public class RestPlayerSettings {
    
    private final Map<String, String> settings;
    
    public RestPlayerSettings() {
        this.settings = new HashMap<>();
    }
    
    /**
     * Get a copy of the current settings.
     * @return An immutable copy of the settings
     */
    public Map<String, String> getSettings() {
        return ImmutableMap.copyOf(settings);
    }
    
    /**
     * Set multiple settings at once.
     * 
     * The settings are not going to be replaced but inserted or updated one for
     * one.
     * 
     * This method has synchronized access.
     * 
     * @param settings The settings. 
     */
    synchronized public void setSettings(RestPlayerSettings settings) {
        
        RestPlayerSettings ps = this;
        
        settings.getSettings().forEach((key, value) -> {
            ps.setSetting(key, value);
        });
    }
    
    /**
     * Get a setting.
     * 
     * @param key The setting to get.
     * @return The value of the corresponding setting or {@code null}.
     */
    public String getSetting(String key) {
        return this.settings.get(key);
    }
    
    /**
     * Set a setting.
     * 
     * @param key The setting.
     * @param value The value of the setting.
     */
    synchronized public void setSetting(String key, String value) {
        this.settings.put(key, value);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.settings);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RestPlayerSettings other = (RestPlayerSettings) obj;
        if (!Objects.equals(this.settings, other.settings)) {
            return false;
        }
        return true;
    }
}
