package com.raiya.frontend;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.raiya.frontend.objects.GameObject;
import com.raiya.frontend.objects.Player;
import com.raiya.frontend.objects.enemies.Boss;
import com.raiya.frontend.objects.enemies.Fairy;
import com.raiya.frontend.objects.items.Item;
import com.raiya.frontend.objects.items.ItemType;
import com.raiya.frontend.systems.AssetManager;
import com.raiya.frontend.systems.BulletManager;
import com.raiya.frontend.systems.CollisionReferee;
import com.raiya.frontend.systems.EntityFactory;
import java.util.ArrayList;
import java.util.List;

public class Main extends ApplicationAdapter {
    private ShapeRenderer shapeRenderer;
    private SpriteBatch batch;
    private List<GameObject> entities;
    private Player player;
    private List<Fairy> fairy;
    private Boss boss;
    private Item pointItem;
    private Item powerItem;
    // TODO: Create a private field to store BulletManager
    private BulletManager bulletManager;
    // TODO: Create a private field to store CollisionReferee
    private CollisionReferee CollisionReferee;
    private float enemyShootTimer = 0f;

    public Main() {
    }

    public void create() {
        this.shapeRenderer = new ShapeRenderer();
        this.batch = new SpriteBatch();
        this.fairy = new ArrayList();
        this.entities = new ArrayList();
        this.bulletManager = new BulletManager();

        AssetManager.getInstance().init();
        AssetManager.getInstance().playMusic("bgm", true);
        this.player = EntityFactory.createPlayer(280.0F, 40.0F, "Reimu Hakurei", 100, 15, 3);
        Fairy redFairy = EntityFactory.createFairy(150.0F, 380.0F, "Red Fairy", 20);
        Fairy blueFairy = EntityFactory.createFairy(250.0F, 380.0F, "Blue Fairy", 20, "fairy_idle_blue");
        this.fairy.add(redFairy);
        this.fairy.add(blueFairy);
        this.boss = EntityFactory.createBoss(380.0F, 400.0F, "Rumia", 150);
        this.powerItem = EntityFactory.createItem(200.0F, 450.0F, ItemType.POWER);
        this.pointItem = EntityFactory.createItem(320.0F, 480.0F, ItemType.POINT);
        this.entities.add(this.player);
        this.entities.add(redFairy);
        this.entities.add(blueFairy);
        this.entities.add(this.boss);
        this.entities.add(this.powerItem);
        this.entities.add(this.pointItem);
    }

    private void updateEnemyScheduler(float delta) {
        enemyShootTimer += delta;
        if (enemyShootTimer >= 1.5f) {
            boss.shootBullet(bulletManager);
            enemyShootTimer = 0f;
        }
    }


    // ...

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();

        // TODO: Create player input. If key Z is pressed call
        // player.shootBullet(bulletManager);
        if (Gdx.input.isKeyJustPressed(Input.Keys.Z)){
            player.shootBullet(bulletManager);
            }

        // TODO: Periodic firing scheduler for Boss Rumia
        updateEnemyScheduler(delta);

        // TODO: Generic update & clean for standard entities (Player, Enemy, Item)
        updateAndClean(entities, delta, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // TODO: Update and recycle Object Pool

        // TODO: Evaluate & resolve all collisions via
        // Mediator Pattern

        // TODO: Clear screen
        ScreenUtils.clear(0.1f, 0.1f, 0.15f, 1f);

        batch.begin();
        for (GameObject entity : entities) {
            if (!entity.isDestroyed()) {
                entity.render(batch);
            }
        }

        // TODO: Render all active bullets from the pool

        batch.end();
    }

// ...


    public void dispose() {
        if (this.batch != null) {
            this.batch.dispose();
        }

        AssetManager.getInstance().dispose();
    }
}
