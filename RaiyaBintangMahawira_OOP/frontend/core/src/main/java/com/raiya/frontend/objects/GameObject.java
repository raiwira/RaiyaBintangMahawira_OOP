package com.raiya.frontend.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public abstract class GameObject implements Collidable {
    protected float x, y;
    protected float width, height;
    protected float speed;
    protected Color color;
    protected boolean active = true;

    protected TextureRegion sprite;
    protected Animation<TextureRegion> animation;
    protected float stateTime = 0f;

    public GameObject(float x, float y, float width, float height, float speed, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.color = color;
    }

    public void update(float delta) {
        // TODO: Increase the object's internal time so its animation advances
        this.stateTime += delta;
    }

    public void render(SpriteBatch batch) {
        if (batch != null && active) {
            if (animation != null) {
                TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);
                batch.draw(currentFrame, x, y, width, height);
            } else if (sprite != null) {
                batch.draw(sprite, x, y, width, height);
            }
        }
    }

    public void render(ShapeRenderer shapeRenderer) {
        // Only render if the object is still active
        if (shapeRenderer != null && color != null && active) {
            shapeRenderer.setColor(color);
            shapeRenderer.rect(x, y, width, height);
        }
    }

    public boolean isDestroyed() {
        return !active;
    }

    public void destroy() {
        this.active = false;
    }

    public boolean isOffScreen(float screenWidth, float screenHeight) {
        // Use a 50px tolerance margin on each side
        return x < -50 || x > screenWidth + 50 || y < -50 || y > screenHeight + 50;
    }

    @Override
    public Rectangle getCoreHitbox() {
        return new Rectangle(x, y, width, height);
    }

    @Override
    public Rectangle getGrazeHitbox() {
        return new Rectangle(x - 10, y - 10, width + 20, height + 20);
    }

    @Override
    public void onCollision(Collidable other) {
        // Base collision handler
    }

    // Encapsulation: Getters and Setters
    public TextureRegion getSprite() {
        return sprite;
    }

    public void setSprite(TextureRegion sprite) {
        this.sprite = sprite;
    }

    public Animation<TextureRegion> getAnimation() {
        return animation;
    }

    public void setAnimation(Animation<TextureRegion> animation) {
        this.animation = animation;
    }
    public float getX() { return x; }
    public void setX(float x) { this.x = x; }

    public float getY() { return y; }
    public void setY(float y) { this.y = y; }

    public float getWidth() { return width; }
    public void setWidth(float width) { if (width > 0) this.width = width; }

    public float getHeight() { return height; }
    public void setHeight(float height) { if (height > 0) this.height = height; }

    public float getSpeed() { return speed; }
    public void setSpeed(float speed) { if (speed >= 0) this.speed = speed; }

    public Color getColor() { return color; }
    public void setColor(Color color) { this.color = color; }
}
