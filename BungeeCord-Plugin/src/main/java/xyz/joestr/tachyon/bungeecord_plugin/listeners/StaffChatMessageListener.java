/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.joestr.tachyon.bungeecord_plugin.listeners;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;
import xyz.joestr.tachyon.bungeecord_plugin.commands.StaffChatCommand;
import xyz.joestr.tachyon.bungeecord_plugin.utils.Configuration;

/**
 *
 * @author Joel
 */
public class StaffChatMessageListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onChat(ChatEvent event) {

        // If the message does not starts with +
        if (!event.getMessage().startsWith("+")) {
            return;
        }
        
        // If the evetn sender is not a player
        if (!(event.getSender() instanceof ProxiedPlayer)) {
            return;
        }

        // We ensured that the sender is a player
        ProxiedPlayer player = (ProxiedPlayer) event.getSender();
        
        // If the player does not have the necessary permission
        if(!player.hasPermission(Configuration.Commands.StaffChat.permission())) {
            return;
        }
        
        // Finally, send the 
        StaffChatCommand
            .sendOutToStaff(
                player.getName(),
                event.getMessage().substring(1)
            );
        
        event.setCancelled(true);
    }
}
