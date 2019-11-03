/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.joestr.tachyon.bungeecord_plugin.commands;

import com.google.common.collect.ImmutableList;
import java.util.Arrays;
import java.util.stream.Collectors;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;
import xyz.joestr.tachyon.bungeecord_plugin.configuration.StaticConfiguration;

/**
 * Represents the '/staffchat' and '/sc' command.
 * 
 * @author Joel
 */
public class StaffChatCommand extends Command implements TabExecutor {

    public StaffChatCommand() {
        super(StaticConfiguration.Commands.StaffChat.command(),
            StaticConfiguration.Commands.StaffChat.permission(),
            StaticConfiguration.Commands.StaffChat.alias()
        );
    }

    @Override
    public void execute(CommandSender cs, String[] strings) {
        
        if(strings.length < 1) {
           StaticConfiguration.Messages.Generic.usage(StaticConfiguration.Commands.StaffChat.usage(),
                StaticConfiguration.Commands.StaffChat.blankUsage(),
                StaticConfiguration.Commands.StaffChat.blankUsage()
            );
           return;
        }
        
        sendOutToStaff(
            cs.getName(),
            Arrays.asList(strings).stream().collect(
                Collectors.joining(" ", "", "")
            )
        );
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender cs, String[] strings) {
        return ImmutableList.of();
    }

    /**
     * Sends out a message to all players who have the permission to receive it.
     * 
     * @param source The sender
     * @param message The message to send
     * @deprecated The location of this method is not very good.
     */
    public static void sendOutToStaff(String source, String message) {
        ProxyServer.getInstance().getPlayers()
            .stream()
            .filter(proxiedPlayer -> proxiedPlayer.hasPermission(StaticConfiguration.Commands.StaffChat.permission()
                )
            )
            .forEach(proxiedPlayer -> proxiedPlayer.sendMessage(StaticConfiguration.Messages.transformMessageForStaffChat(
                        source,
                        message
                    )
                )
            );
        
        ProxyServer.getInstance()
            .getConsole()
            .sendMessage(StaticConfiguration.Messages.transformMessageForStaffChat(
                    source,
                    message
                )
            );
    }
}
