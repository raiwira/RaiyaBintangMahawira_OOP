package com.raiya.frontend;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import java.util.ArrayList;
import java.util.List;

import com.raiya.frontend.objects.GameObject;
import com.raiya.frontend.objects.Player;
import com.raiya.frontend.objects.enemies.Boss;
import com.raiya.frontend.objects.enemies.Fairy;
import com.raiya.frontend.objects.items.Item;
import com.raiya.frontend.objects.items.ItemType;
import com.raiya.frontend.systems.AssetManager;
import com.raiya.frontend.systems.EntityFactory;

public class Main extends ApplicationAdapter {
    private ShapeRenderer shapeRenderer;
    private SpriteBatch batch;
    private List<GameObject> entities;

    private Player player;
    private List<Fairy> fairy;
    private Boss boss;
    private Item pointItem;
    private Item powerItem;

    @Override
    public void create() {
        shapeRenderer = new ShapeRenderer();

        // TODO 1: Create a SpriteBatch and store it in batch
        batch = new SpriteBatch();

        // TODO 2: Initialize the fairy and entities lists as empty ArrayLists
        fairy = new ArrayList<>();
        entities = new ArrayList<>();

        // TODO 3: Get the AssetManager instance and call init()
        AssetManager.getInstance().init();

        // TODO 4: Update how all entities are created! Follow the table and create using EntityFactory
        player = EntityFactory.createPlayer(280, 40, "Reimu Hakurei", 100, 15, 3);

        Fairy redFairy = EntityFactory.createFairy(150, 380, "Red Fairy", 20);
        Fairy blueFairy = EntityFactory.createFairy(250, 380, "Blue Fairy", 20, "fairy_idle_blue");
        fairy.add(redFairy);
        fairy.add(blueFairy);

        boss = EntityFactory.createBoss(380, 400, "Rumia", 150) ;

        powerItem = EntityFactory.createItem(200, 450, ItemType.POWER);
        pointItem = EntityFactory.createItem(320, 480, ItemType.POINT);

        // TODO 5: Add all the objects you have just created to entities
        entities.add(player);
        entities.add(redFairy);
        entities.add(blueFairy);
        entities.add(boss);
        entities.add(powerItem);
        entities.add(pointItem);
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();

        for (GameObject obj : entities) {
            obj.update(delta);
        }

        // AABB Collision detection
        for (int i = 0; i < entities.size(); i++) {
            for (int j = i + 1; j < entities.size(); j++) {
                GameObject a = entities.get(i);
                GameObject b = entities.get(j);

                if (a.getCoreHitbox().overlaps(b.getCoreHitbox())) {
                    a.onCollision(b);
                    b.onCollision(a);
                }
            }
        }

        ScreenUtils.clear(0.1f, 0.1f, 0.15f, 1f);

        // Rendering Sprites using SpriteBatch
        batch.begin();
        for (GameObject entity : entities) {
            if (!entity.isDestroyed()) {
                // TODO: Call each entity's .render() method with the SpriteBatch as its argument.
                entity.render(batch);
            }
        }
        batch.end();
    }

    @Override
    public void dispose() {
        if (batch != null) {
            batch.dispose();
        }

        // TODO: Call dispose on AssetManager to release the loaded Textures as well.
        AssetManager.getInstance().dispose();
    }

}
