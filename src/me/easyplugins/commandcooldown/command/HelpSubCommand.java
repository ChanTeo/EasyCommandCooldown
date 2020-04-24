package me.easyplugins.commandcooldown.command;

import me.easyplugins.commandcooldown.EasyCommandCooldown;
import me.easyplugins.commandcooldown.util.EasySubCommand;
import org.bukkit.command.CommandSender;

import java.util.List;

public class HelpSubCommand extends EasySubCommand {

    public HelpSubCommand() {
        super("help", 0, 0);
    }

    @Override
    public String getUsage() {
        return null;
    }

    @Override
    public String getPermission() {
        return EasyCommandCooldown.PLUGIN.getMainConfig().getPermission("help");
    }

    @Override
    public List<String> getCompletions() {
        return null;
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        sender.sendMessage("&m&8---------&r&8|&2&l Easy&7CC&r&8 |&m--------- ");
        sender.sendMessage(" &2/easycc reload &8- &7Reload the configuration");
        sender.sendMessage(" &2/easycc add [command] [cooldown] [permission] &8- &7Add a cooldown to a command");
        sender.sendMessage(" &2/easycc help &8- &7Show this helpmenu");
        sender.sendMessage("&m&8----------------------------");
    }
}
