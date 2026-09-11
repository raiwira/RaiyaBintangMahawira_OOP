package com.raiya.frontend;

import com.badlogic.gdx.graphics.Color;

public class Player extends ClassObject {
    public String name;
    public int hp;
    public int power;
    public int spellCards;
    public long score;

    public Player(String name, int hp, int power, int spellCards) {
        super(280, 40, 32, 32, 0, Color.RED);

        this.name = name;
        this.hp = hp;
        this.power = power;
        this.spellCards = spellCards;
        this.score = score;
    }
    public Player(float x, float y, String name, int hp, int power, int spellCards) {
        super(x, y, 32, 32, 0, Color.RED);


    }
    public void takeDamage(int damage) {
        // 1. Reduce hp by the damage value.
        setHp(getHp() - damage);

        // 3. If HP is still greater than 0, display the remaining HP in the format: [PlayerName] took [damage] damage! Remaining HP: [hp]
        if (this.hp > 0)
            System.out.println(this.name + "took " + damage + "damage, Remaining HP : " + this.hp);
            // 4. If HP reaches 0, display a message that the Player has been defeated.
        else {
            System.out.println(this.name + " has been defeated");
        }
    }
    public void shoot(Enemy target) {
        // 1. Create an int named damage, calculated by adding 10 to power.
        int damage = 10 + getPower();
        // 2. Display information that the Player is shooting the Enemy, in the format: [name] shoots [TargetName] dealing [damage] DMG!
        System.out.println(this.name + " shoots " + target.name + " dealing " + damage + " DMG!!");
        // 3. Call the Enemy object's takeDamage() method.
        target.takeDamage(damage);
    }
    public boolean isAlive() {
        // 1. Return true if hp > 0, and false otherwise
        return this.hp > 0;
    }
    public int getHp() {return hp;}
    public void setHp(int hp) {
        this.hp = Math.max(0, hp);
    }
    public String getName() {return name;}
    public void setName(String name) {
        this.name = name;
    }
    public int getPower() {return power;}
    public void setPower(int power) {
        this.power = power;
    }
    public int getSpellCards() {return spellCards;}
    public void setSpellCards(int spellCards) {
        this.spellCards = spellCards;
    }
    public int getScore() {return score;}

    public void addScore(long points) {
        if (points > 0) {
            this.score += points;
            System.out.println(getName() + " gained " + points + " pts! Total Score: " + this.score);
        }
    }






}
