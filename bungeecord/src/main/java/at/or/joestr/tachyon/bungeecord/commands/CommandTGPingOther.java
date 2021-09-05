/*
 * Copyright © 2019 Joel Strasser
 *
 * No license
 */
package at.or.joestr.tachyon.bungeecord.commands;

import com.google.common.collect.ImmutableList;
import java.util.stream.Collectors;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

/**
 * Represents the '/list' command.
 *
 * @author Joel Strasser
 */
public class CommandTGPingOther extends Command implements TabExecutor {

  public CommandTGPingOther() {
    super("tg-ping-other", "tachyon.command.tg-ping-other");
  }

  @Override
  public void execute(CommandSender cs, String[] strings) {

    if (strings.length != 1) {
      return;
    }

    ProxiedPlayer target
      = ProxyServer
        .getInstance()
        .getPlayer(strings[0]);

    if (target == null) {
      return;
    }

    cs.sendMessage("");

  }

  @Override
  public Iterable<String> onTabComplete(CommandSender cs, String[] strings) {

    // 0 or 1 argument(s) passed
    if (strings.length <= 1) {
      return ImmutableList.copyOf(
        ProxyServer.getInstance() // Get the proxy
          .getPlayers() // Get all player
          .stream() // Use it a a stream
          .map(p -> p.getName()) // Map to player names
          .filter(pN -> {
            return strings.length == 1
              ? // If a string was passed ...
              pN.startsWith(strings[0])
              : // ... filter the start for it
              false // If not, keep the entry
              ;
          })
          .collect(Collectors.toList()) // Bundle to a list
      );
    }

    // Return a empty list
    return ImmutableList.of();
  }
}
