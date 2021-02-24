/*
 * Copyright © 2019 Joel Strasser
 *
 * No license
 */
package at.or.joestr.tachyon.bungeecord_plugin.commands;

import com.google.common.collect.ImmutableList;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

/**
 * Represents the '/gping' command.
 *
 * @author Joel Strasser
 */
public class CommandTGPing extends Command implements TabExecutor {

    public CommandTGPing() {
        super("tg-ping", "tachyon.command.tg-ping");
    }

    @Override
    public void execute(CommandSender cs, String[] strings) {
        cs.sendMessage("");
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender cs, String[] strings) {
        return ImmutableList.of();
    }
}
