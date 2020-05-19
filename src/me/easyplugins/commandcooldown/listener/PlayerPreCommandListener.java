package me.easyplugins.commandcooldown.listener;

import me.easyplugins.commandcooldown.EasyCommandCooldown;
import me.easyplugins.commandcooldown.enumerator.EasyTimeFormat;
import me.easyplugins.commandcooldown.handle.CooldownCommand;
import me.easyplugins.commandcooldown.handle.PlayerCooldown;
import me.easyplugins.commandcooldown.util.EasyUtil;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;



public class PlayerPreCommandListener implements Listener {

    @EventHandler()
    public void onPlayerPreCommand(PlayerCommandPreprocessEvent event){
        // This could be done later, however as this list is most likely shorter than the list of players
        // we execute this first to continue if the executed command is not included in cooldowns.
        //
        // check if command is included in cooldowns
        CooldownCommand cooldownCommand = null;
        for (CooldownCommand cmd : EasyCommandCooldown.PLUGIN.getMainConfig().getCommands()) {
            if (event.getMessage().startsWith(cmd.getIdentifier())) {
                cooldownCommand = cmd;
                break;
            }
        }
        // command is not on cooldown, exit. As said above, fast check beforehand.
        if(cooldownCommand == null) return;


        // check if player can bypass cooldown for this command
        // check if the player has a bypass to prevent running through the list
        Player player = event.getPlayer();
        if(player.hasPermission(cooldownCommand.getBypass())) return;

        // is player already on cooldown?
        // now that we know this command has to be checked and the player has no bypass perms we can check
        // if he is already on cooldown
        for(PlayerCooldown pcd : EasyCommandCooldown.COOLDOWNS){
            // player in cooldown?
            // player has this specific command on cooldown?
            // safety check, cooldown not over
            if(pcd.getPlayerName().equals(player.getName()) && event.getMessage().startsWith(pcd.getCommand()) && pcd.getEarliestExecution() > System.currentTimeMillis()){
                player.spigot().sendMessage(
                        TextComponent.fromLegacyText(
                            EasyCommandCooldown.PLUGIN.getMainConfig().getMessage(EasyCommandCooldown.PLUGIN.getMainConfig().getMessage("plugin_prefix"))
                            + EasyUtil.formatTime(EasyCommandCooldown.PLUGIN.getMainConfig().getMessage("command-on-cooldown"),
                                                    pcd.getEarliestExecution()-System.currentTimeMillis(),
                                                    EasyTimeFormat.HMS).replace("%command%",pcd.getCommand()))
                );
                event.setCancelled(true);
                return;
            }
        }

        // get the cooldown for the command, init with highest in case no perms at all
        int cooldown = cooldownCommand.getHighestCooldown();
        for(Map.Entry<Integer,String> entry : cooldownCommand.getCooldowns().entrySet()){
            if(player.hasPermission(entry.getValue())){
                cooldown = entry.getKey();
                break;
            }
        }

        // add player to cooldownlist
        // put player in list, and remove after cooldown (async)
        PlayerCooldown playerCooldown = new PlayerCooldown(player.getName(),cooldownCommand.getIdentifier(),System.currentTimeMillis(),cooldown);
        EasyCommandCooldown.COOLDOWNS.add(playerCooldown);

        new BukkitRunnable(){
            public void run(){
                EasyCommandCooldown.COOLDOWNS.remove(playerCooldown);
            }
        }.runTaskLaterAsynchronously(EasyCommandCooldown.PLUGIN,cooldown*1000L);
    }

}
