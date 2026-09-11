package com.raiya.frontend;

import com.badlogic.gdx.graphics.Color;

public class Enemy extends ClassObject {

    // Problem 3 — Creating Enemy Attributes
    public String name;
    public int hp;
    public int maxHp;
    protected long scoreValue;

    // Problem 4 — Creating the Enemy Constructor
    public Enemy(String name, int hp) {
        super(200, 380, 24, 24, 0, Color.PINK);
        this.name = name;
        this.hp = hp;

        // Setting maxHp to match the initial starting hp, as required
        this.maxHp = hp;
    }
    public Enemy(float x, float y, float width, float height, Color color, String name, int hp, long scoreValue){
        super();

    }


    public boolean takeDamage(int damage) {
        // 1. Reduce hp by the damage value.
        setHp(getHp() - damage);
        // 3. Display the current HP in the format: [EnemyName] took [damage] damage! HP: [currentHP]/[maxHP]
        if (this.hp > 0) {
            System.out.println(this.name + "took " + damage + "damage!!, HP : [" + this.hp + this.maxHp + "]");
            return false;
        }
            // 4. If HP reaches 0, display that the Enemy has been defeated, in the format: [EnemyName] was defeated!
        else {
            System.out.println(this.name + " was defeated!!!!");
            return true;
        }
    }
    public void attack(Player player, int damage) {
        // 1. Display information that the Enemy is attacking the Player, in the format: [EnemyName] unleashes bullet barrage on [PlayerName]!
        System.out.println(this.name + " unleashes buller barrage on " + player.name +  "!");
        // 2. Call the Player's takeDamage() method using the given damage.
        player.takeDamage(damage);
    }
    public String getName() {return name;}
    public void setName(String name) {
        this.name = name;
    }
    public int getHp() {return hp;}
    public void setHp(int hp) {
        this.hp = Math.max(0, hp);
    }
    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }
    public long getScoreValue() {return scoreValue;}
    public void setScoreValue(long scoreValue) {
        this.scoreValue = scoreValue;
    }
    public boolean isAlive() {
        // 1. Return true if hp > 0, and false otherwise
        return this.hp > 0;
    }


}
