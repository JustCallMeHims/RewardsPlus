package com.oneshotmc.rewardsplus.util;

import com.oneshotmc.rewardsplus.RewardsPlus;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Nick on 2017-07-05.
 */
public class PluginUtil {

    /*
    Enable color code
     */
    public static String enableCc(String s){
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    /*
    Send message to ... not limited to player.
     */
    public static void sendMsg(CommandSender sender, String msg){
        sender.sendMessage(msg);
    }

    public static String arrayIntoString(String[] args, int startIndex){
        StringBuilder sb = new StringBuilder();

        if(startIndex > args.length)
            return "";

        for(int i=startIndex; i<args.length; i++){
            sb.append(args[i] + (i==args.length-1 ? "" : " "));
        }

        return sb.toString();
    }

    public static boolean isOld(){
        Server server = RewardsPlus.getInstance().getServer();
        return server.getVersion().contains("1.8") || server.getVersion().contains("1.7");
    }

    public static void playSound(Player player){
        Sound sound = RewardsPlus.getInstance().getData().getSound();
        if(sound != null)
            player.playSound(player.getLocation(), sound, 1, 1);
    }
}
