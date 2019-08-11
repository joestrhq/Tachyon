/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.joestr.tachyon.bukkit_plugin.commands;

import com.google.common.collect.ImmutableList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.plugin.Plugin;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import xyz.joestr.tachyon.api.TachyonAPI;
import xyz.joestr.tachyon.api.request.RequestManager;
import xyz.joestr.tachyon.api.utils.Updater;

/**
 *
 * @author Joel
 */
@org.bukkit.plugin.java.annotation.command.Command(
    name = "tbukkit",
    desc = "The main command for the Tachyon Bukkit unit.",
    //aliases = {"", ""},
    permission = "tbukkit.commands.tbukkit",
    permissionMessage = "&cYou are lacking the permission &7tbukkit.commands.tbukkit&c.",
    usage = "/<command> [reload|update]"
)
public class TBukkitCommand implements TabExecutor {

    Plugin plugin;
    Updater updater;

    public TBukkitCommand(Plugin plugin, Updater updater) {
        this.plugin = plugin;
        this.updater = updater;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        
        if(args.length != 1) {
            sender.sendMessage(
                "&cSyntax: &7/" + command.getLabel() + " [reload|update]"
            );
            return true; // false, if we want to display the standard usage message
        }
        
        if(args[0].equalsIgnoreCase("reload")) {
            
            sender.sendMessage(
                "&bReloading config file ..."
            );
            
            this.plugin.reloadConfig();
            
            sender.sendMessage(
                "&bRe-establishing the connection to the MQTT-broker ..."
            );
            
            try {
                TachyonAPI.getInstance().setRequestManager(
                    new RequestManager(
                        this.plugin.getConfig().getString("mqtt.broker.address"),
                        this.plugin.getConfig().getInt("mqtt.broker.port"),
                        this.plugin.getConfig().getString("mqtt.topic"),
                        this.plugin.getConfig().getInt("mqtt.qos"),
                        this.plugin.getConfig().getString("mqtt.client-id"),
                        new MemoryPersistence()
                    )
                );
            } catch (MqttException ex) {

                Bukkit.getLogger().log(
                    Level.SEVERE,
                    null,
                    ex
                );

                sender.sendMessage(
                    "&cRe-establishment failed. Please consult the server console or the log files."
                );
            }
            
            return true;
        }
        
        if(args[0].equalsIgnoreCase("update")) {
            
           this.plugin.getServer().getUpdateFolderFile();
           
           this.updater.check((updater_) -> {
                if (updater_.isFetching()) {
                    sender.sendMessage("&bChecking for an update ...");
                }

                if (!updater_.updateAvailable()) {
                    sender.sendMessage("&bAn update to version &7" + updater_.latestVersion() + " &bis available.");
                }

                if (updater_.downloadUpdate()) {  
                    sender.sendMessage("&bDownload URI: &7" + updater_.downloadUri());
                }

                if (updater_.downloadedUpdate()) {
                    sender.sendMessage("&bThe update is placed in the update folder. Please restart the server to install the update.");
                }
           });
           
           return true;
        }
        
        sender.sendMessage(
            "&cSyntax: &7/tbukkit [reload|update]"
        );
        return true; // false, if we want to display the standard usage message
    }
    
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        // 0 or 1 argument(s) passed
        if (args.length <= 1) {
            return ImmutableList.copyOf(
                Arrays
                    .asList("reload", "update")
                    .stream()
                    .filter(s -> {
                        return
                            args.length == 1 ?       // If a string was passed ...
                            s.startsWith(args[0]) : // ... filter the start for it
                            false                    // If not, keep the entry
                        ;
                    })
                    .collect(Collectors.toList())    // Bundle to a list
                );
        }
        
        // Return a empty list
        return ImmutableList.of();
    }
    
}
