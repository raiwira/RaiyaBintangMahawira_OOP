package com.raiya.frontend;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.raiya.frontend.systems.AssetManager;
import com.raiya.frontend.systems.BulletManager;
import com.raiya.frontend.systems.CollisionReferee;
import com.raiya.frontend.systems.EntityFactory;
import com.raiya.frontend.objects.GameObject;
import com.raiya.frontend.objects.Player;
import com.raiya.frontend.objects.enemies.Boss;
import com.raiya.frontend.objects.enemies.Fairy;
import com.raiya.frontend.objects.items.Item;
import com.raiya.frontend.objects.items.ItemType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Main extends ApplicationAdapter {
    private SpriteBatch batch;

    private Player player;
    private List<Fairy> fairy;
    private Boss boss;
    private Item powerItem;
    private Item pointItem;
    private List<GameObject> entities;

    // CS Module 7: Systems
    private BulletManager bulletManager;
    private CollisionReferee collisionReferee;
    private float enemyShootTimer = 0f;

    @Override
    public void create() {
        batch = new SpriteBatch();
        fairy = new ArrayList<>();
        entities = new ArrayList<>();

        // Instantiate systems
        bulletManager = new BulletManager();
        collisionReferee = new CollisionReferee();

        // 1. Centralized Asset Registration (AssetManager Singleton + Flyweight)
        AssetManager.getInstance().init();
        AssetManager.getInstance().playMusic("bgm", true); // Plays Rumia's theme on loop

        // 2. Instantiate entities via Factory Pattern (EntityFactory)
        player = EntityFactory.createPlayer(280, 40, "Reimu Hakurei", 100, 15, 3);
        fairy.add(EntityFactory.createFairy(150, 380, "Red Fairy", 20));
        fairy.add(EntityFactory.createFairy(250, 380, "Blue Fairy", 20, "fairy_idle_blue"));
        boss = EntityFactory.createBoss(380, 400, "Rumia", 150);
        powerItem = EntityFactory.createItem(200, 450, ItemType.POWER);
        pointItem = EntityFactory.createItem(320, 480, ItemType.POINT);

        entities.add(player);
        entities.addAll(fairy);
        entities.add(boss);
        entities.add(powerItem);
        entities.add(pointItem);
    }

    private void updateEnemyScheduler(float delta) {
        enemyShootTimer += delta;
        if (enemyShootTimer >= 1.5f) {
            if (boss != null && !boss.isDestroyed()) {
                boss.shootBullet(bulletManager);
            }
            enemyShootTimer = 0f;
        }
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();

        // 1. Check Player Bullet shooting input (Key Z)
        if (Gdx.input.isKeyJustPressed(Input.Keys.Z)) {
            player.shootBullet(bulletManager);
        }

        // 2. Periodic firing scheduler for Boss Rumia
        updateEnemyScheduler(delta);

        // 3. Generic update & safe removal of standard entities (Player, Enemy, Item)
        updateAndClean(entities, delta, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // 4. Update and recycle Object Pool
        bulletManager.update(delta, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // 5. Evaluate & resolve all collisions via Mediator Pattern
        collisionReferee.resolveCollisions(player, entities, bulletManager);

        // 6. Clear screen
        ScreenUtils.clear(0.1f, 0.1f, 0.15f, 1f);

        // 7. Render active entities and pooled bullets with SpriteBatch
        batch.begin();

        for (GameObject entity : entities) {
            if (!entity.isDestroyed()) {
                entity.render(batch);
            }
        }

        // Render all active bullets from the pool
        bulletManager.render(batch);

        batch.end();
    }

    // Generic Instance Method with Bounded Type Parameter <T extends GameObject>
    public <T extends GameObject> void updateAndClean(List<T> list, float delta, float screenWidth, float screenHeight) {
        Iterator<T> iterator = list.iterator();
        while (iterator.hasNext()) {
            T entity = iterator.next();
            entity.update(delta);

            if (entity.isOffScreen(screenWidth, screenHeight) || entity.isDestroyed()) {
                System.out.println("Removed via Generic Iterator: " + entity.getClass().getSimpleName());
                iterator.remove(); // Safe removal using Iterator!
            }
        }
    }

    @Override
    public void dispose() {
        if (batch != null) {
            batch.dispose();
        }
        AssetManager.getInstance().dispose();
    }
}
