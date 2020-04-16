package me.easyplugins.commandcooldown.command;


import me.easyplugins.commandcooldown.Main;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class reloadCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            ((Player) sender).spigot().sendMessage(TextComponent.fromLegacyText(""));
        }
        Main.PLUGIN.getMainConfig().reload();
        return true;
    }
}
