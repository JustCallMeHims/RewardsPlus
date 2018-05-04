package com.oneshotmc.rewardsplus.exceptions;

/**
 * Created by Michel on 2017-07-05.
 */
public class TierChanceTooLowException extends Exception {

    public TierChanceTooLowException(){
        super("Tier chance cannot be lower than 3");
    }
}
