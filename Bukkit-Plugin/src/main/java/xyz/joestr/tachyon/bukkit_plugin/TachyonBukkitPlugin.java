/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.joestr.tachyon.bukkit_plugin;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.resource.PathResourceManager;
import io.undertow.server.handlers.resource.ResourceHandler;
import io.undertow.util.Headers;
import java.io.File;
import java.nio.file.Paths;
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
import xyz.joestr.tachyon.api.TachyonAPI;
import xyz.joestr.tachyon.api.utils.Updater;
import xyz.joestr.tachyon.bukkit_plugin.api.TachyonAPIBukkit;
import xyz.joestr.tachyon.bukkit_plugin.commands.TBukkitCommand;

/**
 *
 * @author Joel
 */
@Plugin(name="Tachyon-Bukkit-Plugin", version="0.1.8")
@Description(value="The Tachyon unit for servers which implement the Bukkit API.")
@Website(value="https://git.joestr.xyz/joestr/Tachyon/Bukkit-Plugin")
@Author(value="joestr")
@LoadOrder(value=PluginLoadOrder.STARTUP)
@LogPrefix(value="Tachyon Bukkit")
//@Dependency(value="LuckPerms")
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
        
        Undertow server =
            Undertow.builder()
                .addHttpListener(8080, "0.0.0.0")
                .setHandler(
                    Handlers.path()
                        .addPrefixPath(
                            "/players",
                            Handlers.routing()
                                .get("/", null)
                                .get(
                                    "/{uuid}",
                                    Handlers.routing()
                                        .get("/position", null)
                                    )
                                .put(
                                    "/{uuid}",
                                    Handlers.routing()
                                        .get("/position", null)
                                )
                                .setFallbackHandler(null)
                        )
                        // Redirect root path to /static to serve the index.html by default
                        .addExactPath(
                            "/",
                            Handlers.redirect("/static")
                        )
                        // Serve all static files from a folder
                        .addPrefixPath(
                            "/static",
                            new ResourceHandler(
                                new PathResourceManager(Paths.get("/path/to/www/"), 100)
                            )
                            .setWelcomeFiles("index.html")
                        )
                ).build();
        
        server.start();
    }

    @Override
    public void onDisable() {
        
    }
    
    public static TachyonBukkitPlugin getInstance() {
        return INSTANCE;
    }
}
