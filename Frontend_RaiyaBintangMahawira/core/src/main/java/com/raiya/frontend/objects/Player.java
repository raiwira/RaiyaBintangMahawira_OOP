package com.raiya.frontend.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.raiya.frontend.objects.items.Item;
import com.raiya.frontend.objects.Enemy;
import com.raiya.frontend.objects.items.ItemType;

public class Player extends GameObject {
    private String name;
    private int hp;
    private int power;
    private int spellCards;
    private long score;

    public Player(String name, int hp, int power, int spellCards) {
        super(280, 40, 32, 32, 200f, Color.RED);
        this.name = name;
        this.hp = hp;
        this.power = power;
        this.spellCards = spellCards;
        this.score = 0;
    }

    public Player(float x, float y, String name, int hp, int power, int spellCards) {
        super(x, y, 32, 32, 0, Color.RED);
        this.name = name;
        this.hp = hp;
        this.power = power;
        this.spellCards = spellCards;
        this.score = 0;
    }

    @Override
    public void update(float delta) {
        if (Gdx.input != null) {
            // TODO: Check W / UP input   → y += speed * delta
            if (Gdx.input.isKeyPressed(Input.Keys.Y) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
                y += speed * delta;
            }
            // TODO: Check S / DOWN input → y -= speed * delta
            if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                y += speed * delta;
            }
            // TODO: Check A / LEFT input → x -= speed * delta
            if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                y += speed * delta;
            }
            // TODO: Check D / RIGHT input → x += speed * delta
            if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                y += speed * delta;
            }
        }
    }

    @Override
    public void onCollision(Collidable other) {
        // TODO: Check whether the other received by this method is an Item
        // TODO: Print "Player touches items" then call collectItem((Item) other)
        if (other instanceof Item) {
            System.out.println("Player touches items");
            collectItem((Item) other);
        }
    }

    public void collectItem(Item item) {
        ItemType type = item.getItemTypeEnum();
        if (type != null) {
            switch (type) {
                case POWER -> {
                    // 1. Increase power by type.getPowerBonus() via this.power
                    this.power += type.getPowerBonus();
                    // 2. Add score by item.getScoreValue() via addScore() (addScore() already automatically prints "gained X pts!")
                    addScore(item.getScoreValue());
                    // 3. Print: [name] collected POWER item! Power increased to [power]
                    System.out.println(name + " collected POWER item! Power increased to " + this.power);
                }
                case POINT -> {
                    // 1. Add score by item.getScoreValue() via addScore()
                    addScore(item.getScoreValue());
                    // 2. Print: [name] collected POINT item!
                }
                case BOMB -> {
                    // 1. Increase spellCards by 1
                    this.spellCards += 1;
                    // 2. Add score by item.getScoreValue() via addScore()
                    addScore(item.getScoreValue());
                    // 3. Print: [name] collected BOMB item! SpellCards: [spellCards]
                    System.out.println(name + " collected BOMB item! SpellCards: " + this.spellCards);
                }
                case LIFE -> {
                    // 1. Increase hp by 20
                    this.hp += 20;
                    // 2. Add score by item.getScoreValue() via addScore()
                    addScore(item.getScoreValue());
                    // 3. Print: [name] collected LIFE item! HP: [hp]
                    System.out.println(name + " collected LIFE item! HP: " + this.hp);
                }
            }
        } else {
            addScore(item.getScoreValue());
            System.out.println(name + " collected " + item.getItemType() + "!");
        }
    }



    public void takeDamage(int damage) {
        setHp(getHp() - damage);

        if (getHp() > 0) {
            System.out.println(getName() + " took " + damage + " damage, Remaining HP : " + getHp());
        } else {
            System.out.println(getName() + " has been defeated");
        }
    }

    public void shoot(Enemy target) {
        int damage = 10 + getPower();
        System.out.println(getName() + " shoots " + target.getName() + " dealing " + damage + " DMG!!");
        target.takeDamage(damage);
    }

    public boolean isAlive() {
        return getHp() > 0;
    }

    public void collectItem(Item item) {
        System.out.println(getName() + " collected " + item.getItemType() + "!");
        if (item.getScoreValue() > 0) {
            addScore(item.getScoreValue());
        }
    }

    public void addScore(long points) {
        if (points > 0) {
            this.score += points;
            System.out.println(getName() + " gained " + points + " pts! Total Score: " + this.score);
        }
    }

    public int getHp() { return hp; }
    public void setHp(int hp) {
        this.hp = Math.max(0, hp);
    }

    public String getName() { return name; }
    public void setName(String name) {
        this.name = name;
    }

    public int getPower() { return power; }
    public void setPower(int power) {
        this.power = power;
    }

    public int getSpellCards() { return spellCards; }
    public void setSpellCards(int spellCards) {
        this.spellCards = spellCards;
    }

    public long getScore() { return score; }
}
