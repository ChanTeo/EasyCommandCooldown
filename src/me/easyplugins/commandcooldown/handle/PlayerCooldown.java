package me.easyplugins.commandcooldown.handle;

public class PlayerCooldown {

    private String playerName;
    private String command;
    private Long lastExecution;
    private Long cooldown;
    private Long earliestExecution;


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
