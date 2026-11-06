package com.raiya.frontend.commands;

import com.raiya.frontend.objects.Player;
import com.raiya.frontend.systems.BulletManager;

public interface Command {
    void execute(Player player, BulletManager bulletManager);
}


