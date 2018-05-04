package com.oneshotmc.rewardsplus.util;

import com.google.common.collect.Maps;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

import static com.oneshotmc.rewardsplus.util.InventoryUtil.Result.ADDED;
import static com.oneshotmc.rewardsplus.util.InventoryUtil.Result.DROPPED;

/**
 * Created by Nick on 2017-07-05.
 * Supports merging items.
 */
public class InventoryUtil {
    public enum Result{
        ADDED, DROPPED, PARTIALLY_DROPPED;
    }

    private Player player;

    public InventoryUtil(Player player){
        this.player = player;
    }

    private boolean addToFreeSlot(Inventory inv, ItemStack itemStack){
        for(int i=0; i<inv.getSize(); i++){
            ItemStack item = inv.getItem(i);
            if(item == null) {
                inv.setItem(i, itemStack);
                return true;
            }
        }

        return false;
    }


    public Result addItem(ItemStack itemStack){
        Inventory inv = player.getInventory();

        // Check if inventory has a free slot
        if(inv.firstEmpty() != -1) {
            if(itemStack.getType() == Material.MOB_SPAWNER || itemStack.getType() == Material.POTION
                    || itemStack.getType() == Material.BOOK){
                if(addToFreeSlot(inv, itemStack))
                    return ADDED;
                else{
                    player.getWorld().dropItem(player.getLocation(), itemStack);
                    return DROPPED;
                }
            }
            inv.addItem(itemStack);
            return ADDED;
        }

        if(itemStack.getType() == Material.MOB_SPAWNER || itemStack.getType() == Material.POTION
                || itemStack.getType() == Material.BOOK){
            player.getWorld().dropItem(player.getLocation(), itemStack);
            return DROPPED;
        }

        // if not continue

        int toAdd = itemStack.getAmount();

        Map<ItemStack, Integer> similar = Maps.newHashMap();

        for(ItemStack item: inv){
            if(item == null){
                continue;
            }
            if(item.isSimilar(itemStack) && item.getAmount() < itemStack.getMaxStackSize()){
                similar.put(item, item.getAmount());
            }
        }

        if(similar.size() == 0){
            player.getWorld().dropItem(player.getLocation(), itemStack);
            return DROPPED;
        }

        Integer addAble = 0;

        for(Integer i: similar.values()){
            addAble+=itemStack.getMaxStackSize()-i;
        }

        // addAble is how much we can add

        int amount = addAble < toAdd ? addAble : toAdd;

        if(amount > 0) {
            ItemStack result = itemStack.clone();
            result.setAmount(amount);
            inv.addItem(result);
        }

        if(itemStack.getAmount() == amount){
            return ADDED;
        }

        ItemStack toGive = itemStack.clone();
        toGive.setAmount(toAdd-addAble);

        player.getWorld().dropItem(player.getLocation(), toGive);
        return toGive.getAmount() != toAdd ? Result.PARTIALLY_DROPPED : DROPPED;
    }
}
