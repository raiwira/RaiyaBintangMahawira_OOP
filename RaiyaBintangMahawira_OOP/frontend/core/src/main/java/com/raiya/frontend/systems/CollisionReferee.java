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

        for (Bullet bullet : bulletManager.getActivePlayerBullets()) {
            if (bullet.isDestroyed()) continue;
            for (GameObject entity : entities) {
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
        // 2. Mediator checks Enemy Bullets vs Player (Core Hitbox vs Graze Hitbox)
        for (Bullet bullet : bulletManager.getActiveEnemyBullets()) {
            if (bullet.isDestroyed()) continue;

            if (!player.isDestroyed()) {
                // Priority A: Direct Core Hitbox Collision (Damage & Life Loss)
                if (bullet.getCoreHitbox().overlaps(player.getCoreHitbox())) {
                    // TODO 1: Wrap the contents of this block with an `if (!player.isInvulnerable())` check,
                    if (!player.isInvulnerable()) {
                        System.out.println("[CollisionReferee] CORE HIT! Enemy Bullet hit " + player.getName() + " for " + bullet.getDamage() + " DMG!");
                        // TODO 2: Replace `player.takeDamage(bullet.getDamage())` with `player.takeDamage(100, bulletManager, entities)`
                        player.takeDamage(100, bulletManager, entities);
                        bullet.destroy(); // Mark bullet for recycling by BulletManager
                        break;
                    }
                }
                // Priority B: Graze Hitbox Collision (Passes close to player without hitting core)
                else if (!bullet.hasBeenGrazed() && bullet.getCoreHitbox().overlaps(player.getGrazeHitbox())) {
                    bullet.setGrazed(true);
                    // TODO 4: Replace player.addScore(50) with a call to the player's addGraze() method
                    player.addGraze();
                    System.out.println("[CollisionReferee] GRAZE! " + player.getName() + " grazed an enemy bullet (+50 pts)!");
                }
            }
        }
        // 3. Mediator checks Player vs Items and Enemies (Core vs Graze)
        for (GameObject entity : entities) {
            if (entity.isDestroyed()) continue;
            //   - CASE A: Item Collection
            //     - Check whether entity is an instance of Item item
            if (entity instanceof Item item) {
                if (item.getCoreHitbox().overlaps(player.getCoreHitbox()) || item.getCoreHitbox().overlaps(player.getGrazeHitbox())) {
                    player.collectItem(item);
                }
            } else if (entity instanceof Enemy enemy) {
                if (player.getCoreHitbox().overlaps(enemy.getCoreHitbox())) {
                    if (!player.isInvulnerable()) {
                        // TODO 5: Wrap the contents of this block with `if (!player.isInvulnerable())`
                        System.out.println("[CollisionReferee] CORE COLLISION! Player collided with " + enemy.getName() + "!");
                        // TODO 6: Replace `player.takeDamage(10)` with `player.takeDamage(100, bulletManager, entities)`
                        player.takeDamage(100, bulletManager, entities);
                    }
                }
            }
        }


        entities.addAll(newDrops);
    }
}

