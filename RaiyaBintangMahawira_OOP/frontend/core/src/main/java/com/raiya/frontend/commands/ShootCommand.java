package com.raiya.frontend.commands;

import com.raiya.frontend.objects.Player;
import com.raiya.frontend.systems.BulletManager;

public class ShootCommand implements Command {

    @Override
    public void execute(Player player, BulletManager bulletManager) {
        // - Check if player exists (is not null)
        if (player != null) {
            // - If so, call the player's shootBullet(bulletManager) method
            player.shootBullet(bulletManager);
        }
    }
}
