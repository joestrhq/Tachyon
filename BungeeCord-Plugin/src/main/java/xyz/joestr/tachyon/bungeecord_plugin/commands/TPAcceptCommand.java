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
 * Represents the '/tpaccept' command.
 *
 * @author Joel Strasser
 */
public class TPAcceptCommand extends Command implements TabExecutor {

    public TPAcceptCommand() {
        super(
            Configuration.Commands.List.command(),
            Configuration.Commands.List.permission()
        );
    }

    @Override
    public void execute(CommandSender cs, String[] strings) {

        cs.sendMessage(Configuration.Messages.listCommandOutput(ProxyServer.getInstance().getPlayers()));
        
        ProxiedPlayer player = (ProxiedPlayer) cs;
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender cs, String[] strings) {

        return ImmutableList.of();
    }
}
