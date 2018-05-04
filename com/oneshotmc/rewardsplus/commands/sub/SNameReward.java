package com.oneshotmc.rewardsplus.commands.sub;

import com.oneshotmc.rewardsplus.Reward;
import com.oneshotmc.rewardsplus.commands.internal.SubCommand;
import com.oneshotmc.rewardsplus.manager.Manager;
import com.oneshotmc.rewardsplus.manager.RewardManager;
import com.oneshotmc.rewardsplus.util.PluginUtil;
import org.apache.commons.lang3.math.NumberUtils;
import org.bukkit.command.CommandSender;

/**
 * Created by Admin on 16-7-2017.
 */
public class SNameReward extends SubCommand {

    public SNameReward() {
        super("namereward", "rewardsplus.namereward", "reward", "name");
    }

    @Override
    public void onSubCommand(CommandSender sender, String[] args, boolean isPlayer) {
        RewardManager rewardManager = (RewardManager) Manager.getManager("reward");

        Reward reward = NumberUtils.isDigits(args[0]) ? rewardManager.getById(Integer.valueOf(args[0]))
                : rewardManager.getByName(args[0].replace("_", " "));

        if (reward == null) {
            PluginUtil.sendMsg(sender, PluginUtil.enableCc("&cInvalid reward"));
            return;
        }

        reward.setName(PluginUtil.arrayIntoString(args, 1));
        PluginUtil.sendMsg(sender, PluginUtil.enableCc("&aChanged name of reward!"));

    }
}