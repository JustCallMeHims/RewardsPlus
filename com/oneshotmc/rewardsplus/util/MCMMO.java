package com.oneshotmc.rewardsplus.util;

import com.gmail.nossr50.api.ExperienceAPI;
import com.gmail.nossr50.api.exceptions.McMMOPlayerNotFoundException;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Admin on 4-5-2018.
 */
public class MCMMO {
    private Data data;

    public MCMMO(Data data){
        this.data = data;
    }

    public double getDeduction(Player player, Material mat, String inHand) {

        double deduction = 0.0;
        if (mat != null && inHand != null) {
            try {
                if (data.isMining(mat) && inHand.contains("_PICKAXE"))
                    deduction = ExperienceAPI.getLevel(player, "MINING") *
                            data.getChanceProf().getMining();
                else if (data.isExcavation(mat) && inHand.contains("_SPADE"))
                    deduction = ExperienceAPI.getLevel(player, "EXCAVATION") *
                            data.getChanceProf().getExcavation();
                else if (data.isWoodcutting(mat) && inHand.contains("_AXE"))
                    deduction = ExperienceAPI.getLevel(player, "WOODCUTTING") *
                            data.getChanceProf().getWoodcutting();
                else if (data.isFarming(mat))
                    deduction = ExperienceAPI.getLevel(player, "HERBALISM") *
                            data.getChanceProf().getFarming();
                else
                    return deduction;
            } catch (McMMOPlayerNotFoundException ex) {
                ex.printStackTrace();
                return deduction;
            }
        } else
            deduction = ExperienceAPI.getLevel(player, "FISHING") * data.getChanceProf().getFishing();


        return deduction;
    }
}
