package com.raiya.frontend;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture image;

    @Override
    public void create() {
        batch = new SpriteBatch();
        image = new Texture("libgdx.png");
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        batch.begin();
        batch.draw(image, 140, 210);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
    }
    package com.netlab.frontend;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import java.util.ArrayList;
import java.util.List;

    public class Main extends ApplicationAdapter {
        private ShapeRenderer shapeRenderer;

        // TODO 1: Declare fields for Player, Fairy, Boss, Items, and List<GameObject>


        @Override
        public void create() {
            shapeRenderer = new ShapeRenderer();
            gameObjects = new ArrayList<>();

            // TODO 2: Instantiate Player (Red square) at (280, 40)


            // TODO 3: Instantiate Fairy (Pink square) at (150, 380)


            // TODO 4: Instantiate Boss (Blue square) at (380, 400)


            // TODO 5: Instantiate Items (White squares) with downward speeds


            // TODO 6: Add all entities into the gameObjects list polymorphically

        }

        @Override
        public void render() {
            float delta = Gdx.graphics.getDeltaTime();

            // 1. Polymorphic Update Loop: Items move downward automatically via Item.update(delta)
            for (GameObject obj : gameObjects) {
                obj.update(delta);
            }

            // 2. Clear Screen
            ScreenUtils.clear(0.1f, 0.1f, 0.15f, 1f);

            // 3. Polymorphic Render Loop: Draw hitboxes with ShapeRenderer
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            for (GameObject obj : gameObjects) {
                obj.render(shapeRenderer);
            }
            shapeRenderer.end();
        }

        @Override
        public void dispose() {
            if (shapeRenderer != null) {
                shapeRenderer.dispose();
            }
        }
    }

}
