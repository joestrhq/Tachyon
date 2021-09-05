/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.or.joestr.tachyon.spigot.commands;

import com.google.common.collect.ImmutableList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.plugin.Plugin;
import at.or.joestr.tachyon.api.utils.Updater;

/**
 *
 * @author Joel
 */
public class CommandTBukkit implements TabExecutor {
  Plugin plugin;
  Updater updater;

  public CommandTBukkit(Plugin plugin, Updater updater) {
    this.plugin = plugin;
    this.updater = updater;
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (args.length != 1) {
      sender.sendMessage(
        "&7[T-Bukkit] &cSyntax: &7/" + command.getLabel() + " [reload|update]"
      );
      return true; // false, if we want to display the standard usage message
    }

    if (args[0].equalsIgnoreCase("reload")) {

      sender.sendMessage(
        "&7[T] &bReloading config file ..."
      );

      this.plugin.reloadConfig();

      return true;
    }

    if (args[0].equalsIgnoreCase("update")) {

      this.plugin.getServer().getUpdateFolderFile();

      this.updater.check((updater_) -> {
        if (updater_.isFetching()) {
          sender.sendMessage("&7[T] &bChecking for an update ...");
        }

        if (!updater_.updateAvailable()) {
          sender.sendMessage("&7[T] &bAn update to version &7" + updater_.latestVersion() + " &bis available.");
        }

        if (updater_.downloadUpdate()) {
          sender.sendMessage("&7[T] &bDownload URI: &7" + updater_.downloadUri());
        }

        if (updater_.downloadedUpdate()) {
          sender.sendMessage("&7[T] &bThe update is placed in the update folder. Please restart the server to install the update.");
        }
      });

      return true;
    }

    sender.sendMessage(
      "&7[T] &cSyntax: &7/tbukkit [reload|update|info]"
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
            return args.length == 1
              ? // If a string was passed ...
              s.startsWith(args[0])
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
