package com.raiya.frontend;

public class Enemy {

    // Problem 3 — Creating Enemy Attributes
    public String name;
    public int hp;
    public int maxHp;

    // Problem 4 — Creating the Enemy Constructor
    public Enemy(String name, int hp) {
        this.name = name;
        this.hp = hp;

        // Setting maxHp to match the initial starting hp, as required
        this.maxHp = hp;
    }

    public void takeDamage(int damage) {
        // 1. Reduce hp by the damage value.
        this.hp -= damage;
        // 2. HP must not go below 0.
        if (this.hp < 0) {
            this.hp = 0;
        }
        // 3. Display the current HP in the format: [EnemyName] took [damage] damage! HP: [currentHP]/[maxHP]
        if (this.hp > 0)
            System.out.println(this.name + "took " + damage + "damage!!, HP : [" + this.hp + this.maxHp + "]");

            // 4. If HP reaches 0, display that the Enemy has been defeated, in the format: [EnemyName] was defeated!
        else {
            System.out.println(this.name + " was defeated!!!!");
        }
    }
    public void attack(Player player, int damage) {
        // 1. Display information that the Enemy is attacking the Player, in the format: [EnemyName] unleashes bullet barrage on [PlayerName]!
        System.out.println(this.name + " unleashes buller barrage on " + player.name +  "!");
        // 2. Call the Player's takeDamage() method using the given damage.
        player.takeDamage(damage);
    }
    public boolean isAlive() {
        // 1. Return true if hp > 0, and false otherwise
        return this.hp > 0;
    }


}
