/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.joestr.tachyon.bungeecord_plugin.chatfilters;

import java.util.Arrays;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import xyz.joestr.tachyon.bungeecord_plugin.utils.ChatFilter;

/**
 * Is the chat filter for player name annotations. (For example: @joestr) The
 * name will get higlighted in green.
 * 
 * @author Joel
 */
public class AnnotatedPlayerChatFilter implements ChatFilter {
    
    @Override
    public void filter(BaseComponent[] baseComponents) {
        
        Arrays.asList(baseComponents).stream()
            .filter(bC -> bC.toPlainText().startsWith("@"))
            .filter(bC -> {
                return ProxyServer.getInstance()
                    .getPlayer(bC.toPlainText().substring(1)) != null;
            })
            .forEach(bC -> bC.setColor(ChatColor.GREEN));
    }

    @Override
    public String permission() {
        throw new UnsupportedOperationException("annotatedplayer");
    }
}