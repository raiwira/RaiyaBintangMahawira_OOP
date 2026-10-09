package com.raiya.frontend.systems;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.raiya.frontend.objects.Player;
import com.raiya.frontend.objects.bullets.Bullet;
import com.raiya.frontend.objects.bullets.BulletType;
import com.raiya.frontend.objects.enemies.Boss;
import com.raiya.frontend.objects.enemies.Fairy;
import com.raiya.frontend.objects.items.Item;
import com.raiya.frontend.objects.items.ItemType;

public class EntityFactory {

    // Create a Player and assign the 'player_idle' animation from AssetManager
    public static Player createPlayer(float x, float y, String name, int hp, int power, int spellCards) {
        Player player = new Player(x, y, name, hp, power, spellCards);
        Animation<TextureRegion> anim = AssetManager.getInstance().getAnimation("player_idle");
        player.setAnimation(anim);
        return player;
    }
    // MISSING ; Fairy enemy and assign the 'fairy_idle' animation from AssetManager
    public static Fairy createFairy(float x, float y, String name, int hp) {
        Fairy fairy = new Fairy(x, y, name, hp);
        // 2. Retrieve the "fairy_idle_red" animation using getAnimation(...)
        //    from AssetManager.getInstance(). Store the result in
        //    a local variable named `idleAnim`.
        Animation<TextureRegion> idleAnim = AssetManager.getInstance().getAnimation("fairy_idle_red");
        fairy.setAnimation(idleAnim);
        return fairy;
    }
    public static Fairy createFairy(float x, float y, String name, int hp, String keyString) {
        // TODO:
        // 1. Create a new Fairy using x, y, name, and hp from the parameters;
        //    store it in a local variable named `fairy`.
        Fairy fairy = new Fairy(x, y, name, hp);
        // 2. Retrieve the animation for keyString using getAnimation(...)
        //    from AssetManager.getInstance(). Store the result in
        //    a local variable named `idleAnim`.
        Animation<TextureRegion> idelAnim = AssetManager.getInstance().getAnimation(keyString);
        // 3. Assign idleAnim to fairy using fairy.setAnimation(...).
        fairy.setAnimation(idelAnim);
        // 4. Return fairy.
        return fairy;
    }

    // MISSING ; Boss enemy and assign the 'boss_idle' animation from AssetManager
    public static Boss createBoss(float x, float y, String name, int hp) {
        Boss boss = new Boss(x, y, name, hp);
        // 2. Retrieve the "boss_idle" animation using getAnimation(...)
        //    from AssetManager.getInstance(). Store the result in
        //    a local variable named `idleAnim`.
        Animation<TextureRegion> idleAnim = AssetManager.getInstance().getAnimation("boss_idle");
        boss.setAnimation(idleAnim);
        return boss;
    }

    // Create an Item and assign its sprite from AssetManager based on ItemType
    public static Item createItem(float x, float y, ItemType itemType) {
        Item item = new Item(x, y, itemType);
        String key = switch (itemType) {
            case POWER -> "item_power";
            case POINT -> "item_point";
            case BOMB  -> "item_bomb";
            case LIFE  -> "item_life";
        };
        TextureRegion sprite = AssetManager.getInstance().getTextureRegion(key);
        item.setSprite(sprite);
        return item;
    }

    // Create an enemy bullet (type DANMAKU, speed 0f, sprite bullet_danmaku)
    public static Bullet createEnemyBullet(float x, float y, int damage) {
        Bullet bullet = new Bullet(x, y, 0f, BulletType.DANMAKU, damage);
        TextureRegion sprite = AssetManager.getInstance().getTextureRegion("bullet_danmaku");
        bullet.setSprite(sprite);
        return bullet;
    }
}
