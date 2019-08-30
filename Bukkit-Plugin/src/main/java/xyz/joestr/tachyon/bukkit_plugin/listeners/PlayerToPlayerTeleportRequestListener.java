/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package xyz.joestr.tachyon.bukkit_plugin.listeners;

import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import xyz.joestr.tachyon.api.request.Request;
import xyz.joestr.tachyon.api.request.RequestEvent;
import xyz.joestr.tachyon.api.requests.PlayerToPlayerTeleportRequest;

/**
 *
 * @author Joel
 */
public class PlayerToPlayerTeleportRequestListener implements RequestEvent<PlayerToPlayerTeleportRequest> {

    @Override
    public void onRequestReceived(Request request) {
        
        if(!(request instanceof PlayerToPlayerTeleportRequest)) {
            return;
        }
        
        PlayerToPlayerTeleportRequest customRequest = (PlayerToPlayerTeleportRequest) request;
        
        if(customRequest.getSender() == null || customRequest.getTarget() == null) {
            return;
        }
        
        Player sender = Bukkit.getServer().getPlayer(customRequest.getSender());
        if(sender == null) return;
        if(!sender.isOnline()) return;
        
        Player target = Bukkit.getServer().getPlayer(customRequest.getTarget());
        if(target == null) return;
        if(!target.isOnline()) return;
        
        sender.teleport(target);
    }
}
