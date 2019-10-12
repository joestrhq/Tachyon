/*
 * Copyright © 2019 Joel Strasser
 *
 * No license
 */
package xyz.joestr.tachyon.bungeecord_plugin.commands;

import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;
import xyz.joestr.tachyon.api.TachyonAPI;
import xyz.joestr.tachyon.bungeecord_plugin.managers.TPAManager;
import xyz.joestr.tachyon.bungeecord_plugin.utils.StaticConfiguration;
import xyz.joestr.tachyon.bungeecord_plugin.utils.TPAType;

/**
 * Represents the '/tpa' command.
 *
 * @author Joel Strasser
 */
public class TPACommand extends Command implements TabExecutor {

    public TPACommand() {
        super(StaticConfiguration.Commands.List.command(),
            StaticConfiguration.Commands.List.permission()
        );
    }

    @Override
    public void execute(CommandSender cs, String[] strings) {

        if(cs == null) {
            return;
        }
        
        // If the sender of the command is not a player ...
        if (!(cs instanceof ProxiedPlayer)) {
            // send a message and quit.
            cs.sendMessage(StaticConfiguration.Messages.Generic.notAPlayer());
            return;
        }
        
        // If there are not enough or too many parameters ...
        if (strings.length != 1) {
            // ... build a message, send and quit.
            cs.sendMessage(
                StaticConfiguration.Messages.Generic.usage(
                    StaticConfiguration.Commands.List.usage(),
                    StaticConfiguration.Commands.List.blankUsage(),
                    StaticConfiguration.Commands.List.blankUsage()
                )
            );
            return;
        }

        // We ensured that the command sender is a player
        ProxiedPlayer sourcePlayer = (ProxiedPlayer) cs;
        
        // Get the target player from the first argument
        ProxiedPlayer targetPlayer =
            ProxyServer
                .getInstance()
                .getPlayer(strings[0]);
        
        // If there is no such player
        if (targetPlayer == null) {
            // TODO: send a message
            return;
        }

        TPAManager tpaManager = TPAManager.getInstance();
        
        if(tpaManager.hasOutstandingRequest(sourcePlayer.getUniqueId())) {
            sourcePlayer.sendMessage(
                StaticConfiguration.Messages.Commands.TPA.sourceHasPendingRequest()
            );
            return;
        }
        
        if(tpaManager.hasOutstandingRequest(targetPlayer.getUniqueId())) {
            sourcePlayer.sendMessage(
                StaticConfiguration.Messages.Commands.TPA.targetHasPendingRequest(targetPlayer.getName())
            );
            return;
        }
        
        tpaManager.createRequest(
            sourcePlayer.getUniqueId(),
            targetPlayer.getUniqueId(),
            TPAType.TO,
            LocalDateTime.now().plusSeconds(15)
        );
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender cs, String[] strings) {

        return ProxyServer.getInstance().getPlayers().stream().map(p -> p.getName()).collect(Collectors.toList());
    }
}
