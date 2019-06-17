/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.joestr.tachyon.bungeecord_plugin.chatfilters;

import net.md_5.bungee.api.chat.BaseComponent;
import xyz.joestr.tachyon.api.chatfilter.ChatFilter;

/**
 * This is a raw chat filter, which does not modify the array of
 * {@link BaseComponent}s at all.
 * 
 * @author Joel
 */
public class RawChatFilter implements ChatFilter {
    
    @Override
    public void filter(BaseComponent[] baseComponents) {
        
        // Nothing to do
    }

    @Override
    public String permission() {
        throw new UnsupportedOperationException("raw");
    }
}
