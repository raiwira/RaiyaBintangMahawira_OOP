package com.raiya.frontend.objects.enemies;

import com.badlogic.gdx.graphics.Color;
import com.raiya.frontend.objects.Collidable;
import com.raiya.frontend.objects.Enemy;
import com.raiya.frontend.objects.Player;
import com.raiya.frontend.objects.items.Item;

public class Fairy extends Enemy {

    public Fairy(String name, int hp) {
        super(150, 380, 24, 24, Color.PINK, name, hp, 500L);
    }

    public Fairy(float x, float y, String name, int hp) {
        super(x, y, 24, 24, Color.PINK, name, hp, 500L);
    }
    @Override
    public void onCollision(Collidable other) {
        // TODO: Check whether the other received by this method is a Player
        // TODO: Print "Player touches fairy")
        if (other instanceof Player) {
            System.out.println("Player touches fairy");
        }
    }
}
