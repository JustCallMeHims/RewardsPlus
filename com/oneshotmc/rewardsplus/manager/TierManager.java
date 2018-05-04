package com.oneshotmc.rewardsplus.manager;

import com.oneshotmc.rewardsplus.Tier;
import com.oneshotmc.rewardsplus.util.PluginUtil;
import org.bukkit.ChatColor;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Michel on 2017-07-06.
 */
public class TierManager implements Manager<Tier> {

    private Set<Tier> tiers = new HashSet<>();

    @Override
    public boolean add(Tier tier) {
        if(getById(tier.getId()) != null)
            return false;

        tiers.add(tier);
        return true;
    }

    @Override
    public boolean del(Tier tier) {
        return tiers.removeIf(t -> t.getId() == tier.getId());
    }

    @Override
    public Tier getById(int id) {
        return tiers.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    @Override
    public Tier getByName(String name) {
        return tiers.stream().filter(t -> ChatColor.stripColor(
                PluginUtil.enableCc(t.getName())).equalsIgnoreCase(ChatColor.stripColor(
                PluginUtil.enableCc(name)))).findFirst().orElse(null);
    }

    public Set<Tier> getTiers(){
        return tiers;
    }


}
