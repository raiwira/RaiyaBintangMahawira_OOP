package com.raiya.frontend.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import java.util.HashMap;
import java.util.Map;

public class AssetManager {
    private static AssetManager instance;
    private Map<String, TextureRegion> textureRegionMap = new HashMap();
    private Map<String, Animation<TextureRegion>> animationMap = new HashMap();
    private Map<String, Texture> textureMap = new HashMap();
    private Map<String, Sound> soundMap = new HashMap();
    private Map<String, Music> musicMap = new HashMap();

    private AssetManager() {
    }

    public static AssetManager getInstance() {
        if (instance == null) {
            instance = new AssetManager();
        }

        return instance;
    }

    public Texture loadTexture(String filename) {
        if (!this.textureMap.containsKey(filename)) {
            if (Gdx.files == null || !Gdx.files.internal(filename).exists()) {
                return null;
            }

            Texture texture = new Texture(Gdx.files.internal(filename));
            this.textureMap.put(filename, texture);
        }

        return (Texture)this.textureMap.get(filename);
    }

    public void registerSound(String key, String filename) {
        if (!this.soundMap.containsKey(key) && Gdx.files.internal(filename).exists()) {
            Sound sound = Gdx.audio.newSound(Gdx.files.internal(filename));
            this.soundMap.put(key, sound);
        }

    }

    public void registerMusic(String key, String filename) {
        if (!this.musicMap.containsKey(key) && Gdx.files.internal(filename).exists()) {
            Music music = Gdx.audio.newMusic(Gdx.files.internal(filename));
            this.musicMap.put(key, music);
        }

    }

    public void playSound(String key) {
        Sound sound = (Sound)this.soundMap.get(key);
        if (sound != null) {
            sound.play(0.5F);
        }

    }

    public void playMusic(String key, boolean loop) {
        Music music = (Music)this.musicMap.get(key);
        if (music != null) {
            music.setLooping(loop);
            music.setVolume(0.2F);
            music.play();
        }

    }

    public void registerRegion(String key, TextureRegion region) {
        this.textureRegionMap.put(key, region);
    }

    public void registerRegionFromSheet(String key, String filename, int tileWidth, int tileHeight, int row, int col) {
        Texture tex = this.loadTexture(filename);
        if (tex != null) {
            TextureRegion[][] grid = TextureRegion.split(tex, tileWidth, tileHeight);
            this.textureRegionMap.put(key, grid[row][col]);
        }

    }

    public void registerAnimationFromSheet(String key, String filename, int tileWidth, int tileHeight, int row, int numFrames, float frameDuration) {
        this.registerAnimationFromSheet(key, filename, tileWidth, tileHeight, row, 0, numFrames, frameDuration, PlayMode.LOOP);
    }

    public void registerAnimationFromSheet(String key, String filename, int tileWidth, int tileHeight, int row, int startCol, int numFrames, float frameDuration, Animation.PlayMode playMode) {
        Texture tex = this.loadTexture(filename);
        if (tex != null) {
            TextureRegion[][] grid = TextureRegion.split(tex, tileWidth, tileHeight);
            TextureRegion[] frames = new TextureRegion[numFrames];

            for(int i = 0; i < numFrames; ++i) {
                frames[i] = grid[row][startCol + i];
            }

            Animation<TextureRegion> anim = new Animation(frameDuration, frames);
            anim.setPlayMode(playMode);
            this.animationMap.put(key, anim);
            this.textureRegionMap.put(key, frames[0]);
        }

    }

    public void registerFlippedAnimationFromSheet(String key, String filename, int tileWidth, int tileHeight, int row, int numFrames, float frameDuration, boolean flipX, boolean flipY) {
        this.registerFlippedAnimationFromSheet(key, filename, tileWidth, tileHeight, row, 0, numFrames, frameDuration, PlayMode.LOOP, flipX, flipY);
    }

    public void registerFlippedAnimationFromSheet(String key, String filename, int tileWidth, int tileHeight, int row, int startCol, int numFrames, float frameDuration, Animation.PlayMode playMode, boolean flipX, boolean flipY) {
        Texture tex = this.loadTexture(filename);
        if (tex != null) {
            TextureRegion[][] grid = TextureRegion.split(tex, tileWidth, tileHeight);
            TextureRegion[] frames = new TextureRegion[numFrames];

            for(int i = 0; i < numFrames; ++i) {
                TextureRegion frameCopy = new TextureRegion(grid[row][startCol + i]);
                frameCopy.flip(flipX, flipY);
                frames[i] = frameCopy;
            }

            Animation<TextureRegion> anim = new Animation(frameDuration, frames);
            anim.setPlayMode(playMode);
            this.animationMap.put(key, anim);
            this.textureRegionMap.put(key, frames[0]);
        }

    }

    public TextureRegion getTextureRegion(String key) {
        return (TextureRegion)this.textureRegionMap.get(key);
    }

    public TextureRegion getRegion(String key) {
        return this.getTextureRegion(key);
    }

    public Animation<TextureRegion> getAnimation(String key) {
        return (Animation)this.animationMap.get(key);
    }

    public void init() {
        this.registerAnimationFromSheet("player_idle", "player.png", 32, 48, 0, 0, 8, 0.125F, PlayMode.LOOP);
        this.registerAnimationFromSheet("player_left", "player.png", 32, 48, 1, 0, 4, 0.12F, PlayMode.LOOP);
        this.registerAnimationFromSheet("player_right", "player.png", 32, 48, 2, 0, 4, 0.12F, PlayMode.LOOP);
        this.registerAnimationFromSheet("boss_idle", "rumia.png", 64, 64, 0, 0, 4, 0.2F, PlayMode.LOOP);
        this.registerAnimationFromSheet("boss_left", "rumia.png", 64, 64, 1, 0, 4, 0.15F, PlayMode.REVERSED);
        this.registerAnimationFromSheet("boss_right", "rumia.png", 64, 64, 2, 0, 4, 0.15F, PlayMode.NORMAL);
        this.registerAnimationFromSheet("fairy_idle_blue", "fairy.png", 32, 32, 0, 0, 8, 0.125F, PlayMode.LOOP);
        this.registerAnimationFromSheet("fairy_idle_red", "fairy.png", 32, 32, 1, 0, 8, 0.125F, PlayMode.LOOP);
        this.registerRegionFromSheet("bullet_amulet", "amulet_reimu.png", 16, 16, 0, 0);
        this.registerRegionFromSheet("bullet_amulet_homing", "amulet_reimu.png", 16, 16, 1, 0);
        this.registerRegionFromSheet("bullet_danmaku", "bullets_small.png", 16, 16, 2, 3);
        this.registerRegionFromSheet("item_power", "items.png", 16, 16, 0, 0);
        this.registerRegionFromSheet("item_point", "items.png", 16, 16, 0, 1);
        this.registerRegionFromSheet("item_bomb", "items.png", 16, 16, 0, 3);
        this.registerRegionFromSheet("item_life", "items.png", 16, 16, 0, 5);
        this.registerMusic("bgm", "rumia_bgm.mp3");
        this.registerSound("shoot", "shoot.wav");
        this.registerSound("item", "item.wav");
        this.registerSound("death", "death.wav");
    }

    public void dispose() {
        for(Texture texture : this.textureMap.values()) {
            texture.dispose();
        }

        for(Sound sound : this.soundMap.values()) {
            sound.dispose();
        }

        for(Music music : this.musicMap.values()) {
            music.dispose();
        }

        this.textureMap.clear();
        this.textureRegionMap.clear();
        this.animationMap.clear();
        this.soundMap.clear();
        this.musicMap.clear();
    }
}
