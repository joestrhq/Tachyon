/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.or.joestr.tachyon.bungeecord.chatfilters;

import java.util.Arrays;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import at.or.joestr.tachyon.api.classes.ChatFilter;

/**
 * Strips out color codes from a message.
 * 
 * @author Joel
 */
public class ColorCodeFilter implements ChatFilter {

    @Override
    public void filter(BaseComponent[] baseComponents) {
       Arrays.asList(baseComponents).stream()
           .forEach(
               bC -> bC.retain(ComponentBuilder.FormatRetention.FORMATTING)
           );
    }

    @Override
    public String permission() {
        return "colorcode";
    }
    
}
