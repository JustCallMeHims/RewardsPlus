package com.oneshotmc.rewardsplus.util;

import com.oneshotmc.rewardsplus.RewardsPlus;
import com.oneshotmc.rewardsplus.mcmmo.ChanceProfile;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Michel on 2017-07-05.
 */
public class Data {

    private ChanceProfile chanceProfile;
    private boolean mcmmo = false;

    private Set<Material> mining = new HashSet<>();
    private Set<Material> farming = new HashSet<>();
    private Set<Material> woodcutting = new HashSet<>();
    private Set<Material> excavation = new HashSet<>();

    private FileConfiguration config;
    private Sound sound;

    public String prefix = "[prefix]";
    public String partDropMsg = "Some items were dropped!";
    public String dropMsg = "Your reward was dropped";
    public String rewardMsg = "DROPPED!";

    public Data(FileConfiguration config){
        this.config = config;
    }

    public void load(){
        prefix = config.getString("messages.prefix");
        partDropMsg = config.getString("messages.rewards-partially-dropped");
        dropMsg = config.getString("messages.rewards-dropped");
        rewardMsg = config.getString("messages.reward");
        config.getStringList("mining").forEach(s -> mining.add(Material.valueOf(s)));
        config.getStringList("woodcutting").forEach(s -> woodcutting.add(Material.valueOf(s)));
        config.getStringList("farming").forEach(s -> farming.add(Material.valueOf(s)));
        config.getStringList("excavation").forEach(s -> excavation.add(Material.valueOf(s)));

        double mining = config.getDouble("mcmmo.chance-increase-mcmmo.mining");
        double woodcutting = config.getDouble("mcmmo.chance-increase-mcmmo.woodcutting");
        double farming = config.getDouble("mcmmo.chance-increase-mcmmo.farming");
        double fishing = config.getDouble("mcmmo.chance-increase-mcmmo.fishing");
        double excavation = config.getDouble("mcmmo.chance-increase-mcmmo.excavation");

        this.chanceProfile = new ChanceProfile(mining, excavation, farming, fishing, woodcutting);
        sound = Sound.valueOf(config.getString("reward-sound"));
        setMcmmo();
        /*
        rewards-partially-dropped: '&cSome items were dropped onto the ground!'
  rewards-dropped: '&cYour reward has been dropped onto the ground!'
  reward: '&cYou found a {reward}'
         */
    }

    private void setMcmmo(){
        boolean existent = RewardsPlus.getInstance().getServer().getPluginManager().isPluginEnabled("mcMMO");
        mcmmo = existent && config.getBoolean("mcmmo.enabled");
    }


    public boolean useMCMMO(){
        return mcmmo;
    }

    public Sound getSound(){
        return sound;
    }

    public ChanceProfile getChanceProf(){
        return chanceProfile;
    }

    public boolean isMining(Material material){
        return mining.contains(material);
    }

    public boolean isFarming(Material material){
        return farming.contains(material);
    }

    public boolean isWoodcutting(Material material){
        return woodcutting.contains(material);
    }

    public boolean isExcavation(Material material){
        return excavation.contains(material);
    }
}
