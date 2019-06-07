package xyz.joestr.joecloud.console;

import java.io.IOException;
import java.util.Arrays;

import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;

import org.fusesource.jansi.AnsiConsole;
import org.jline.reader.UserInterruptException;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import xyz.joestr.joecloud.command.ICommand;
import xyz.joestr.joecloud.command.ICommandSender;
import xyz.joestr.joecloud.command.CommandManager;
import xyz.joestr.joecloud.util.Utilities;

public class Console implements ICommandSender {

    private static Console instance;

    private final Terminal terminal;
    private final LineReader lineReader;

    private boolean running = true;

    private CommandManager commandManager = null;

    public static Console getInstance() {

        if (instance == null) {
            try {
                instance = new Console();
            } catch (IOException ex) {
                System.err.println(ex.getLocalizedMessage());
            }
        }

        return instance;
    }

    private Console() throws IOException {

        this.commandManager = CommandManager.getInstance();

        AnsiConsole.systemInstall();

        terminal
            = TerminalBuilder
                .builder()
                .build();

        lineReader
            = LineReaderBuilder
                .builder()
                .terminal(terminal)
                .appName("joeCloud")
                .build();
    }

    public void start() {

        String command;
        String[] arguments;
        String[] input;

        try {
            while (this.running) {

                input = lineReader.readLine("> ").split(" ");

                command = input[0];
                arguments = Arrays.copyOfRange(input, 1, input.length);

                for (ICommand c : commandManager.getRegisteredCommands()) {

                    if (c.getCommandString().equalsIgnoreCase(command)) {
                        if (!c.callExecute(this, arguments)) {
                            this.sendMessage(c.usage());
                        }
                    }
                }
            }
        } catch (UserInterruptException ex) {

            this.running = false;
            System.exit(0);
        }
    }

    public CommandManager getCommandManager() {

        return this.commandManager;
    }

    @Override
    public void sendMessage(String message) {
        this.lineReader.printAbove("[" + Utilities.getTimestamp() + "] " + message);
    }
}
