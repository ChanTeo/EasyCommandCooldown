package me.easyplugins.commandcooldown.command;

import me.easyplugins.commandcooldown.handle.EasyPermission;
import me.easyplugins.commandcooldown.util.EasySubCommand;
import org.bukkit.command.CommandSender;

import java.util.List;

public class AddSubCommand extends EasySubCommand {
    public AddSubCommand() {
        super("add", 3, 3);
    }

    @Override
    public String getUsage() {
        return null;
    }

    @Override
    public String getPermission() {
        return EasyPermission.get("add");
    }

    @Override
    public List<String> getCompletions() {
        return null;
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        /*
        TreeMap<Integer, String> cooldown = new TreeMap<>();
        cooldown.

        CooldownCommand.add(new CooldownCommand(Main.PLUGIN.getMainConfig().getLastestExecution()+1,"",));*/
    }
}
