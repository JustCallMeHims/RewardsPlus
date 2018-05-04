package com.oneshotmc.rewardsplus.manager;

import com.oneshotmc.rewardsplus.Reward;
import com.oneshotmc.rewardsplus.Tier;
import com.oneshotmc.rewardsplus.util.PluginUtil;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Michel on 2017-07-05.
 */
public class RewardManager implements Manager<Reward>{

    private List<Reward> rewards = new ArrayList<>();

    @Override
    public boolean add(Reward reward) {
        if(getById(reward.getId()) != null)
            return false;

        rewards.add(reward);
        return true;
    }

    @Override
    public boolean del(Reward reward) {
        return rewards.removeIf(r -> r.getId() == reward.getId());
    }

    @Override
    public Reward getById(int id) {
        return rewards.stream().filter(r -> r.getId() == id).findFirst().orElse(null);
    }

    @Override
    public Reward getByName(String name) {
        return rewards.stream().filter(r -> ChatColor.stripColor(
                PluginUtil.enableCc(r.getName())).equalsIgnoreCase(ChatColor.stripColor(
                PluginUtil.enableCc(name)))).findFirst().orElse(null);
    }

    public List<Reward> getByTier(Tier tier) {
        return rewards.stream().filter(r -> r.getTier().getId() == tier.getId()).collect(Collectors.toList());
    }

    public List<Reward> getRewards(){
        return rewards;
    }
}
