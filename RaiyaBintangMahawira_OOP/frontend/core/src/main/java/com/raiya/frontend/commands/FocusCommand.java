package com.raiya.frontend.commands;

import com.raiya.frontend.objects.Player;
import com.raiya.frontend.systems.BulletManager;

public class FocusCommand implements Command {
    private boolean focused;

    public FocusCommand(boolean focused) {
        this.focused = focused;
    }

    // - Call the player's setFocused(focused) method
    @Override
    public void execute(Player player, BulletManager bulletManager) {
        player.setFocused(focused);
    }
}
