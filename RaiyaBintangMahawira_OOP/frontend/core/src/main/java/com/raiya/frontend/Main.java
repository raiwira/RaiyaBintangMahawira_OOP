package com.raiya.frontend;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.raiya.frontend.commands.Command;
import com.raiya.frontend.commands.InputHandler;
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
import com.raiya.frontend.ui.GameHUD;

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
    private BulletManager bulletManager;
    private CollisionReferee collisionReferee;
    private float enemyShootTimer = 0f;
    // TODO: Declare private fields InputHandler inputHandler and GameHUD gameHUD
    private InputHandler inputHandler;
    private GameHUD gameHUD;

    @Override
    public void create() {
        batch = new SpriteBatch();
        fairy = new ArrayList<>();
        entities = new ArrayList<>();

        // Instantiate systems
        bulletManager = new BulletManager();
        collisionReferee = new CollisionReferee();
        // TODO: Instantiate new objects for inputHandler and gameHUD
        inputHandler = new InputHandler();
        gameHUD = new GameHUD();

        // 1. Centralized Asset Registration (AssetManager Singleton + Flyweight)
        AssetManager.getInstance().init();
        AssetManager.getInstance().playMusic("bgm", true); // Plays Rumia's theme on loop

        // 2. Instantiate entities via Factory Pattern (EntityFactory)
        player = EntityFactory.createPlayer(200, 50, "Reimu Hakurei", 100, 15, 3);
        // TODO: Register gameHUD as an Observer on the player object!
        // Hint: player.registerObserver(gameHUD);
        player.registerObserver(gameHUD);

        fairy.add(EntityFactory.createFairy(150, 380, "Red Fairy", 20));
        fairy.add(EntityFactory.createFairy(250, 380, "Blue Fairy", 20, "fairy_idle_blue"));
        boss = EntityFactory.createBoss(250, 400, "Rumia", 150);
        powerItem = EntityFactory.createItem(180, 450, ItemType.POWER);
        pointItem = EntityFactory.createItem(220, 480, ItemType.POINT);

        entities.add(player);
        entities.addAll(fairy);
        entities.add(boss);
        entities.add(powerItem);
        entities.add(pointItem);

        // TODO: Register the entities list to inputHandler for bullet conversion during a Bomb!
        // Hint: inputHandler.setEntities(entities);
        inputHandler.setEntities(entities);
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

    private void restartGame() {
        System.out.println("[Main] Restarting game...");
        entities.clear();
        bulletManager.clearEnemyBullets();
        bulletManager.clearPlayerBullets();
        entities.add(player);
        player.resetGame();

        fairy.clear();
        fairy.add(EntityFactory.createFairy(150, 380, "Red Fairy", 20));
        fairy.add(EntityFactory.createFairy(250, 380, "Blue Fairy", 20, "fairy_idle_blue"));
        boss = EntityFactory.createBoss(250, 400, "Rumia", 150);
        powerItem = EntityFactory.createItem(180, 450, ItemType.POWER);
        pointItem = EntityFactory.createItem(220, 480, ItemType.POINT);

        entities.addAll(fairy);
        entities.add(boss);
        entities.add(powerItem);
        entities.add(pointItem);
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();

        // 0. Restart Game on 'R' key when Game Over
        // TODO: Check if Gdx.input != null AND the R key was just pressed (isKeyJustPressed(Input.Keys.R))
        //       AND player.isGameOver() is true: call restartGame();
        if (Gdx.input != null && Gdx.input.isKeyJustPressed(Input.Keys.R) && player.isGameOver()){
            restartGame();
        }

        // 1. Process all player inputs through the Command Pattern (Invoker Command Stream)
        // TODO:
        // - Retrieve the command stream: List<Command> commands = inputHandler.handleInput(delta);
        List<Command> commands = inputHandler.handleInput(delta);
        // - Loop through each command in commands and call: command.execute(player, bulletManager);
        for (Command command : commands) {
            command.execute(player, bulletManager);
        }

        // 2. Boss periodic shooting scheduler
        updateEnemyScheduler(delta);

        // 3. Generic update & clean for standard entities within Playfield bounds (416, 560)
        updateAndClean(entities, delta, 416, 560);

        // 4. Update and recycle Object Pool within Playfield bounds (416, 560)
        bulletManager.update(delta, 416, 560);

        // 5. Evaluate collisions through the Mediator Pattern (CollisionReferee)
        collisionReferee.resolveCollisions(player, entities, bulletManager);

        // 6. Clear the screen
        ScreenUtils.clear(0.1f, 0.1f, 0.15f, 1f);

        // 7. Render active entity sprites, pooled bullets, and GameHUD Observer text
        batch.begin();
        for (GameObject entity : entities) {
            if (!entity.isDestroyed()) {
                entity.render(batch);
            }
        }
        bulletManager.render(batch);
        // TODO: Call gameHUD's render method with batch as parameter
        gameHUD.render(batch);
        batch.end();

        // 8. Render UI border frame lines & Focus Mode Core Hitbox indicator (Drawn ON TOP of sprites!)
        // TODO: Call gameHUD.renderBackground()
        gameHUD.renderBackground();
        // TODO: Call player.renderFocusIndicator(gameHUD.getShapeRenderer())
        player.renderFocusIndicator(gameHUD.getShapeRenderer());
    }

    @Override
    public void dispose() {
        if (batch != null) { batch.dispose(); }
        // TODO: If gameHUD is not null, call gameHUD.dispose()
        if (gameHUD != null) { gameHUD.dispose(); }
        AssetManager.getInstance().dispose();
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
}
