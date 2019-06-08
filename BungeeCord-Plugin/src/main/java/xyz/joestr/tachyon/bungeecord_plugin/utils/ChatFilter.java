/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.joestr.tachyon.bungeecord_plugin.utils;

import net.md_5.bungee.api.chat.BaseComponent;

/**
 * Is an interface for more chat filters.
 * 
 * @author Joel
 */
public interface ChatFilter {
    
    public void filter(BaseComponent[] baseComponents);
    
    public String permission();
    
}
