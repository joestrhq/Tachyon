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
import xyz.joestr.tachyon.bungeecord_plugin.utils.Configuration;

/**
 * Represents the '/staffchat' and '/sc' command.
 * 
 * @author Joel
 */
public class StaffChatCommand extends Command implements TabExecutor {

    public StaffChatCommand() {
        super(
            Configuration.Commands.StaffChat.command(),
            Configuration.Commands.StaffChat.permission(),
            Configuration.Commands.StaffChat.alias()
        );
    }

    @Override
    public void execute(CommandSender cs, String[] strings) {
        
        if(strings.length == 0) {
           Configuration.Messages.usage(
                Configuration.Commands.StaffChat.usage(),
                Configuration.Commands.StaffChat.blankUsage(),
                Configuration.Commands.StaffChat.blankUsage()
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
            .filter(
                proxiedPlayer -> proxiedPlayer.hasPermission(
                    Configuration.Commands.StaffChat.permission()
                )
            )
            .forEach(
                proxiedPlayer -> proxiedPlayer.sendMessage(
                    Configuration.Messages.transformMessageForStaffChat(
                        source,
                        message
                    )
                )
            );
        
        ProxyServer.getInstance()
            .getConsole()
            .sendMessage(
                Configuration.Messages.transformMessageForStaffChat(
                    source,
                    message
                )
            );
    }
}
