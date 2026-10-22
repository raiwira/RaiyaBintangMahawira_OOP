package com.raiya.frontend;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
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

    public Main() {
    }

    public void create() {
        this.shapeRenderer = new ShapeRenderer();
        this.batch = new SpriteBatch();
        this.fairy = new ArrayList();
        this.entities = new ArrayList();
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

    public void render() {
        float delta = Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyJustPressed(54)) {
            this.entities.add(this.player.shootBullet());
        }

        for(GameObject obj : this.entities) {
            obj.update(delta);
        }

        for(int i = 0; i < this.entities.size(); ++i) {
            for(int j = i + 1; j < this.entities.size(); ++j) {
                GameObject a = (GameObject)this.entities.get(i);
                GameObject b = (GameObject)this.entities.get(j);
                if (!a.isDestroyed() && !b.isDestroyed() && a.getCoreHitbox().overlaps(b.getCoreHitbox())) {
                    a.onCollision(b);
                    b.onCollision(a);
                }
            }
        }

        ScreenUtils.clear(0.1F, 0.1F, 0.15F, 1.0F);
        this.batch.begin();

        for(GameObject entity : this.entities) {
            if (!entity.isDestroyed()) {
                entity.render(this.batch);
            }
        }

        this.batch.end();
    }

    public void dispose() {
        if (this.batch != null) {
            this.batch.dispose();
        }

        AssetManager.getInstance().dispose();
    }
}
