package com.oneshotmc.rewardsplus.essentials;

import com.google.common.collect.Maps;
import com.oneshotmc.rewardsplus.RewardsPlus;
import com.oneshotmc.rewardsplus.util.PluginUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

/**
 * Created by Nick on 2017-07-05.
 */
public abstract class GUI implements Listener{

    private UUID uuid;
    private String title;
    private Integer size;
    private boolean cancelClick;
    private boolean input;

    private Inventory inv;

    private Map<Integer, ItemStack> optionItems = Maps.newHashMap();

    public GUI(Player player, String title, int rows, boolean cancelClick, boolean input){
        this.uuid = player.getUniqueId();
        this.title = title;
        this.size = (rows > 6 ? 6 : rows)*9;
        this.cancelClick = cancelClick;
        this.input = input;
        inv = Bukkit.createInventory(null, size, PluginUtil.enableCc(title));
    }

    public Player getPlayer(){
        return Bukkit.getPlayer(uuid);
    }

    public void open(){
        getPlayer().openInventory(inv);
    }

    public void set(int slot, ItemStack item){
        this.optionItems.put(slot, item);
        this.inv.setItem(slot, item);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event){
        if(event.getWhoClicked().getUniqueId() != uuid || !event.getInventory().getTitle().equals(title))
            return;

        boolean contains = optionItems.containsKey(event.getRawSlot());

        if(contains && cancelClick){
            event.setCancelled(true);
        }else if(!contains && !input){
            event.setCancelled(true);
        }else{
            return;
        }

        GUIClickEvent(event.getCurrentItem(), event.getRawSlot(), (Player)event.getWhoClicked());
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event){
        if(event.getPlayer().getUniqueId() != uuid || !event.getInventory().getTitle().equals(title))
            return;

        List<ItemStack> content = new ArrayList<>();
        for(ItemStack item: event.getInventory()){
            if(item == null)
                continue;

            content.add(item);
        }

        GUICloseEvent((Player)event.getPlayer(), content);
        destroy();
    }

    public void register(){
        RewardsPlus.getInstance().register(this);
    }

    private void destroy(){
        HandlerList.unregisterAll(this);
        this.uuid = null;
        this.title = null;
        this.size = null;
        this.inv = null;
        this.optionItems = null;
    }

    public abstract void GUIClickEvent(ItemStack clickedItem, int rawSlot, Player clicker);

    public abstract void GUICloseEvent(Player player, List<ItemStack> content);

}
