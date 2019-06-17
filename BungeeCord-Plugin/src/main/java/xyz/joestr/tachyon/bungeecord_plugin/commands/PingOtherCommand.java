/*
 * Copyright © 2019 Joel Strasser
 *
 * No license
 */
package xyz.joestr.tachyon.bungeecord_plugin.commands;

import com.google.common.collect.ImmutableList;
import java.util.stream.Collectors;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;
import xyz.joestr.tachyon.bungeecord_plugin.utils.Configuration;

/**
 * Represents the '/list' command.
 *
 * @author Joel Strasser
 */
public class PingOtherCommand extends Command implements TabExecutor {

    public PingOtherCommand() {
        super(
            Configuration.Commands.List.command(),
            Configuration.Commands.List.permission()
        );
    }

    @Override
    public void execute(CommandSender cs, String[] strings) {

        if(strings.length != 1) {
            cs.sendMessage(
                Configuration.Messages.usage(
                    Configuration.Commands.PingOther.usage(),
                    Configuration.Commands.PingOther.blankUsage(),
                    Configuration.Commands.PingOther.blankUsage()
                )
            );
            return;
        }
        
        ProxiedPlayer target =
            ProxyServer
                .getInstance()
                .getPlayer(strings[0]);
        
        if(target == null) {
            cs.sendMessage(
                Configuration.Messages
                    .pingOtherTargetNotOnline(target.getName())
            );
            return;
        }
        
        cs.sendMessage(
            Configuration.Messages
                .pingOtherCommandOutput(target)
        );
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender cs, String[] strings) {

        // 0 or 1 argument(s) passed
        if (strings.length <= 1) {
            return ImmutableList.copyOf(
                ProxyServer.getInstance()               // Get the proxy
                    .getPlayers()                       // Get all player
                    .stream()                           // Use it a a stream
                    .map(p -> p.getName())              // Map to player names
                    .filter(pN -> {
                        return 
                            strings.length == 1 ?       // If a string was passed ...
                            pN.startsWith(strings[0]) : // ... filter the start for it
                            false                       // If not, keep the entry
                        ;
                    })
                    .collect(Collectors.toList())       // Bundle to a list
                );
        }
        
        // Return a empty list
        return ImmutableList.of();
    }
}
