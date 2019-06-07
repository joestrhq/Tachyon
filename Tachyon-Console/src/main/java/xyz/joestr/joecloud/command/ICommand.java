package xyz.joestr.joecloud.command;

public interface ICommand {

    public boolean callExecute(ICommandSender sender, String[] arguments);

    public String[] callCompletions(ICommandSender sender, String[] arguments);

    public String usage();

    public String description();

    public String getNamespaceString();

    public String getCommandString();
}
