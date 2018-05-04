package com.oneshotmc.rewardsplus.commands.sub;

import com.oneshotmc.rewardsplus.Rarity;
import com.oneshotmc.rewardsplus.Tier;
import com.oneshotmc.rewardsplus.commands.internal.SubCommand;
import com.oneshotmc.rewardsplus.exceptions.TierChanceTooLowException;
import com.oneshotmc.rewardsplus.manager.Manager;
import com.oneshotmc.rewardsplus.manager.TierManager;
import com.oneshotmc.rewardsplus.util.PluginUtil;
import org.bukkit.command.CommandSender;

/**
 * Created by Michel on 2017-07-07.
 */
public class SNewTier extends SubCommand {

    public SNewTier() {
        super("newtier", "rewardsplus.newtier", "rank", "chance", "name");
    }

    @Override
    public void onSubCommand(CommandSender sender, String[] args, boolean isPlayer) {
        TierManager tierManager = (TierManager)Manager.getManager("tier");

        Rarity rarity;
        Tier tier = null;

        try {
            rarity = new Rarity(Integer.valueOf(args[1]));
            tier = new Tier(Integer.valueOf(args[0]), PluginUtil.arrayIntoString(args, 2), rarity);
        } catch (TierChanceTooLowException e) {
            e.printStackTrace();
        }

        boolean added = tierManager.add(tier);
        PluginUtil.sendMsg(sender, PluginUtil.enableCc(added ? "&aCreated Tier!" : "&cTier rank already exists!"));
    }
}
