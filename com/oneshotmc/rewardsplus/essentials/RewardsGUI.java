package com.oneshotmc.rewardsplus.essentials;

import com.oneshotmc.rewardsplus.Reward;
import com.oneshotmc.rewardsplus.util.PluginUtil;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Created by Nick on 2017-07-05.
 */
public class RewardsGUI extends GUI{

    private Reward reward;

    public RewardsGUI(Reward reward, Player player) {
        super(player, "Rewards", 5,
                false, true);

        this.reward = reward;

        if(reward.hasItems()) {
            for (int i = 0; i < reward.getItems().size(); i++) {
                set(i, reward.getItems().get(i));
            }
        }
    }

    @Override
    public void GUIClickEvent(ItemStack clickedItem, int rawSlot, Player clicker) {

    }

    @Override
    public void GUICloseEvent(Player player, List<ItemStack> content) {
        reward.setItems(content);
        PluginUtil.sendMsg(player, PluginUtil.enableCc("&aRewards saved!"));
    }
}
