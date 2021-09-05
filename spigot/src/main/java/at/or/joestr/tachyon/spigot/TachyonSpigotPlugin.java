/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.or.joestr.tachyon.spigot;

import java.io.File;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import at.or.joestr.tachyon.api.TachyonApi;
import at.or.joestr.tachyon.api.utils.Updater;
import at.or.joestr.tachyon.spigot.api.TachyonApiSpigot;
import at.or.joestr.tachyon.spigot.commands.CommandTSpigot;

/**
 *
 * @author Joel
 */
public class TachyonSpigotPlugin extends JavaPlugin {

	private static TachyonSpigotPlugin INSTANCE;

	private Updater updater;

	@Override
	public void onEnable() {

		INSTANCE = this;

		this.updater
			= new Updater(
				"https://mvn-repo.joestr.xyz/repository/repository/",
				Bukkit.getServer().getUpdateFolderFile(),
				86400,
				new File(Bukkit.getServer().getUpdateFolderFile(), "${project.name}.${project.packaging}")
			);

		TachyonApi.setInstance(new TachyonApiSpigot());

		this.saveDefaultConfig();

		CommandTSpigot tBukkitCommand = new CommandTSpigot(this, this.updater);
		PluginCommand tBukkitPluginCommand = this.getServer().getPluginCommand("tbukkit");
		tBukkitPluginCommand.setExecutor(tBukkitCommand);
		tBukkitPluginCommand.setTabCompleter(tBukkitCommand);
	}

	@Override
	public void onDisable() {
		
	}

	public static TachyonSpigotPlugin getInstance() {
		return INSTANCE;
	}
}
