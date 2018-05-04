package com.oneshotmc.rewardsplus.commands.sub;

import com.oneshotmc.rewardsplus.Tier;
import com.oneshotmc.rewardsplus.commands.internal.SubCommand;
import com.oneshotmc.rewardsplus.manager.Manager;
import com.oneshotmc.rewardsplus.manager.RewardManager;
import com.oneshotmc.rewardsplus.manager.TierManager;
import com.oneshotmc.rewardsplus.util.PluginUtil;
import org.apache.commons.lang3.math.NumberUtils;
import org.bukkit.command.CommandSender;

/**
 * Created by Michel on 2017-07-13.
 */
public class SSetMsg extends SubCommand {

    public SSetMsg() {
        super("setmsg", "rewardsplus.setmsg", "tier");
    }

    @Override
    public void onSubCommand(CommandSender sender, String[] args, boolean isPlayer) {
        TierManager tierManager = (TierManager) Manager.getManager("tier");
        RewardManager rewardManager = (RewardManager)Manager.getManager("reward");

        Tier tier = NumberUtils.isDigits(args[0]) ? tierManager.getById(Integer.valueOf(args[0]))
                : tierManager.getByName(args[0].replace("_", " "));

        if(tier == null){
            PluginUtil.sendMsg(sender, PluginUtil.enableCc("&cInvalid tier!"));
            return;
        }

        tier.setTierMsg(args.length > 1 ? PluginUtil.arrayIntoString(args, 1) : "");

        PluginUtil.sendMsg(sender, PluginUtil.enableCc("&aSet tier message"));
    }
}