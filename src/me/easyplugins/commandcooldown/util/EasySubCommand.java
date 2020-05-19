package me.easyplugins.commandcooldown.util;

import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;

public abstract class EasySubCommand {
    /**
     * EasyPlugin SubCommand.
     * Async execution as it is called by EasyCommand
     * automated permission and argument-Exception and handling.
     */
    private String name;
    private int expectedArgs;
    private int maximumArgs;

    public EasySubCommand(String name) {
        this(name, -1, -1);
    }

    public EasySubCommand(String name, int expectedArgs) {
        this(name, expectedArgs, expectedArgs);
    }

    public EasySubCommand(String name, int expectedArgs, int maximumArgs) {
        this.name = name;
        this.expectedArgs = expectedArgs;
        this.maximumArgs = maximumArgs;
    }

    private EasySubCommand.ConditionResult checkConditions(CommandSender sender, String[] args) {
        if (!args[0].equalsIgnoreCase(this.name)) {
            return EasySubCommand.ConditionResult.FAILURE_WRONG_NAME;
        } else if (this.expectedArgs != -1 && this.maximumArgs != -1 && args.length - 1 != this.expectedArgs && args.length - 1 > this.maximumArgs) {
            return EasySubCommand.ConditionResult.FAILURE_WRONG_ARGS_LENGTH;
        } else {
            return !this.getPermission().isEmpty() && !sender.hasPermission(this.getPermission()) ? EasySubCommand.ConditionResult.FAILURE_PERMISSION : EasySubCommand.ConditionResult.SUCCESS;
        }
    }

    public boolean execute(CommandSender sender, String[] args) {
        EasySubCommand.ConditionResult result = this.checkConditions(sender, args);
        switch(result) {
            case SUCCESS:
                this.onExecute(sender, (String[]) Arrays.copyOfRange(args, 1, args.length));
                return true;
            case FAILURE_PERMISSION:
                sender.sendMessage(EasyUtil.colorize("NOPERMS"));//TODO: EasyConfig rework
                return true;
            case FAILURE_WRONG_NAME:
                sender.sendMessage(EasyUtil.colorize("WRONGSUB"));
                return true;
            case FAILURE_WRONG_ARGS_LENGTH:
                sender.sendMessage(EasyUtil.colorize("WRONGARGS"));
                return true;
            default:
                return false;
        }
    }

    public String getName() {
        return this.name;
    }

    public abstract String getUsage();

    public abstract String getPermission();

    public abstract List<String> getCompletions();

    public abstract void onExecute(CommandSender sender, String[] args);

    public static enum ConditionResult {
        FAILURE_PERMISSION,
        FAILURE_WRONG_NAME,
        FAILURE_WRONG_ARGS_LENGTH,
        SUCCESS;
    }


}
