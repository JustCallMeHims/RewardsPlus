package com.oneshotmc.rewardsplus;

import com.oneshotmc.rewardsplus.exceptions.TierChanceTooLowException;
import org.bukkit.Bukkit;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Nick on 2017-07-05.
 */
public class Rarity {

    private int chance;

    public Rarity(int chance) throws TierChanceTooLowException {
        if(chance <= 2){
            throw new TierChanceTooLowException();
        }
        this.chance = chance;
    }

    public int getChance(){
        return chance;
    }

    public static boolean perform(Rarity rarity, double deduction){
        double dChance = (rarity.getChance()/100.0)*(100.0-deduction);
        if(dChance < 3)
            dChance = 3;

        return ThreadLocalRandom.current().nextInt(1, (int) dChance +1) == 2;
    }

}
