package com.raiya.frontend;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.awt.*;

public class ClassObject {
    public float x;
    public float y;
    public float width;
    public float height;
    public float speed;
    public Color color;

    public ClassObject(float x, float y, float width, float height, float speed, Color color){
        this.height = height;
        this.speed = speed;
        this.width = width;
        this.x = x;
        this.y = y;
        this.color = color;
    }
    @Override
    public void update(float delta) {
        this.y -= speed * delta;
    }

    public float getX() {return x;}
    public void setX(float x) {
        this.x = x;
    }
    public float getY() {return y;}
    public void setY(float y) {
        this.y = y;
    }
    public Color getColor() {return color;}
    public void setColor(Color color) {
        this.color = color;
    }
    public void setWidth(float width) {
        if (width > 0) this.width = width;
    }
    public void setHeight(float height) {
        if (height > 0) this.height = height;
    }
    public void setSpeed(float speed) {
        if (speed >= 0) this.speed = speed;
    }

    public void render(ShapeRenderer shapeRenderer){
        shapeRenderer.rect(x, y, width, height, color, color, color, color);

    }


}
