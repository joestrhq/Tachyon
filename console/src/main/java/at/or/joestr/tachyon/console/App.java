package at.or.joestr.tachyon.console;

import at.or.joestr.tachyon.console.command.CommandManager;
import at.or.joestr.tachyon.console.console.Console;
import at.or.joestr.tachyon.console.provided.commands.HelpCommand;
import at.or.joestr.tachyon.console.provided.commands.PrintCommand;

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
