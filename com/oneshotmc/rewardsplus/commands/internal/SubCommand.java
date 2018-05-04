package com.oneshotmc.rewardsplus.commands.internal;

import com.oneshotmc.rewardsplus.util.PluginUtil;
import org.bukkit.command.CommandSender;

/**
 * Created by Michel on 2017-07-06.
 */
public abstract class SubCommand {

    private String name;
    private String permission;
    private String[] expectedArguments;

    public SubCommand(String name, String permission, String... expectedArguments){
        this.name = name;
        this.permission = permission;
        this.expectedArguments = expectedArguments;
    }


    public String getName(){
        return name;
    }

    public String getPermission(){
        return permission;
    }

    public String getExpectedArguments(){
        if(expectedArguments == null || expectedArguments.length == 0)
            return "";

        StringBuilder missing = new StringBuilder();

        for(int i=0; i<expectedArguments.length; i++){
            missing.append("&9<&3"+expectedArguments[i]+"&9>");
        }

        return missing.toString();
    }

    public void perform(CommandSender sender, String[] args, boolean isPlayer) {
        if(permission != null && permission.length() > 0){
            if(!sender.hasPermission(permission)) {
                sender.sendMessage("Insufficient permission!");
                return;
            }
        }


        String[] argsResult = new String[args.length-1]; // Always >= 0

        if(args.length > 1) {
            for (int i = 1; i < args.length; i++) {
                argsResult[i-1] = args[i];
            }
        }



        if(expectedArguments != null && argsResult.length < expectedArguments.length){
            StringBuilder missing = new StringBuilder("&cMissing argument(s): ");

            for(int i=argsResult.length; i<expectedArguments.length; i++){
                missing.append("&9<&3"+expectedArguments[i]+"&9>");
            }

            sender.sendMessage(PluginUtil.enableCc(missing.toString()));
            return;
        }

        onSubCommand(sender, argsResult, isPlayer);
    }

    public abstract void onSubCommand(CommandSender sender, String[] args, boolean isPlayer);

}
