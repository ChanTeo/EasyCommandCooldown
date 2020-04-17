package me.easyplugins.commandcooldown.listener;

import me.easyplugins.commandcooldown.Main;
import me.easyplugins.commandcooldown.enumerator.EasyMessage;
import me.easyplugins.commandcooldown.enumerator.EasyTimeFormat;
import me.easyplugins.commandcooldown.handle.CooldownCommand;
import me.easyplugins.commandcooldown.handle.PlayerCooldown;
import me.easyplugins.commandcooldown.util.EasyUtil;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;



public class PlayerPreCommandListener implements Listener {

    @EventHandler()
    public void onPlayerPreCommand(PlayerCommandPreprocessEvent event){
        //check if command is included in cooldowns
        CooldownCommand cooldownCommand = null;
        for (CooldownCommand cmd : Main.PLUGIN.getMainConfig().getCommands()) {
            if (event.getMessage().startsWith(cmd.getExecution())) {
                cooldownCommand = cmd;
                break;
            }
        }
        //command is not on cooldown, exit check
        if(cooldownCommand == null) return;


        //check if player can bypass cooldown for this command
        Player player = event.getPlayer();
        if(player.hasPermission(cooldownCommand.getBypass())) return;

        //is player already on cooldown?
        for(PlayerCooldown pcd : Main.COOLDOWNS){
            if(pcd.getPlayerName().equals(player.getName())){
                if(event.getMessage().startsWith(pcd.getCommand())){
                    if(pcd.getEarliestExecution() > System.currentTimeMillis()){
                        player.spigot().sendMessage(TextComponent.fromLegacyText(Main.PLUGIN.getMainConfig().getMessage(EasyMessage.PLUGIN_PREFIX) + EasyUtil.formatTime(Main.PLUGIN.getMainConfig().getMessage(EasyMessage.COMMAND_ON_COOLDOWN),pcd.getEarliestExecution()-System.currentTimeMillis(), EasyTimeFormat.HMS).replace("%command%",pcd.getCommand())));
                        event.setCancelled(true);
                        return;
                    }
                }
            }
        }

        //player not on cooldown, get cooldown for player
        int cooldown = cooldownCommand.getHighestCooldown();
        for(Map.Entry<Integer,String> entry : cooldownCommand.getCooldowns().entrySet()){
            if(player.hasPermission(entry.getValue())){
                cooldown = entry.getKey();
                break;
            }
        }

        //add player to cooldownlist
        PlayerCooldown playerCooldown = new PlayerCooldown(player.getName(),cooldownCommand.getExecution(),System.currentTimeMillis(),cooldown);
        Main.COOLDOWNS.add(playerCooldown);
        Bukkit.getScheduler().runTaskLaterAsynchronously(Main.PLUGIN, new BukkitRunnable(){
            public void run(){
                Main.COOLDOWNS.remove(playerCooldown);
            }
        },cooldown*1000);
    }

}
