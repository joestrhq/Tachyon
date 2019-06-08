package xyz.joestr.tachyon.console;

import xyz.joestr.tachyon.console.command.CommandManager;
import xyz.joestr.tachyon.console.console.Console;
import xyz.joestr.tachyon.console.provided.commands.HelpCommand;
import xyz.joestr.tachyon.console.provided.commands.PrintCommand;

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
