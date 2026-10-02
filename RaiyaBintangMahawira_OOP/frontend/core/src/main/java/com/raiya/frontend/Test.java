package com.raiya.frontend;

import com.raiya.frontend.objects.Player;
import com.raiya.frontend.objects.bullets.Bullet;

public class Test {
    public static void main(String[] args) {
        // --- SANITY CHECK FOR PRE-CS MODULE 4 ---
        Player reimu = new Player("Reimu Hakurei", 100, 15, 3);
        Bullet bullet = reimu.shootBullet();

        System.out.println("Bullet created at: (" + bullet.getX() + ", " + bullet.getY() + ") | Damage: " + bullet.getDamage());

        bullet.update(0.1f);
        System.out.println("Bullet Y after 0.1s: " + bullet.getY());

        System.out.println("Is bullet off screen? " + bullet.isOffScreen(640, 480));
    }
}
