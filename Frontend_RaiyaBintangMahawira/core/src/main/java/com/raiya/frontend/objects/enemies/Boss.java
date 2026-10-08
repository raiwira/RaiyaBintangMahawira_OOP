package com.raiya.frontend.objects.enemies;

import com.badlogic.gdx.graphics.Color;
import com.raiya.frontend.objects.Collidable;
import com.raiya.frontend.objects.Enemy;
import com.raiya.frontend.objects.Player;

public class Boss extends Enemy {

    public Boss(String name, int hp) {
        super(380, 400, 48, 48, Color.BLUE, name, hp, 5000L);
    }

    // The type of inheritance formed by the GameObject → Enemy → Fairy/Boss relationship is Multilevel Inheritance.
    public Boss(float x, float y, String name, int hp) {
        super(x, y, 48, 48, Color.BLUE, name, hp, 5000L);
    }

    @Override
    public void onCollision(Collidable other) {
        // TODO: Check whether the other received by this method is a Player
        // TODO: Print "Player touches boss"
        if (other instanceof Player) {
            System.out.println("Player touches boss");
        }
    }


}
