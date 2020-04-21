package me.easyplugins.commandcooldown.command;

import me.easyplugins.commandcooldown.EasyConfig;
import me.easyplugins.commandcooldown.Main;
import me.easyplugins.commandcooldown.util.EasySubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class ReloadSubCommand extends EasySubCommand {

    private EasyConfig config = Main.PLUGIN.getMainConfig();

    public ReloadSubCommand() {
        super("reload", 0, 0);
    }

    @Override
    public String getUsage() {
        return "/easycc reload";
    }

    @Override
    public String getPermission() {
        return Main.PLUGIN.getMainConfig().getPermission("reload");
    }

    @Override
    public List<String> getCompletions() {
        return null;
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        config.reload();
        if(sender instanceof Player)
            sender.sendMessage(config.getCommandsLoaded().replace("%commands%",String.valueOf(config.getCommands().size())));
    }
}
