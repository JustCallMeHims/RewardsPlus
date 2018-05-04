package com.oneshotmc.rewardsplus.util;

import com.oneshotmc.rewardsplus.Reward;
import com.oneshotmc.rewardsplus.RewardsPlus;
import com.oneshotmc.rewardsplus.Tier;
import com.oneshotmc.rewardsplus.exceptions.TierChanceTooLowException;
import com.oneshotmc.rewardsplus.manager.Manager;
import com.oneshotmc.rewardsplus.manager.RewardManager;
import com.oneshotmc.rewardsplus.manager.TierManager;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Michel on 2017-07-11.
 */
public class RewardConfig {

    private File file;
    private YamlConfiguration config;

    public RewardConfig(){
        loadFile();
        loadConfig();
    }

    private void loadFile(){
        File file = new File(RewardsPlus.getInstance().getDataFolder(), "rewards.yml");
        if(!file.exists())
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

        this.file = file;
    }

    private void loadConfig(){
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    public void loadAllTiers(boolean clearTiers) throws TierChanceTooLowException {
        if(!config.isSet("tiers"))
            return;

        TierManager tierManager = (TierManager)Manager.getManager("tier");

        if(clearTiers)
            tierManager.getTiers().clear();

        ConfigurationSection section = config.getConfigurationSection("tiers");

        for(String s: section.getKeys(false)){
            int id = Integer.valueOf(s);
            String name = section.getString(s+ ".name").replace("_", " ");
            int chance = section.getInt(s+".chance");
            String msg = section.getString(s+".message");
            Tier tier = new Tier(id, name, chance);
            if(msg.length() > 0)
                tier.setTierMsg(msg);

            tierManager.add(tier);
        }
    }

    public void loadAllRewards(boolean clearRewards){
        if(!config.isSet("rewards"))
            return;
        RewardManager rewardManager = (RewardManager) Manager.getManager("reward");
        TierManager tierManager = (TierManager)Manager.getManager("tier");

        if(clearRewards)
            rewardManager.getRewards().clear();


        ConfigurationSection section = config.getConfigurationSection("rewards");
        for(String s: section.getKeys(false)){
            int id = Integer.valueOf(s);
            String name = section.getString(s+ ".name").replace("_", " ");
            List<String> commands = section.getStringList(s+ ".commands");
            List<ItemStack> items = Arrays.asList(((List<ItemStack>)section.get(s+".items")).toArray(new ItemStack[0]));
            int tierId = section.getInt(s+".tier");

            Tier tier = tierManager.getById(tierId);
            Reward reward = new Reward(id, tier, name);
            reward.setCommands(commands);
            reward.setItems(items);
            rewardManager.add(reward);
        }
    }

    public void saveAllRewards(boolean writeToFile){
        RewardManager rewardManager = (RewardManager) Manager.getManager("reward");
        ConfigurationSection sec = config.createSection("rewards");

        for(String s: sec.getKeys(false)){
            if(rewardManager.getById(Integer.valueOf(s)) == null)
                sec.set(s, null);
        }

        for(Reward reward: rewardManager.getRewards()){
            ConfigurationSection sec2 = sec.createSection(String.valueOf(reward.getId()));
            sec2.createSection("name");
            sec2.createSection("commands");
            sec2.createSection("items");
            sec2.createSection("tier");
            sec2.set("name", reward.getName().replace(" ", "_"));
            sec2.set("commands", reward.getCommands());
            sec2.set("items", reward.getItems().toArray(new ItemStack[0]));
            sec2.set("tier", reward.getTier().getId());
        }

        if(writeToFile)
            write();
    }

    public void saveAllTiers(boolean writeToFile){
        TierManager tierManager = (TierManager)Manager.getManager("tier");
        ConfigurationSection sec = config.createSection("tiers");
        for(String s: sec.getKeys(false)){
            if(tierManager.getById(Integer.valueOf(s)) == null)
                sec.set(s, null);
        }

        for(Tier tier: tierManager.getTiers()){
            ConfigurationSection sec2 = sec.createSection(String.valueOf(tier.getId()));
            // I think there is a better way of doing this, but I forgot
            sec2.createSection("name");
            sec2.createSection("chance");
            sec2.createSection("message");
            sec2.set("name", tier.getName().replace(" ", "_"));
            sec2.set("chance", tier.getRarity().getChance());
            sec2.set("message", tier.hasTierMsg() ? tier.getTierMsg() : "");
        }

        if(writeToFile)
            write();
    }

    public YamlConfiguration getConfig(){
        return config;
    }

    private void write(){
        try {
            config.save(this.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
