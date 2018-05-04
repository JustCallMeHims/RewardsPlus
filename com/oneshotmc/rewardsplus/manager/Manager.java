package com.oneshotmc.rewardsplus.manager;

import com.oneshotmc.rewardsplus.RewardsPlus;

/**
 * Created by Michel on 2017-07-06.
 */
public interface Manager<T> {

    boolean add(T t);
    boolean del(T t);
    T getById(int id);
    T getByName(String name);

    /*
    I don't really like this, but oh well.
     */
    static Manager getManager(String manager){
        if(manager.equalsIgnoreCase("tier"))
            return RewardsPlus.getInstance().getTierMngr();
        else if(manager.equalsIgnoreCase("reward"))
            return RewardsPlus.getInstance().getRewardMngr();

        return null;
    }
}
