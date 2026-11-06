package com.raiya.frontend.systems;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.raiya.frontend.objects.GameObject;
import com.raiya.frontend.objects.bullets.Bullet;
import com.raiya.frontend.objects.bullets.BulletType;
import com.raiya.frontend.objects.items.Item;
import com.raiya.frontend.objects.items.ItemType;
import com.raiya.frontend.systems.EntityFactory;

import java.util.*;

public class BulletManager {
    // 1. Pool Queues for Object Recycling (Object Pool Pattern)
    private Queue<Bullet> playerBulletPool;
    private Queue<Bullet> enemyBulletPool;

    // 2. Lists of Active Bullets on Screen
    private List<Bullet> activePlayerBullets;
    private List<Bullet> activeEnemyBullets;

    public BulletManager() {
        // TODO:
        // 1. Initialize playerBulletPool and enemyBulletPool as empty LinkedLists
        this.playerBulletPool = new LinkedList<>();
        this.enemyBulletPool = new LinkedList<>();
        // 2. Initialize activePlayerBullets and activeEnemyBullets as empty ArrayLists
        this.activePlayerBullets = new ArrayList<>();
        this.activeEnemyBullets = new ArrayList<>();
    }

    // Borrowing Player Bullet from Pool (5 Points)
    public Bullet spawnPlayerBullet(float x, float y, float vx, float vy, int damage) {
        Bullet bullet;
        // TODO:
        // 1. Check whether playerBulletPool is not empty.
        if (!playerBulletPool.isEmpty()){
            bullet = playerBulletPool.poll();
        } else {
            bullet = EntityFactory.createPlayerBullet(x, y, damage);
        }
        //    If so, poll from the pool. If not, create a new one using the Factory.
        // 2. Re-initialize the bullet.
        bullet.init(x, y, vx, vy, BulletType.AMULET, damage, true);
        activePlayerBullets.add(bullet);
        // 3. Add the bullet to the activePlayerBullets list.
        // 4. Return the bullet instance.
        return bullet;
    }

    // Borrowing Enemy Bullet from Pool (5 Points)
    public Bullet spawnEnemyBullet(float x, float y, float vx, float vy, int damage) {
        Bullet bullet;
        // TODO:
        // 1. Check whether enemyBulletPool is not empty:
        //    If so, poll from the pool. If not, create a new one using the Factory.
        if (!enemyBulletPool.isEmpty()) {
            bullet = enemyBulletPool.poll();
        } else {
            bullet = EntityFactory.createEnemyBullet(x, y, damage);
        }
        // 2. Re-initialize the bullet.
        bullet.init(x, y, vx, vy, BulletType.AMULET, damage, true);
        activeEnemyBullets.add(bullet);
        // 3. Add the bullet to the activeEnemyBullets list
        // 4. Return the bullet instance
        return bullet;
    }

    public void update(float delta, float screenWidth, float screenHeight) {
        // 1. Player bullet recycling & movement (5 Points)
        Iterator<Bullet> pIter = activePlayerBullets.iterator();
        while (pIter.hasNext()) {
            Bullet bullet = pIter.next();
            bullet.update(delta);

            if (bullet.isOffScreen(screenWidth, screenHeight) || bullet.isDestroyed()) {
                pIter.remove();                 // Remove safely during traversal
                bullet.destroy();               // Reset active flag
                playerBulletPool.offer(bullet); // Return to pool for reuse!
            }
        }
        // TODO:
        // - Copy-paste the iterator loop from updateAndClean() in Main.java
        // - Change the class referenced by the iterator to `Bullet` instead of T
        //   and change the referenced list to activePlayerBullets
        // - Change the check to whether the bullet is off-screen
        //   OR the bullet is already marked as destroyed
        //   - If YES:
        //     * Mark the bullet as destroyed (destroy)
        //     * Safely remove the bullet from the active bullet list
        //     * Return the bullet to the pool queue

        // 2. Enemy bullet recycling & movement (5 Points)
        // TODO:
        Iterator<Bullet> eIter = activeEnemyBullets.iterator();
        while (eIter.hasNext()) {
            Bullet bullet = eIter.next();
            bullet.update(delta);

            if (bullet.isOffScreen(screenWidth, screenHeight) || bullet.isDestroyed()) {
                eIter.remove();                 // Remove safely during traversal
                bullet.destroy();               // Reset active flag
                enemyBulletPool.offer(bullet); // Return to pool for reuse!
            }
        }
        // - Follow the exact same procedure as above for activeEnemyBullets
        // - Enemy bullets that go off-screen or are destroyed are returned to enemyBulletPool via offer()
    }

    public void render(SpriteBatch batch) {
        for (Bullet b : activePlayerBullets) {
            if (!b.isDestroyed()) b.render(batch);
        }
        for (Bullet b : activeEnemyBullets) {
            if (!b.isDestroyed()) b.render(batch);
        }
    }

    // Spell Card (Bomb) Execution: Clear all active enemy bullets and convert them into POINT item drops!
    public void clearEnemyBullets(List<GameObject> entities) {
        // 1. Create an Iterator<Bullet> to safely traverse activeEnemyBullets.
        Iterator<Bullet> eIter = activeEnemyBullets.iterator();

        // 2. While the iterator has a next element:
        while (eIter.hasNext()) {
            //    - Retrieve the current Bullet object.
            Bullet bullet = eIter.next();
            //    - If the entities parameter is not null:
            if (entities != null) {
                //      * Create an Item object of type ItemType.POINT using EntityFactory.createItem(...)
                //        at position (bullet.getX(), bullet.getY()), then add it to the entities list.
                Item pointItem = EntityFactory.createItem(bullet.getX(), bullet.getY(), ItemType.POINT);
                entities.add(pointItem);
            }
            //    - Mark the bullet as destroyed (bullet.destroy()).
            bullet.destroy();
            //    - Remove the bullet from activeEnemyBullets using remove() on the iterator from step 1.
            eIter.remove();
            //    - Return the bullet to the enemyBulletPool queue using offer().
            enemyBulletPool.offer(bullet);
        }
    }

    public void clearEnemyBullets() {
        clearEnemyBullets(null);
    }

    // Clear all player bullets when restarting the game (Copy-Paste):
    public void clearPlayerBullets() {
        Iterator<Bullet> pIter = activePlayerBullets.iterator();
        while (pIter.hasNext()) {
            Bullet bullet = pIter.next();
            bullet.destroy();
            pIter.remove();
            playerBulletPool.offer(bullet);
        }
    }

    // Helper methods to access pools and active lists
    public List<Bullet> getActivePlayerBullets() { return activePlayerBullets; }
    public List<Bullet> getActiveEnemyBullets() { return activeEnemyBullets; }
    public int getPlayerPoolSize() { return playerBulletPool.size(); }
    public int getEnemyPoolSize() { return enemyBulletPool.size(); }
}
