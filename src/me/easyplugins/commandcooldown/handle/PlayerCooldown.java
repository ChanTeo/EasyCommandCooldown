package me.easyplugins.commandcooldown.handle;

import me.easyplugins.commandcooldown.Main;
import org.bukkit.entity.Player;

public class PlayerCooldown {

    Player player;
    String command;
    Long lastExecution;
    Long cooldown;
    Long earliestExecution;


    public PlayerCooldown(Player player, String command,long lastExecution, long cooldown){
        this.player = player;
        this.command = command;
        this.lastExecution = lastExecution;
        this.cooldown = cooldown*1000;
        this.earliestExecution = this.cooldown + this.lastExecution;
    }

    public Player getPlayer() {
        return player;
    }

    public String getCommand() {
        return command;
    }

    public Long getLastExecution() {
        return lastExecution;
    }

    public Long getCooldown() {
        return cooldown;
    }

    public Long getEarliestExecution() {
        return earliestExecution;
    }
}
