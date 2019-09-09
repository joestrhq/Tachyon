/*
 * Copyright © 2019 Joel Strasser
 *
 * No license
 */
package xyz.joestr.tachyon.bungeecord_plugin.commands;

import com.google.common.collect.ImmutableList;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;
import org.eclipse.paho.client.mqttv3.MqttException;
import xyz.joestr.tachyon.api.TachyonAPI;
import xyz.joestr.tachyon.bungeecord_plugin.TachyonBungeeCordPlugin;
import xyz.joestr.tachyon.bungeecord_plugin.utils.Configuration;

/**
 * Represents the '/tpa' command.
 *
 * @author Joel Strasser
 */
public class TPACommand extends Command implements TabExecutor {

    public TPACommand() {
        super(
            Configuration.Commands.List.command(),
            Configuration.Commands.List.permission()
        );
    }

    @Override
    public void execute(CommandSender cs, String[] strings) {

        // If the sender of the command is not a player ...
        if (!(cs instanceof ProxiedPlayer)) {
            // send a message and quit.
            cs.sendMessage(Configuration.Messages.notAPlayer());
            return;
        }

        // If there are not enough or too many parameters ...
        if (strings.length != 1) {
            // ... build a message, send and quit.
            cs.sendMessage(
                Configuration.Messages.usage(
                    Configuration.Commands.List.usage(),
                    Configuration.Commands.List.blankUsage(),
                    Configuration.Commands.List.blankUsage()
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

        
        try {
            TachyonAPI.getInstance().getRequestManager().sendMessage("dd"
            );
        } catch (MqttException ex) {
            Logger.getLogger(TPACommand.class.getName()).log(Level.SEVERE, null, ex);
        }

        targetPlayer.sendMessage(
            Configuration
                .Messages
                .tpaCommandOutput(
                    sourcePlayer.getName()
                )
        );
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender cs, String[] strings) {

        return ProxyServer.getInstance().getPlayers().stream().map(p -> p.getName()).collect(Collectors.toList());
    }
}
