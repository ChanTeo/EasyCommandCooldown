package me.easyplugins.commandcooldown.listener;

import me.easyplugins.commandcooldown.Main;
import me.easyplugins.commandcooldown.enumerator.EasyMessage;
import me.easyplugins.commandcooldown.enumerator.EasyTimeFormat;
import me.easyplugins.commandcooldown.handle.CooldownCommand;
import me.easyplugins.commandcooldown.handle.PlayerCooldown;
import me.easyplugins.commandcooldown.util.EasyUtil;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.Map;
import java.util.TreeMap;


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
                long remainingTime = System.currentTimeMillis() - playerCooldown.getEarliestExecution();
                player.spigot().sendMessage(TextComponent.fromLegacyText(EasyUtil.formatTime(Main.PLUGIN.getMainConfig().getMessage(EasyMessage.COMMAND_ON_COOLDOWN),remainingTime, EasyTimeFormat.HMS).replace("%command%",playerCooldown.getCommand())));
                event.setCancelled(true);
            }else{
                Main.COOLDOWNS.remove(playerCooldown);
            }
        }else{
            int cooldown = cooldownCommand.getHighestCooldown();
            Map<Integer,String> sorted = new TreeMap<>(cooldownCommand.getCooldowns());
            for(Map.Entry<Integer,String> entry : sorted.entrySet()){
                if(player.hasPermission(entry.getValue())){
                    cooldown = entry.getKey();
                    break;
                }
            }
            Main.COOLDOWNS.add(new PlayerCooldown(player,cooldownCommand.getIdentifier(),System.currentTimeMillis(),cooldown));
        }
    }


}
