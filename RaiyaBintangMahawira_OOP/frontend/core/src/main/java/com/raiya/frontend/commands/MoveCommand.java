package com.raiya.frontend.commands;

import com.raiya.frontend.objects.Player;
import com.raiya.frontend.systems.BulletManager;

public class MoveCommand implements Command {
    private float dx;
    private float dy;

    public MoveCommand(float dx, float dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public void set(float dx, float dy) {
        this.dx = dx;
        this.dy = dy;
    }

    // TODO: Override `execute` with the following business logic:
    public void execute(Player player, BulletManager bulletManager) {
        // - Call the player's move(dx, dy) method
        player.move(dx, dy);
        // - Update the player's animation state
        player.updateAnimationState(dx);
    }
}
