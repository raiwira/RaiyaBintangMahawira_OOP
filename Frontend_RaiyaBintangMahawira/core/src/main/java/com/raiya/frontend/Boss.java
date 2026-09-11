package com.raiya.frontend;

import java.awt.*;

public class Boss extends Enemy{
    public Boss(String name, int hp){
        super(380, 400, 48, 48, Color.BLUE, name, hp, 5000L);
    }
    //type of inheritance is formed by the GameObject → Enemy → Fairy/Boss relationship is multilevel.
    public Boss(float x, float y, String name, int hp){

    }



}
