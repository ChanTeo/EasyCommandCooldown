package me.easyplugins.commandcooldown.command;


import me.easyplugins.commandcooldown.Main;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class baseCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length != 1){
            sender.sendMessage(Main.PLUGIN.getMainConfig().getCommandUsage());
            return true;
        }
        if(!args[0].equals("reload")){
            sender.sendMessage(Main.PLUGIN.getMainConfig().getCommandUsage());
            return true;
        }
        if(sender instanceof Player){
            ((Player) sender).spigot().sendMessage(TextComponent.fromLegacyText(Main.PLUGIN.getMainConfig().getCommandsLoaded().replace("%commands%",Main.PLUGIN.getMainConfig().getCommands().size()+"")));
        }
        Main.PLUGIN.getMainConfig().reload();
        return true;
    }
}
