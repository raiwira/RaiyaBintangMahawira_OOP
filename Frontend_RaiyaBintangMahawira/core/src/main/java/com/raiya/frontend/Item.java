package com.raiya.frontend;

import com.badlogic.gdx.graphics.Color;

public class Item extends ClassObject {
    private String itemType;
    private long scoreValue;

    public Item(float x, float y, String itemType) {
        super(x, y, 16, 16, 100f, Color.WHITE, 1000L);

    }
    public Item(float x, float y, float width, float height, float speed, String itemType) {
        super(x, y, width, height, speed, Color.WHITE);

    }
    public Item(float x, float y, float width, float height, float speed, String itemType, long scoreValue) {
        super(x, y, width, height, speed, Color.WHITE);

    }
    @Override
    public void update(float delta) {
        this.y -= speed * delta;
    }

    public void collectItem(Item item) {
        System.out.println(getName() + " collected " + item.getItemType() + "!");
        if (item.getScoreValue() > 0) {
            addScore(item.getScoreValue());
        }
    }




}
