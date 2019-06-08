/*
 * Copyright © 2019 Joel Strasser
 *
 * No license
 */
package xyz.joestr.tachyon.bungeecord_plugin.commands;

import com.google.common.collect.ImmutableList;
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
public class ListCommand extends Command implements TabExecutor {

    public ListCommand() {
        super(
            Configuration.Commands.List.command(),
            Configuration.Commands.List.permission()
        );
    }

    @Override
    public void execute(CommandSender cs, String[] strings) {

        cs.sendMessage(
            Configuration
                .Messages
                .listCommandOutput(
                ProxyServer.getInstance().getPlayers()
            )
        );
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender cs, String[] strings) {

        // Does not need a tab completion, so empty list
        return ImmutableList.of();
    }
}
