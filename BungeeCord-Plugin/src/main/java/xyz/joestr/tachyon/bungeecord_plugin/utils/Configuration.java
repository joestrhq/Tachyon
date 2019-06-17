/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.joestr.tachyon.bungeecord_plugin.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import me.lucko.luckperms.LuckPerms;
import me.lucko.luckperms.api.LuckPermsApi;
import me.lucko.luckperms.api.User;
import me.lucko.luckperms.api.caching.CachedData;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.TranslatableComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 * Represents a single point of configurability for the plugin.
 *
 * @author Joel
 */
public class Configuration {

    public static class Commands {

        public static class List {

            public static String command() {
                return "list";
            }

            public static String permission() {
                return "tbungee.commands.list";
            }

            public static String alias() {
                return "";
            }

            public static String blankUsage() {

                return "/" + command();
            }

            public static String usage() {

                return blankUsage() + " <Spielername>";
            }
        }
        
        public static class Ping {
            
            public static String command() {
                return "ping";
            }

            public static String permission() {
                return "tbungee.commands.ping";
            }

            public static String alias() {
                return "";
            }

            public static String blankUsage() {

                return "/" + command();
            }

            public static String usage() {

                return blankUsage() + "";
            }
        }
        
        public static class PingOther {
            
            public static String command() {
                return "ping-other";
            }

            public static String permission() {
                return "tbungee.commands.ping-other";
            }

            public static String alias() {
                return "";
            }

            public static String blankUsage() {

                return "/" + command();
            }

            public static String usage() {

                return blankUsage() + " <Spielername>";
            }
        }

        public static class StaffChat {

            public static String command() {
                return "staffchat";
            }

            public static String permission() {
                return "tbungee.commands.staffchat";
            }

            public static String alias() {
                return "sc";
            }

            public static String blankUsage() {

                return "/" + command();
            }

            public static String usage() {

                return blankUsage() + " <Nachricht ...>";
            }
        }
    }

    public static class Messages {

        public static BaseComponent[] notAPlayer() {

            return new ComponentBuilder("Du bist kein Spieler!")
                .color(ChatColor.RED)
                .create();
        }

        public static BaseComponent[] usage(String usage, String hover, String click) {

            return new ComponentBuilder("Benutze: ")
                .color(ChatColor.RED)
                .append(usage)
                .color(ChatColor.GRAY)
                .event(
                    new HoverEvent(
                        HoverEvent.Action.SHOW_TEXT,
                        new ComponentBuilder(hover).color(ChatColor.GRAY).create()
                    )
                )
                .event(
                    new ClickEvent(
                        ClickEvent.Action.SUGGEST_COMMAND,
                        click
                    )
                )
                .create();
        }
        
        public static BaseComponent[] transformMessageForGlobalChat(String source, BaseComponent[] message) {
            
            LuckPermsApi api = LuckPerms.getApi();
            User u = api.getUser(source);
            CachedData cD = u.getCachedData();
            SortedMap<Integer, String> prefixes
                = cD.getMetaData(
                    api.getContextManager().lookupApplicableContexts(u).orElse(api.getContextManager().getStaticContexts())
                ).getPrefixes();
            SortedMap<Integer, String> suffixes
                = cD.getMetaData(
                    api.getContextManager().lookupApplicableContexts(u).orElse(api.getContextManager().getStaticContexts())
                ).getSuffixes();
            
            ComponentBuilder result = new ComponentBuilder("")
                .append(
                    TextComponent.fromLegacyText(
                        ChatColor.translateAlternateColorCodes(
                            '&',
                            prefixes.values().stream().collect(Collectors.joining(""))
                        )
                    )
                )
                .append(source)
                .append(
                    TextComponent.fromLegacyText(
                        ChatColor.translateAlternateColorCodes(
                            '&',
                            suffixes.values().stream().collect(Collectors.joining(""))
                        )
                    )
                )
                .append(": ")
                .color(ChatColor.WHITE);
            
            for(int i = 0; i < message.length; i++) {
                result.append(message[i]);
                if (i < message.length -1) {
                    result.append(" ");
                }
            }
            
            return result.create();
        }

        public static BaseComponent[] transformMessageForStaffChat(String source, String message) {

            return new ComponentBuilder("[")
                .color(ChatColor.GRAY)
                .append("+")
                .color(ChatColor.DARK_PURPLE)
                .append("] ")
                .color(ChatColor.GRAY)
                .append(source)
                .color(ChatColor.DARK_PURPLE)
                .append(": ")
                .color(ChatColor.GRAY)
                .append(message)
                .color(ChatColor.DARK_PURPLE)
                .create();
        }

        public static BaseComponent listCommandOutput(Collection<ProxiedPlayer> players) {

            TranslatableComponent listMessage = new TranslatableComponent("commands.list.players");
            listMessage.addWith(String.valueOf(ProxyServer.getInstance().getOnlineCount()));
            listMessage.addWith(String.valueOf(ProxyServer.getInstance().getConfig().getPlayerLimit()));

            TextComponent listMessageBody = new TextComponent();

            Iterator<ProxiedPlayer> it = players.iterator();

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

            return listMessage;
        }
        
        public static BaseComponent[] pingCommandOutput(ProxiedPlayer player) {
            
            return new ComponentBuilder("Dein Ping beträgt ")
                .color(ChatColor.AQUA)
                .append(String.valueOf(player.getPing()) + " ms")
                .color(ChatColor.GRAY)
                .append(".")
                .color(ChatColor.AQUA)
                .create();
        }
        
        public static BaseComponent[] pingOtherCommandOutput(ProxiedPlayer player) {
            
            return new ComponentBuilder("Der Ping von ")
                .color(ChatColor.AQUA)
                .append(player.getName())
                .color(ChatColor.GRAY)
                .append(" beträgt ")
                .color(ChatColor.AQUA)
                .append(String.valueOf(player.getPing()) + " ms")
                .color(ChatColor.GRAY)
                .append(".")
                .color(ChatColor.AQUA)
                .create();
        }
        
        public static BaseComponent[] pingOtherTargetNotOnline(String target) {
            
            return new ComponentBuilder(target)
                .color(ChatColor.GRAY)
                .append(" ist nicht online.")
                .color(ChatColor.RED)
                .create();
        }

        @Deprecated
        public static BaseComponent tpaCommandOutput(ProxiedPlayer requester, ProxiedPlayer target) {

            TextComponent result = new TextComponent();

            TextComponent requesterText = new TextComponent(requester.getDisplayName());
            result.addExtra(requesterText);

            TextComponent arrow = new TextComponent(" -> ");
            arrow.setColor(ChatColor.GRAY);
            result.addExtra(arrow);

            TextComponent targetText = new TextComponent(target.getDisplayName());
            result.addExtra(targetText);

            TextComponent splitter = new TextComponent(" | ");
            splitter.setColor(ChatColor.GRAY);
            result.addExtra(splitter);

            TranslatableComponent accept = new TranslatableComponent("gui.yes");
            accept.setColor(ChatColor.GREEN);

            BaseComponent[] acceptHoverText = new BaseComponent[]{new TranslatableComponent("chat.coordinates.tooltip")};

            accept.setHoverEvent(
                new HoverEvent(
                    HoverEvent.Action.SHOW_TEXT,
                    acceptHoverText
                )
            );
            result.addExtra(accept);

            TextComponent minus = new TextComponent(" - ");
            minus.setColor(ChatColor.GRAY);
            result.addExtra(minus);

            TranslatableComponent deny = new TranslatableComponent("gui.no");
            deny.setColor(ChatColor.RED);
            result.addExtra(deny);

            return result;
        }

        public static BaseComponent[] tpaCommandOutput(String source) {

            return new ComponentBuilder("")
                .append(TextComponent.fromLegacyText(source))
                .append(" möchte sich zu dir teleportieren.")
                .color(ChatColor.BLUE)
                .append("\n")
                .append("Akzeptieren").color(ChatColor.RED)
                .event(
                    new HoverEvent(
                        HoverEvent.Action.SHOW_TEXT,
                        new ComponentBuilder("/tpaccept").color(ChatColor.GRAY).create()
                    )
                )
                .append(" | ")
                .color(ChatColor.GRAY)
                .append("Ablehnen")
                .event(
                    new HoverEvent(
                        HoverEvent.Action.SHOW_TEXT,
                        new ComponentBuilder("/tpdeny").color(ChatColor.GRAY).create()
                    )
                )
                .create();
        }

        public static BaseComponent[] tpahereCommandOutput(String source) {

            return new ComponentBuilder("Möchtest du dich zu ")
                .color(ChatColor.BLUE)
                .append(TextComponent.fromLegacyText(source))
                .append("teleportieren?")
                .color(ChatColor.BLUE)
                .append("\n")
                .append("Akzeptieren").color(ChatColor.RED)
                .event(
                    new HoverEvent(
                        HoverEvent.Action.SHOW_TEXT,
                        new ComponentBuilder("/tpaccept").color(ChatColor.GRAY).create()
                    )
                )
                .append(" | ")
                .color(ChatColor.GRAY)
                .append("Ablehnen")
                .event(
                    new HoverEvent(
                        HoverEvent.Action.SHOW_TEXT,
                        new ComponentBuilder("/tpdeny").color(ChatColor.GRAY).create()
                    )
                )
                .create();
        }

        public static BaseComponent[] tpa_tpahereCommandHasOpenRequest() {

            return new ComponentBuilder("Du hast schon eine TP-Anfrage gestellt.")
                .color(ChatColor.RED)
                .create();
        }

        public static BaseComponent[] tpacceptCommandSuccess(String source) {

            return new ComponentBuilder(source)
                .append("Du wurdest zu ")
                .color(ChatColor.GREEN)
                .append(TextComponent.fromLegacyText(source))
                .append(" teleportiert.")
                .create();
        }

        public static BaseComponent[] tpacceptCommandNoRequest() {

            return new ComponentBuilder("Du hast keine TP-Anfrage erhalten.")
                .color(ChatColor.RED)
                .create();
        }

        public static BaseComponent[] tpacceptCommandValidityExpired() {

            return new ComponentBuilder("Die Zeit ist abgelaufen.")
                .color(ChatColor.RED)
                .create();
        }

        public static BaseComponent[] tpacceptCommandSourceOffline(String source) {

            return new ComponentBuilder(source)
                .append(TextComponent.fromLegacyText(source))
                .append(" ist nicht online.")
                .color(ChatColor.RED)
                .create();
        }
    }

    public static class LoosePermission {
        
        public static String bypassChatFilter() {
            
            return "tbungee.chatfilter.bypass";
        }
        
        public static String bypassChatFilter(String permission) {
            
            return "tbungee.chatfilter.bypass." + permission;
        }
    }
    
    public static class Values {

        public static Integer tpaCommandValiditySeconds() {

            return 15;
        }
    }
}
