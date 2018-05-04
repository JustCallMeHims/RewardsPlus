package com.oneshotmc.rewardsplus.listener;

import com.oneshotmc.rewardsplus.Reward;
import com.oneshotmc.rewardsplus.util.Data;
import com.oneshotmc.rewardsplus.util.MCMMO;
import com.oneshotmc.rewardsplus.util.PluginUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerFishEvent;

/**
 * Created by Michel on 2017-07-08.
 */
public class SkillListener implements Listener {

    private MCMMO mcmmo;
    private Data data;

    public SkillListener(Data data, MCMMO mcmmo){
        this.data = data;
        this.mcmmo = mcmmo;
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.isCancelled()) {
            return;
        }

        double deduction = 0.0;
        Material mat = event.getBlock().getType();

        String inHand = (PluginUtil.isOld() ? event.getPlayer().getItemInHand().getType().name() :
                event.getPlayer().getEquipment().getItemInMainHand().getType().name());

        if (inHand == null)
            inHand = "";

        Player player = event.getPlayer();


        if (data.useMCMMO())
            deduction = mcmmo.getDeduction(player, mat, inHand);

        Reward reward = Reward.spin(deduction);
        if (reward == null)
            return;

        reward.reward(event.getPlayer(), true);
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onFishing(PlayerFishEvent event){
        if(event.isCancelled() || event.getCaught() == null)
            return;


        double deduction = 0.0;

        if(data.useMCMMO())
            deduction = mcmmo.getDeduction(event.getPlayer(), null, null);


        Reward reward = Reward.spin(deduction);

        if(reward == null)
            return;

        reward.reward(event.getPlayer(), true);
    }

}
