package com.oneshotmc.rewardsplus.commands.sub;

import com.oneshotmc.rewardsplus.Tier;
import com.oneshotmc.rewardsplus.commands.internal.SubCommand;
import com.oneshotmc.rewardsplus.exceptions.TierChanceTooLowException;
import com.oneshotmc.rewardsplus.manager.Manager;
import com.oneshotmc.rewardsplus.manager.TierManager;
import com.oneshotmc.rewardsplus.util.PluginUtil;
import org.apache.commons.lang3.math.NumberUtils;
import org.bukkit.command.CommandSender;

/**
 * Created by Michel on 2017-07-13.
 */
public class SSetTierChance extends SubCommand {

    public SSetTierChance() {
        super("settierchance", "rewardsplus.settierchance", "tier", "chance");
    }

    @Override
    public void onSubCommand(CommandSender sender, String[] args, boolean isPlayer) {
        TierManager tierManager = (TierManager) Manager.getManager("tier");

        Tier tier = NumberUtils.isDigits(args[0]) ? tierManager.getById(Integer.valueOf(args[0]))
                : tierManager.getByName(args[0].replace("_", " "));

        if(tier == null){
            PluginUtil.sendMsg(sender, PluginUtil.enableCc("&cInvalid tier!"));
            return;
        }

        try {
            tier.setChance(Integer.valueOf(args[1]));
        } catch (TierChanceTooLowException e) {
            e.printStackTrace();
        }
        
        PluginUtil.sendMsg(sender, PluginUtil.enableCc("&aUpdated chance of tier!"));
    }
}