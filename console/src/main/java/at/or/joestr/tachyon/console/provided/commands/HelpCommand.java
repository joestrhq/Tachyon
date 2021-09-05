package at.or.joestr.tachyon.console.provided.commands;

import java.util.Arrays;
import java.util.stream.Collectors;

import at.or.joestr.tachyon.console.command.ICommand;
import at.or.joestr.tachyon.console.command.ICommandSender;
import at.or.joestr.tachyon.console.console.Console;

public class HelpCommand implements ICommand {

  public HelpCommand() {
  }

  @Override
  public boolean callExecute(ICommandSender commandSender, String[] arguments) {

    if (arguments.length == 0) {

      commandSender.sendMessage("Help:");

      Console.getInstance()
        .getCommandManager()
        .getRegisteredCommands().forEach((command) -> {
          commandSender.sendMessage(command.usage() + " - " + command.description());
        });

      return true;
    }

    if (arguments.length == 1) {

      commandSender.sendMessage("Help (" + arguments[0] + "):");

      Console.getInstance()
        .getCommandManager()
        .getRegisteredCommands().stream()
        .filter(
          (command) -> command.getCommandString().startsWith(arguments[0])
        ).forEach((command) -> {
          commandSender.sendMessage(command.usage() + " - " + command.description());
        });
    }

    return true;
  }

  @Override
  public String[] callCompletions(ICommandSender sender, String[] arguments) {
    return new String[]{"empty"};
  }

  @Override
  public String usage() {
    return "help [command]";
  }

  @Override
  public String description() {
    return "Provides generic help.";
  }

  @Override
  public String getNamespaceString() {
    return "tconsole";
  }

  @Override
  public String getCommandString() {
    return "help";
  }
}
