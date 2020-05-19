package me.easyplugins.commandcooldown.util;

import me.easyplugins.commandcooldown.EasyCommandCooldown;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * EasyPlugin implementation of Command.
 * Async execution, tabexecution
 */
public abstract class EasyCommand extends Command implements TabExecutor, CommandExecutor {

    private List<EasySubCommand> subCommands = new ArrayList<>();

    public EasyCommand(String name){
        this(name,null,new ArrayList<>());
    }

    public EasyCommand(String name, String... aliases){
        this(name,null,Arrays.asList(aliases));
    }

    public EasyCommand(String name, String permission, String[] aliases) {
        this(name,permission,Arrays.asList(aliases));
    }

    public EasyCommand(String name, String permission, List<String> aliases){
        super(name,null,null, aliases);
        super.setPermission(permission);
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        return true;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(this.getPermission() != null && !sender.hasPermission(this.getPermission())){
            sender.sendMessage("");//TODO: EasyConfig rework
            return true;
        }else{
            try{
                Bukkit.getScheduler().runTaskAsynchronously(EasyCommandCooldown.PLUGIN, ()->{
                    this.onExecute(sender,args);
                });
                return true;
            }catch (Exception e){
                e.printStackTrace();
                return true;
            }
        }
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if(this.getPermission() != null && !sender.hasPermission(this.getPermission())){
            return null;
        }else{
            List<String> tabCompletions = this.onTabComplete(sender,args);
            if(this.subCommands != null) this.subCommands.forEach(easySubCommand ->{
               if(sender.hasPermission(easySubCommand.getPermission())) tabCompletions.add(easySubCommand.getName());
            } );
            if(tabCompletions != null){
                return tabCompletions;
            }else if (args.length != 0){
                String lastWord = args[args.length-1];
                List<String> list = new ArrayList<>();
                Bukkit.getServer().getOnlinePlayers().forEach(player->{
                    if(player.getName().startsWith(lastWord.toLowerCase())){
                        list.add(player.getName());
                    }
                });
                return list;
            }else{
                List<String> list = new ArrayList<>();
                Bukkit.getServer().getOnlinePlayers().forEach(player->{
                   list.add(player.getName());
                });
                return list;
            }
        }
    }

    public List<EasySubCommand> getSubCommands(){
        return this.subCommands;
    }

    public abstract void onExecute(CommandSender sender, String[] args);

    public abstract List<String> onTabComplete(CommandSender sender, String[] args);

    public abstract  List<EasySubCommand> subCommands();


}
