package com.oneshotmc.rewardsplus;

import com.oneshotmc.rewardsplus.commands.MainCmd;
import com.oneshotmc.rewardsplus.exceptions.TierChanceTooLowException;
import com.oneshotmc.rewardsplus.listener.SkillListener;
import com.oneshotmc.rewardsplus.manager.RewardManager;
import com.oneshotmc.rewardsplus.manager.TierManager;
import com.oneshotmc.rewardsplus.util.Data;
import com.oneshotmc.rewardsplus.util.MCMMO;
import com.oneshotmc.rewardsplus.util.RewardConfig;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Nick on 2017-07-05.
 */
public class RewardsPlus extends JavaPlugin {

    private static RewardsPlus instance;
    private RewardManager rewardMngr;
    private TierManager tierMngr;
    private Data data;
    private RewardConfig rewardConfig;

    public void onEnable(){
        instance = this; // I'm lazy
        rewardMngr = new RewardManager();
        tierMngr = new TierManager();
        saveDefaultConfig();
        data = new Data(getConfig());
        data.load();
        rewardConfig = new RewardConfig();
        try {
            rewardConfig.loadAllTiers(false);
            rewardConfig.loadAllRewards(false);
        } catch (TierChanceTooLowException e) {
            e.printStackTrace();
        }
        getCommand("rewardsplus").setExecutor(new MainCmd());
        setupListener();
    }

    private void setupListener(){
        Bukkit.getPluginManager().registerEvents(new SkillListener(data, data.useMCMMO() ? new MCMMO(data) : null), this);
    }

    public void onDisable(){
        rewardConfig.saveAllTiers(false);
        // Write to file @ last call, we don't want to write when it's not necessary
        rewardConfig.saveAllRewards(true);
    }

    public void register(Listener listener){
        Bukkit.getPluginManager().registerEvents(listener, this);
    }

    public static RewardsPlus getInstance(){
        return instance;
    }

    public RewardManager getRewardMngr(){
        return rewardMngr;
    }

    public TierManager getTierMngr(){
        return tierMngr;
    }

    public RewardConfig getRewardConf(){
        return rewardConfig;
    }

    public Data getData(){
        return data;
    }


}
