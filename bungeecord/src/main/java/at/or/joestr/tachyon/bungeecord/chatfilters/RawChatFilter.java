/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.or.joestr.tachyon.bungeecord.chatfilters;

import net.md_5.bungee.api.chat.BaseComponent;
import at.or.joestr.tachyon.api.classes.ChatFilter;

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
        return "raw";
    }
}
