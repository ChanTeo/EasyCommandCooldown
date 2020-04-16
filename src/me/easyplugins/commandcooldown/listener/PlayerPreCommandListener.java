package me.easyplugins.commandcooldown.listener;

import me.easyplugins.commandcooldown.Main;
import me.easyplugins.commandcooldown.enumerator.EasyMessage;
import me.easyplugins.commandcooldown.handle.CooldownCommand;
import me.easyplugins.commandcooldown.handle.PlayerCooldown;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;


public class PlayerPreCommandListener implements Listener {

    @EventHandler()
    public void onPlayerPreCommand(PlayerCommandPreprocessEvent event){
        Player player = event.getPlayer();
        CooldownCommand cooldownCommand = null;

        for (CooldownCommand cmd : Main.PLUGIN.getMainConfig().getCommands()) {
            if (event.getMessage().startsWith(cmd.getExecution())) {
                cooldownCommand = cmd;
                break;
            }
        }

        // Check if there is a cooldown for this command
        if(cooldownCommand == null) return;
        // Check if player has bypass
        if(player.hasPermission(cooldownCommand.getBypass())) return;

        // is Command in Cooldown?

        String identifier = cooldownCommand.getIdentifier();

        PlayerCooldown playerCooldown = Main.COOLDOWNS.stream()
                .filter(cds->player == cds.getPlayer())
                .filter(cds-> identifier.equals(cds.getCommand()))
                .findFirst().orElse(null);

        if(playerCooldown != null){
            if(playerCooldown.getEarliestExecution() > System.currentTimeMillis()){
                player.spigot().sendMessage(TextComponent.fromLegacyText(Main.PLUGIN.getMainConfig().getMessage(EasyMessage.COMMAND_ON_COOLDOWN)));
                event.setCancelled(true);
                return;
            }else{
                Main.COOLDOWNS.remove(playerCooldown);
                return;
            }
        }else{







            return;
        }









    }


}
