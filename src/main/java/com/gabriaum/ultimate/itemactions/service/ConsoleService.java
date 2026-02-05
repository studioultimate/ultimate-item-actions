package com.gabriaum.ultimate.itemactions.service;

import com.gabriaum.ultimate.itemactions.UltimateItemActionsMain;
import org.bukkit.Bukkit;

public class ConsoleService {
    public static void debug(String message) {
        if (UltimateItemActionsMain.getInstance().getConfig().getBoolean("debug-mode", false))
            Bukkit.getLogger().warning("[DEBUG] " + message);
    }

    public static void log(String message) {
        Bukkit.getLogger().warning(message);
    }

    public static void fine(String message) {
        Bukkit.getLogger().fine(message);
    }

    public static void error(String message) {
        Bukkit.getLogger().severe(message);
    }
}
