package com.oneshotmc.rewardsplus.commands.sub;

import com.oneshotmc.rewardsplus.Reward;
import com.oneshotmc.rewardsplus.commands.internal.SubCommand;
import com.oneshotmc.rewardsplus.essentials.GUI;
import com.oneshotmc.rewardsplus.essentials.RewardsGUI;
import com.oneshotmc.rewardsplus.manager.Manager;
import com.oneshotmc.rewardsplus.manager.RewardManager;
import com.oneshotmc.rewardsplus.util.PluginUtil;
import org.apache.commons.lang3.math.NumberUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Michel on 2017-07-07.
 */
public class SSetRewards extends SubCommand {

    public SSetRewards() {
        super("setrewards", "rewardsplus.setrewards", "reward");
    }

    @Override
    public void onSubCommand(CommandSender sender, String[] args, boolean isPlayer) {
        RewardManager rewardManager = (RewardManager)Manager.getManager("reward");
        Player player = (Player) sender;

        Reward reward = NumberUtils.isDigits(args[0]) ? rewardManager.getById(Integer.valueOf(args[0]))
                : rewardManager.getByName(PluginUtil.arrayIntoString(args, 0));

        if(reward == null){
            PluginUtil.sendMsg(sender, PluginUtil.enableCc("&cInvalid reward"));
            return;
        }

        GUI gui = new RewardsGUI(reward, player);
        gui.register();
        gui.open();
    }
}
