/*
 * Copyright © 2019 Joel Strasser
 *
 * No license
 */
package xyz.joestr.tachyon.bungeecord_plugin.commands;

import com.google.common.collect.ImmutableList;
import java.util.Iterator;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.TranslatableComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

/**
 * Represents the '/tlist' command.
 *
 * @author Joel Strasser
 */
public class CommandTGList extends Command implements TabExecutor {

    public CommandTGList(String name, String permission, String... aliases) {
        super(name, permission, aliases);
    }
    
    @Override
    public void execute(CommandSender cs, String[] strings) {
        
        TranslatableComponent listMessage = new TranslatableComponent("commands.list.players");
        listMessage.addWith(String.valueOf(ProxyServer.getInstance().getOnlineCount()));
        listMessage.addWith(String.valueOf(ProxyServer.getInstance().getConfig().getPlayerLimit()));

                TextComponent listMessageBody = new TextComponent();

                Iterator<ProxiedPlayer> it = ProxyServer.getInstance().getPlayers().iterator();
                
                while (it.hasNext()) {

                    ProxiedPlayer proxiedPlayer = it.next();

                    TextComponent playerEntry = new TextComponent(proxiedPlayer.getDisplayName());
                    playerEntry.setClickEvent(
                        new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/tell " + proxiedPlayer.getName() + " ")
                    );

                    TextComponent coma = new TextComponent(", ");
                    coma.setColor(ChatColor.GRAY);

                    listMessageBody.addExtra(playerEntry);

                    if (it.hasNext()) {
                        listMessageBody.addExtra(coma);
                    }
                }

                listMessage.addWith(listMessageBody);
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender cs, String[] strings) {

        // Does not need a tab completion, so empty list
        return ImmutableList.of();
    }
}
