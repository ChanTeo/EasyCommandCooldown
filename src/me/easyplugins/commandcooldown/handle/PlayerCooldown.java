package me.easyplugins.commandcooldown.handle;

public class PlayerCooldown {

    String playerName;
    String command;
    Long lastExecution;
    Long cooldown;
    Long earliestExecution;


    public PlayerCooldown(String playerName, String command,long lastExecution, long cooldown){
        this.playerName = playerName;
        this.command = command;
        this.lastExecution = lastExecution;
        this.cooldown = cooldown*1000;
        this.earliestExecution = this.cooldown + this.lastExecution;
    }

    public String getPlayerName() {
        return playerName;
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
