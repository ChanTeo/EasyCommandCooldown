package me.easyplugins.commandcooldown.enumerator;

public enum EasyMessage {

    PLUGIN_PREFIX("plugin_prefix", "&7&l[&2&lEasy&f&lCC&7&l] &f"),
    NO_PERMISSION("no-permission", "&7You don't have permission to use this command!"),
    COMMAND_ON_COOLDOWN("command-on-cooldown","&7You have to wait &2{hrs} {min} {sec} to use {command} again");
    private String identifier;
    private String defaultMessage;


    EasyMessage(String identifier, String defaultMessage) {
        this.identifier = identifier;
        this.defaultMessage = defaultMessage;
    }

    public String getIndex() {
        return identifier;
    }

    public String getDefault() {
        return identifier;
    }

}
