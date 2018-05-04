package com.oneshotmc.rewardsplus.commands.internal;

import com.oneshotmc.rewardsplus.util.PluginUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michel on 2017-07-06.
 */
public abstract class CMD implements CommandExecutor{

    private String permission;
    private List<SubCommand> subCommands = new ArrayList<>();

    public CMD(String permisssion){
        this.permission = permisssion != null ? permisssion : "";
    }

    public List<SubCommand> getSubCommands(){
        return subCommands;
    }

    public void addSubCommand(SubCommand subCommand){
        if(getSubCommand(subCommand.getName()) != null)
            return;

        subCommands.add(subCommand);
    }

    public SubCommand getSubCommand(String name){
        return subCommands.stream().filter(sCmd -> sCmd.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public boolean performSubCmd(CommandSender sender, String[] args, boolean isPlayer){
        if(args.length == 0)
            return false;

        SubCommand sCmd = getSubCommand(args[0]);

        if(sCmd == null)
            return false;

        sCmd.perform(sender, args, isPlayer);
        return true;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        boolean isPlayer = sender instanceof Player;
        boolean hasPermission = sender.hasPermission(permission);

        if(!hasPermission) {
            PluginUtil.sendMsg(sender, PluginUtil.enableCc("&cInsufficient Permissions"));
            return true;
        }

        onCommand(sender, args, isPlayer, hasPermission);
        return true;
    }

    public abstract boolean onCommand(CommandSender sender, String[] args, boolean isPlayer, boolean hasPermission);

}
