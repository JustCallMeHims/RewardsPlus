package com.oneshotmc.rewardsplus;

import com.oneshotmc.rewardsplus.exceptions.TierChanceTooLowException;
import org.bukkit.ChatColor;

/**
 * Created by Nick on 2017-07-05.
 */
public class Tier {

    private int id; // the id = the rank of the tier
    private String name;
    private String tierMsg;
    private Rarity rarity;

    public Tier(int id, String name, int chance) throws TierChanceTooLowException {
        this.id = id;
        this.name = name;
        this.rarity = new Rarity(chance);
    }

    public Tier(int id, String name, Rarity rarity) throws TierChanceTooLowException {
        this.id = id;
        this.name = name;
        this.rarity = rarity;
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public void setChance(int chance) throws TierChanceTooLowException {
        this.rarity = new Rarity(chance);
    }

    public boolean hasTierMsg(){
        return tierMsg != null && tierMsg.length() > 0;
    }

    public String getTierMsg(){
        return tierMsg;
    }

    public void setTierMsg(String msg){
        this.tierMsg = msg;
    }

    public Rarity getRarity(){
        return rarity;
    }

}
