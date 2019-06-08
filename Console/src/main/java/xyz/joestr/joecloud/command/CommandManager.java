package xyz.joestr.joecloud.command;

import java.util.ArrayList;
import java.util.Collection;

import xyz.joestr.joecloud.command.ICommand;
import xyz.joestr.joecloud.logger.JCLogger;

public class CommandManager {

    private static CommandManager instance;
    private Collection<ICommand> registeredCommands = new ArrayList<>();

    private CommandManager() {

    }

    public static CommandManager getInstance() {

        if (instance == null) {
            instance = new CommandManager();
        }

        return instance;
    }

    /**
     * Registers a {@link Command} to the {@link Collection} of {@link Command}s
     * in the {@link CommandManager}.
     *
     * @param command The {@link Command} to register.
     */
    public void registerCommand(ICommand command) {

        if (this.registeredCommands.contains(command)) {

            JCLogger.error("Command '" + command.getCommandString() + "' is already registered! Cannot register");
            return;
        }

        JCLogger.info("Command '" + command.getCommandString() + "' registered.");

        this.registeredCommands.add(command);
    }

    ;

    /**
     * Unregisters a {@link Command} from the {@link Collection} of {@link Command}s in the {@link CommandManager}.
     * @param command The {@link Command} to unregister.
     * @throws IllegalArgumentException If the {@link Collection} of {@link Command}s does not contain {@code command}.
     */
    public void unregisterCommand(ICommand command) throws IllegalArgumentException {

        // If the collection of commands does not contain command, ...
        if (!this.registeredCommands.contains(command)) {

            JCLogger.error("Command '" + command.getCommandString() + "' was not registered! Cannot unregister!");
            return;
        }

        JCLogger.info("Command '" + command.getCommandString() + "' unregistered!");

        // Unregister 'command' by removing it from the collection of commands.
        this.registeredCommands.remove(command);
    }

    public Collection<ICommand> getRegisteredCommands() {

        Collection<ICommand> copiedRegisteredCommands = new ArrayList<>();

        copiedRegisteredCommands.addAll(this.registeredCommands);

        return copiedRegisteredCommands;
    }
}
