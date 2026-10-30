package com.raiya.frontend.systems;

import com.raiya.frontend.objects.GameObject;
import com.raiya.frontend.objects.Player;
import com.raiya.frontend.objects.bullets.Bullet;
import com.raiya.frontend.objects.enemies.Enemy;
import com.raiya.frontend.objects.items.Item;
import com.raiya.frontend.objects.items.ItemType;

import java.util.ArrayList;
import java.util.List;

public class CollisionReferee {

    public void resolveCollisions(Player player, List<GameObject> entities, BulletManager bulletManager) {
        if (player == null || entities == null || bulletManager == null) return;

        List<GameObject> newDrops = new ArrayList<>();

        // TODO Code for Question 3:
        // - Loop through each bullet in the ActivePlayerBullets list.
        //   - If the bullet is already destroyed, skip it (continue)
        for (Bullet bullet : bulletManager.getActivePlayerBullets()) {
            if (bullet.isDestroyed()) continue;
            //   - Loop through each entity in entities: (HINT: the `entities` variable is in the parameters above)
            for (GameObject entity : entities) {
                //     - Check whether entity is an instance of Enemy enemy and is not destroyed yet.
                if (entity instanceof Enemy enemy && !enemy.isDestroyed()) {
                    if (bullet.getCoreHitbox().overlaps(enemy.getCoreHitbox())) {
                        //     - If so, check whether the bullet's Core Hitbox collides with the enemy's Core Hitbox.
                        //     - If they collide:
                        //       1. Deal damage to the enemy: boolean defeated = enemy.takeDamage(bullet.getDamage());
                        boolean defeated = enemy.takeDamage(bullet.getDamage());
                        if (defeated) {
                            player.addScore(enemy.getScoreValue());
                            newDrops.add(EntityFactory.createItem(enemy.getX(), enemy.getY(), ItemType.POWER));
                        }
                        //          - Create a POWER item drop via the factory at the x, y coordinates where the enemy died,
                        //            then add that item to the newDrops list
                        //       3. Mark the bullet as destroyed so it will be recycled later by BulletManager
                        bullet.destroy();
                    }
                }
            }
        }
        //
        // 2. Evaluate Enemy Bullets vs Player (Core Hit vs Graze Hit)
        // TODO Code for Question 4:
        // - Loop through each bullet in ActiveEnemyBullets.
        //   - If the bullet is already destroyed, skip it (continue)
        // 2. Evaluate Enemy Bullets vs Player (Core Hit vs Graze Hit)
        for (Bullet bullet : bulletManager.getActiveEnemyBullets()) { // FIX 1: activeEnemyBullets
            if (bullet.isDestroyed()) continue;

            if (!player.isDestroyed()) { // FIX 2: Added "!" to check if player is ALIVE
                // PRIORITY 1: Core Hit (Player was Shot)
                if (bullet.getCoreHitbox().overlaps(player.getCoreHitbox())) {
                    player.takeDamage(bullet.getDamage());
                    bullet.destroy();
                }
                // PRIORITY 2: Graze Hit (Bullet Grazes Player)
                else if (!bullet.hasBeenGrazed() && bullet.getCoreHitbox().overlaps(player.getGrazeHitbox())){
                    bullet.setGrazed(true);
                    player.addScore(50);
                    System.out.println(player.getName() + " grazed an enemy bullet +50!");
                }
            }
        }
        // TODO Code for Question 5:
        for (GameObject entity : entities) {
            if (entity.isDestroyed()) continue;
            //   - CASE A: Item Collection
            //     - Check whether entity is an instance of Item item
            if (entity instanceof Item item) {
                if (item.getCoreHitbox().overlaps(player.getCoreHitbox()) || item.getCoreHitbox().overlaps(player.getGrazeHitbox())) {
                    player.collectItem(item);
                }
            }
            else if (entity instanceof Enemy enemy) {
                if (player.getCoreHitbox().overlaps(enemy.getCoreHitbox())) {
                    player.takeDamage(10);
                }
            }
            //   - CASE B: Physical Contact with Enemy
            //     - Check whether entity is an instance of Enemy enemy
            //     - If the player's Core Hitbox collides with the enemy's Core Hitbox.
            //     - If YES: deal 10 body collision damage to the player: player.takeDamage(10)
        }

        entities.addAll(newDrops);
    }
}

