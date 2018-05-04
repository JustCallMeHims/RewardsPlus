package com.oneshotmc.rewardsplus.mcmmo;

/**
 * Created by Michel on 2017-07-11.
 */
public class ChanceProfile {

    private double mining = 0.0;
    private double excavation = 0.0;
    private double farming = 0.0;
    private double fishing = 0.0;
    private double woodcutting = 0.0;

    public ChanceProfile(double mining, double excavation, double farming, double fishing, double woodcutting){
        this.mining = mining;
        this.excavation = excavation;
        this.farming = farming;
        this.fishing = fishing;
        this.woodcutting = woodcutting;
    }

    public ChanceProfile(){
        // No reason for this constructor
    }

    public double getMining(){
        return mining;
    }

    public void setMining(double chance){
        this.mining = chance;
    }

    public double getExcavation(){
        return excavation;
    }

    public void setExcavation(double chance){
        this.excavation = chance;
    }

    public double getFarming(){
        return farming;
    }

    public void getFarming(double chance){
        this.farming = chance;
    }

    public double getFishing(){
        return fishing;
    }

    public void setFishing(double chance){
        this.fishing = chance;
    }

    public double getWoodcutting(){
        return woodcutting;
    }

    public void setWoodcutting(double chance){
        this.woodcutting = chance;
    }


}
