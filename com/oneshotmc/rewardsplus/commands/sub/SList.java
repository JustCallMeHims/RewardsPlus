package com.oneshotmc.rewardsplus.commands.sub;

import com.oneshotmc.rewardsplus.RewardsPlus;
import com.oneshotmc.rewardsplus.commands.internal.SubCommand;
import com.oneshotmc.rewardsplus.manager.Manager;
import com.oneshotmc.rewardsplus.manager.RewardManager;
import com.oneshotmc.rewardsplus.manager.TierManager;
import com.oneshotmc.rewardsplus.util.PluginUtil;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.bukkit.command.CommandSender;

/**
 * Created by Michel on 2017-07-11.
 */
public class SList extends SubCommand {

    public SList() {
        super("list", "rewardsplus.list", null);
    }

    @Override
    public void onSubCommand(CommandSender sender, String[] args, boolean isPlayer) {
        TierManager tierManager = (TierManager) Manager.getManager("tier");
        RewardManager rewardManager  = (RewardManager) Manager.getManager("reward");

        PluginUtil.sendMsg(sender, "MCMMO: " + RewardsPlus.getInstance().getData().useMCMMO());
        PluginUtil.sendMsg(sender, PluginUtil.enableCc("&e{Tiers}"));
        tierManager.getTiers().forEach(t ->
                PluginUtil.sendMsg(sender, PluginUtil.enableCc("&7Rank: &e"+t.getId()+
                        " &7Name: &e"+t.getName()+" &7Chance: &e1/"+t.getRarity().getChance())));
        PluginUtil.sendMsg(sender, PluginUtil.enableCc("&e{Rewards}"));
        rewardManager.getRewards().forEach(r ->
                PluginUtil.sendMsg(sender, PluginUtil.enableCc("&7ID: &e"+r.getId()+
                        " &7Name: &e"+r.getName()+" &7Tier: &e"+ r.getTier().getId()+":"+r.getTier().getName())));
    }
}