package com.gabriaum.ultimate.itemactions;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class UltimateItemActionsMain extends JavaPlugin {
    @Getter
    protected static UltimateItemActionsMain instance;

    @Override
    public void onLoad() {
        saveDefaultConfig();
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }
}