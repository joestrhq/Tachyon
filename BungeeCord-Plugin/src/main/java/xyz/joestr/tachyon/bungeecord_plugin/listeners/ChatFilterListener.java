/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.joestr.tachyon.bungeecord_plugin.listeners;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;
import xyz.joestr.tachyon.api.classes.ChatFilter;
import xyz.joestr.tachyon.bungeecord_plugin.TachyonBungeeCordPlugin;
import xyz.joestr.tachyon.bungeecord_plugin.configuration.StaticConfiguration;

/**
 * Applies filters to sent chat messages
 * 
 * @author Joel
 */
public class ChatFilterListener implements Listener {
    
    // Highest priority in order to catch all messages
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(ChatEvent event) {

        // Check if the command sender is a player
        if (!(event.getSender() instanceof ProxiedPlayer)) {
            return;
        }
        
        // Get the player here
        ProxiedPlayer proxiedPlayer = (ProxiedPlayer) event.getSender();

        // Builds up new components here
        ComponentBuilder componentBuilder = new ComponentBuilder("");
        
        // Basic splitting ans merging of new components
        for(String s : event.getMessage().split(" ")) {
            
            componentBuilder.append(
                TextComponent.fromLegacyText(s, ChatColor.WHITE)
            );
        }
        
        // This is the full message based on components
        BaseComponent[] baseComponents = componentBuilder.create();
        
        // Run through the chat filters
        for(ChatFilter chatFilter : TachyonBungeeCordPlugin.chatFilters) {
            
            if(!proxiedPlayer.hasPermission(StaticConfiguration.LoosePermission.bypassChatFilter())) {
                break; // Player can bypass all filters -> break here
            }
            
            if(!proxiedPlayer.hasPermission(StaticConfiguration.LoosePermission.bypassChatFilter(chatFilter.permission()))) {
                continue; // Player can bypass this specific filter -> continue with next filter
            }
            
            // Apply the filter here
            chatFilter.filter(baseComponents);
        }
        
        // Alter filteration, broadcast the whole message
        ProxyServer.getInstance().broadcast(StaticConfiguration.Messages.transformMessageForGlobalChat(
                proxiedPlayer.getName(),
                baseComponents
            )
        );
        
        event.setCancelled(true);
    }
}
