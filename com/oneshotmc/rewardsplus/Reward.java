package com.oneshotmc.rewardsplus;

import com.google.common.collect.Maps;
import com.oneshotmc.rewardsplus.manager.RewardManager;
import com.oneshotmc.rewardsplus.util.PluginUtil;
import com.oneshotmc.rewardsplus.util.Data;
import com.oneshotmc.rewardsplus.util.InventoryUtil;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import com.oneshotmc.rewardsplus.util.InventoryUtil.Result;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Nick on 2017-07-05.
 * Reward players with items & commands
 */
public class Reward {

    //private static Data data = RewardsPlus.getInstance().getData();
    private int id;
    private Tier tier;
    private String name;
    private List<ItemStack> items = new ArrayList<>();
    private List<String> commands = new ArrayList<>(); //ArrayList to ease loading from config

    public Reward(int id, Tier tier, String name){
        this.id = id;
        this.tier = tier;
        this.name = name;
    }


    public int getId(){
        return id;
    }

    public Tier getTier(){
        return tier;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public List<ItemStack> getItems(){
        return items;
    }

    public void setItems(List<ItemStack> items){
        this.items = items;
    }

    public void addItem(ItemStack item){
        items.add(item);
    }

    public void setCommands(List<String> commands){
        this.commands = commands;
    }

    public List<String> getCommands(){
        return commands;
    }

    public void addCommand(String command){
        commands.add(command);
    }


    public boolean hasItems(){
        return items.size() > 0;
    }

    public boolean hasCommands(){
        return commands.size() > 0;
    }

    public void reward(Player player, boolean notifyDroppedItems){

        PluginUtil.playSound(player);
        Data data = RewardsPlus.getInstance().getData();
        if(tier.hasTierMsg())
            Bukkit.broadcastMessage(PluginUtil.enableCc(data.prefix + tier.getTierMsg().replace("{player}",
                    player.getName()).replace("{reward}", this.name).replace("{tier}",
                    tier.getName())));

        String output = data.rewardMsg.replace("{reward}", this.name);

        PluginUtil.sendMsg(player, PluginUtil.enableCc(data.prefix + output));

        commands.forEach(cmd -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd.replace("{player}",
                player.getName())));

        dispatchItems(player, notifyDroppedItems);

        // Add final reward msg

    }

    private void dispatchItems(Player player, boolean notifyDroppedItems){
        if(hasItems()){
            InventoryUtil invUtil = new InventoryUtil(player);
            //  items.forEach(invUtil::addItem);

            Map<Result, Integer> results = Maps.newHashMap();

            for(ItemStack item: items){
                // Can't possibly be null, unless you fuck up.. (code wise)
                Result result = invUtil.addItem(item);

                Integer resultCount = results.get(result);
                if(resultCount == null)
                    resultCount = 0;

                results.put(result, resultCount+1);
            }

            Integer dropped = results.get(Result.DROPPED);
            Integer partDropped = results.get(Result.PARTIALLY_DROPPED);

            if(dropped == null)
                dropped = 0;
            if(partDropped == null)
                partDropped = 0;

            int totalDropped = (dropped+partDropped);

            if(totalDropped < 1)
                return;

            Data data = RewardsPlus.getInstance().getData();
            String msg = data.prefix + (totalDropped < items.size() ?
                    data.partDropMsg : data.dropMsg);

            if(notifyDroppedItems)
                PluginUtil.sendMsg(player, PluginUtil.enableCc(msg));

        }
    }

    public static Reward spin(double deduction){
        RewardManager rManager = RewardsPlus.getInstance().getRewardMngr();
        for(Tier tier: RewardsPlus.getInstance().getTierMngr().getTiers()){
            if(Rarity.perform(tier.getRarity(), deduction)){
                List<Reward> byTiers = rManager.getByTier(tier);
                if(byTiers.size() == 0)
                    continue;

                return byTiers.get(ThreadLocalRandom.current().nextInt(0, byTiers.size()));
            }
        }
        return null;
    }



}
