/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.joestr.tachyon.bukkit_plugin;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.PluginLoadOrder;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.annotation.command.Command;
import org.bukkit.plugin.java.annotation.dependency.Dependency;
//import org.bukkit.plugin.java.annotation.dependency.SoftDependency;
import org.bukkit.plugin.java.annotation.permission.ChildPermission;
import org.bukkit.plugin.java.annotation.permission.Permission;
import org.bukkit.plugin.java.annotation.plugin.ApiVersion;
//import org.bukkit.plugin.java.annotation.dependency.LoadBefore;
import org.bukkit.plugin.java.annotation.plugin.Description;
import org.bukkit.plugin.java.annotation.plugin.LoadOrder;
import org.bukkit.plugin.java.annotation.plugin.LogPrefix;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.Website;
import org.bukkit.plugin.java.annotation.plugin.author.Author;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.server.ResourceConfig;
import xyz.joestr.tachyon.api.TachyonAPI;
import xyz.joestr.tachyon.api.utils.Updater;
import xyz.joestr.tachyon.bukkit_plugin.api.TachyonAPIBukkit;
import xyz.joestr.tachyon.bukkit_plugin.commands.TBukkitCommand;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;

/**
 *
 * @author Joel
 */
@Plugin(name="Tachyon-Bukkit-Plugin", version="0.1.7")
@Description(value="The Tachyon unit for servers which implement the Bukkit API.")
@Website(value="https://git.joestr.xyz/joestr/Tachyon/Bukkit-Plugin")
@Author(value="joestr")
@LoadOrder(value=PluginLoadOrder.STARTUP)
@LogPrefix(value="Tachyon Bukkit")
@Dependency(value="LuckPerms")
@ApiVersion(value=ApiVersion.Target.v1_13)
//@LoadBefore("")
//@SoftDependency("")
//@Command(name = "foo", desc = "Foo command", aliases = {"foobar", "fubar"}, permission = "test.foo", permissionMessage = "You do not have permission!", usage = "/<command> [test|stop]")
//@Permission(name = "test.foo", desc = "Allows foo command", defaultValue = PermissionDefault.OP)
//@Permission(name = "test.*", desc = "Wildcard foo permission", defaultValue = PermissionDefault.OP)
//@Permission(name = "test.*", desc = "Wildcard permission", children = {@ChildPermission(name ="test.foo")})
public class TachyonBukkitPlugin extends JavaPlugin {
    
    private static TachyonBukkitPlugin INSTANCE;
    
    private Updater updater;
    private HttpServer httpServer = null;
    
    @Override
    public void onEnable() {
        
        INSTANCE = this;
        
        this.updater =
            new Updater(
                "https://mvn-repo.joestr.xyz/repository/repository/",
                Bukkit.getServer().getUpdateFolderFile(),
                86400,
                new File(Bukkit.getServer().getUpdateFolderFile(), "${project.name}.${project.packaging}")
            );
        
        TachyonAPI.setInstance(new TachyonAPIBukkit());
        
        this.saveDefaultConfig();
        
        TBukkitCommand tBukkitCommand = new TBukkitCommand(this, this.updater); 
        PluginCommand tBukkitPluginCommand = this.getServer().getPluginCommand("tbukkit");
        tBukkitPluginCommand.setExecutor(tBukkitCommand);
        tBukkitPluginCommand.setTabCompleter(tBukkitCommand);
        
        this.httpServer = GrizzlyHttpServerFactory.createHttpServer(
            URI.create(
                this.getConfig().getString("listenuri")
            ),
            new ResourceConfig().packages(
                "xyz.joestr.tachyon.bukkit_plugin.rest"
            ),
            false
        );
        
        Enumeration<String> loggers = LogManager.getLogManager().getLoggerNames();
        while (loggers.hasMoreElements()) {
            String loggerName = loggers.nextElement();
            if(loggerName.contains("glassfish")) {
                Logger logger = LogManager.getLogManager().getLogger(loggerName);
                logger.setLevel(Level.OFF);     
            }
        }
        
        try {
            httpServer.start();
        } catch (IOException ex) {
            Logger.getLogger(TachyonBukkitPlugin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void onDisable() {

        this.httpServer.shutdownNow();
    }
    
    public static TachyonBukkitPlugin getInstance() {
        return INSTANCE;
    }
}
