/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.or.joestr.tachyon.spigot;

import java.io.File;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.PluginLoadOrder;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.annotation.plugin.ApiVersion;
import org.bukkit.plugin.java.annotation.plugin.Description;
import org.bukkit.plugin.java.annotation.plugin.LoadOrder;
import org.bukkit.plugin.java.annotation.plugin.LogPrefix;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.Website;
import org.bukkit.plugin.java.annotation.plugin.author.Author;
import at.or.joestr.tachyon.api.TachyonApi;
import at.or.joestr.tachyon.api.utils.Updater;
import at.or.joestr.tachyon.spigot.api.TachyonAPIBukkit;
import at.or.joestr.tachyon.spigot.commands.CommandTBukkit;

/**
 *
 * @author Joel
 */
@Plugin(name = "Tachyon-Bukkit-Plugin", version = "0.1.8")
@Description(value = "The Tachyon unit for servers which implement the Bukkit API.")
@Website(value = "https://git.joestr.xyz/joestr/Tachyon/Bukkit-Plugin")
@Author(value = "joestr")
@LoadOrder(value = PluginLoadOrder.STARTUP)
@LogPrefix(value = "Tachyon Bukkit")
//@Dependency(value="LuckPerms")
@ApiVersion(value = ApiVersion.Target.v1_13)
//@LoadBefore("")
//@SoftDependency("")
//@Command(name = "foo", desc = "Foo command", aliases = {"foobar", "fubar"}, permission = "test.foo", permissionMessage = "You do not have permission!", usage = "/<command> [test|stop]")
//@Permission(name = "test.foo", desc = "Allows foo command", defaultValue = PermissionDefault.OP)
//@Permission(name = "test.*", desc = "Wildcard foo permission", defaultValue = PermissionDefault.OP)
//@Permission(name = "test.*", desc = "Wildcard permission", children = {@ChildPermission(name ="test.foo")})
public class TachyonBukkitPlugin extends JavaPlugin {

	private static TachyonBukkitPlugin INSTANCE;

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

		TachyonApi.setInstance(new TachyonAPIBukkit());

		this.saveDefaultConfig();

		CommandTBukkit tBukkitCommand = new CommandTBukkit(this, this.updater);
		PluginCommand tBukkitPluginCommand = this.getServer().getPluginCommand("tbukkit");
		tBukkitPluginCommand.setExecutor(tBukkitCommand);
		tBukkitPluginCommand.setTabCompleter(tBukkitCommand);
	}

	@Override
	public void onDisable() {
		
	}

	public static TachyonBukkitPlugin getInstance() {
		return INSTANCE;
	}
}
