package me.easyplugins.commandcooldown.command;


import me.easyplugins.commandcooldown.handle.EasyPermission;
import me.easyplugins.commandcooldown.util.EasyCommand;
import me.easyplugins.commandcooldown.util.EasySubCommand;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;

public class BaseCommand extends EasyCommand {

    public BaseCommand() {
        super("easycommand", EasyPermission.get("reload"), new String[]{"easycc"});
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        if(args.length > 0){
            subCommands().stream().filter(command -> command.getName().equals(args[0])).findFirst().ifPresent(subCommand -> subCommand.execute(sender, args));
        }else{
            onExecute(sender, new String[]{"help"});
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return null;
    }

    @Override
    public List<EasySubCommand> subCommands() {
        return Arrays.asList(
                new ReloadSubCommand(),
                new HelpSubCommand(),
                new AddSubCommand()
        );
    }
}
