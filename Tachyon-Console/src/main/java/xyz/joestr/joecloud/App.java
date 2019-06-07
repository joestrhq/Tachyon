package xyz.joestr.joecloud;

import xyz.joestr.joecloud.command.CommandManager;
import xyz.joestr.joecloud.console.Console;
import xyz.joestr.joecloud.provided.commands.HelpCommand;
import xyz.joestr.joecloud.provided.commands.PrintCommand;

public class App {

    public static void main(String[] args) {

        Console console = Console.getInstance();

        CommandManager.getInstance().registerCommand(new HelpCommand());
        CommandManager.getInstance().registerCommand(new PrintCommand());

        if (console != null) {
            console.start();
        } else {
            System.err.println("Console not started! Exiting application ...");
            System.exit(-1);
        }
    }
}
