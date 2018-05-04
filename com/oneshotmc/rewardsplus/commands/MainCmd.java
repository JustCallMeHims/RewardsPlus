package com.oneshotmc.rewardsplus.commands;

import com.oneshotmc.rewardsplus.commands.internal.CMD;
import com.oneshotmc.rewardsplus.commands.sub.*;
import com.oneshotmc.rewardsplus.util.PluginUtil;
import org.bukkit.command.CommandSender;

/**
 * Created by Michel on 2017-07-06.
 */
public class MainCmd extends CMD{

    public MainCmd() {
        super("rewardsplus.maincmd");
        addSubCommand(new SNewReward());
        addSubCommand(new SNewTier());
        addSubCommand(new SSetTierChance());
        addSubCommand(new SSetMsg());
        addSubCommand(new SSetRewards());
        addSubCommand(new SNameReward());
        addSubCommand(new SSetCommands());
        addSubCommand(new SDelTier());
        addSubCommand(new SDelReward());
        addSubCommand(new SList());
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args, boolean isPlayer, boolean hasPermission) {
        if(performSubCmd(sender, args, isPlayer))
            return true;

        PluginUtil.sendMsg(sender, PluginUtil.enableCc("&eAvailable Subcommands: "));
        getSubCommands().stream().filter(sub -> sender.hasPermission(sub.getPermission()))
                .forEach(sub -> PluginUtil.sendMsg(sender, PluginUtil.enableCc("/rplus " + sub.getName()
                + " " + sub.getExpectedArguments())));

        return true;
    }
}
