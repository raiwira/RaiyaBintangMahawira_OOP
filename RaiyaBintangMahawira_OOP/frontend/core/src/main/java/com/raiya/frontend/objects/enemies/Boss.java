package com.raiya.frontend.objects.enemies;

import com.badlogic.gdx.graphics.Color;
import com.raiya.frontend.objects.Collidable;
import com.raiya.frontend.objects.Player;
import com.raiya.frontend.objects.items.Item;
import com.raiya.frontend.objects.patterns.ShootingPattern;
import com.raiya.frontend.objects.patterns.shootingStrategy.SpreadShot;
import com.raiya.frontend.systems.BulletManager;

public class Boss extends Enemy {
    // TODO: Declare a private field named `shootingPattern` of type ShootingPattern
    //       and immediately initialize it with the `SpreadShot` we just created.
    //       Make sure speed is 200f, spread angle is 30f, and 3 bullets (num) are fired.
    private ShootingPattern shootingPattern = new SpreadShot(200f, 3, 3);

    // Existing Boss constructors you already made before. (Don't touch these!)
    public Boss(String name, int hp) {
        super(380, 400, 64, 64, Color.BLUE, name, hp, 5000L);
    }

    public Boss(float x, float y, String name, int hp) {
        super(x, y, 64, 64, Color.BLUE, name, hp, 5000L);
    }

    // TODO: Create the shootBullet(BulletManager bulletManager) method:
    public void shootBullet(BulletManager bulletManager) {
        // If shootingPattern is not null and the boss hasn't been destroyed,
        if (bulletManager != null && shootingPattern != null && !isDestroyed()) {
            // execute shootingPattern at the boss's center cone coordinates (x + width / 2 - 8, y).
            int damage = 10;
            shootingPattern.execute(x + (width / 2f) - 8f, y, bulletManager, false);
        }
    }

    // TODO: Provide a getter and setter for shootingPattern
    public ShootingPattern getShootingPattern() {
        return shootingPattern;
    }

    public void setShootingPattern(ShootingPattern shootingPattern) {
        this.shootingPattern = shootingPattern;
    }
}
