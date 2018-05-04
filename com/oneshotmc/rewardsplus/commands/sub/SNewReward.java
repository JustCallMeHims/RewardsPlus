package com.oneshotmc.rewardsplus.commands.sub;

import com.oneshotmc.rewardsplus.Reward;
import com.oneshotmc.rewardsplus.RewardsPlus;
import com.oneshotmc.rewardsplus.Tier;
import com.oneshotmc.rewardsplus.commands.internal.SubCommand;
import com.oneshotmc.rewardsplus.manager.Manager;
import com.oneshotmc.rewardsplus.manager.RewardManager;
import com.oneshotmc.rewardsplus.manager.TierManager;
import com.oneshotmc.rewardsplus.util.PluginUtil;
import com.oneshotmc.rewardsplus.util.Data;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.command.CommandSender;

/**
 * Created by Michel on 2017-07-08.
 */
public class SNewReward extends SubCommand {

    public SNewReward() {
        super("newreward", "rewardsplus.add", "tier", "name");
    }

    @Override
    public void onSubCommand(CommandSender sender, String[] args, boolean isPlayer) {
        Data data = RewardsPlus.getInstance().getData();
        TierManager tierManager = (TierManager) Manager.getManager("tier");
        RewardManager rewardManager = (RewardManager)Manager.getManager("reward");


        Tier tier =  NumberUtils.isDigits(args[0]) ? tierManager.getById(Integer.valueOf(args[0])) : tierManager.getByName(args[0]);
        String name = PluginUtil.arrayIntoString(args, 1);

        Reward reward = new Reward(rewardManager.getRewards().size(), tier, name);

        boolean added = rewardManager.add(reward);
        PluginUtil.sendMsg(sender, PluginUtil.enableCc(added ? "&aCreated Reward!" : "&cReward already exists!"));
    }


}