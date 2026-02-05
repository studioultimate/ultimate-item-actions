package com.gabriaum.ultimate.itemactions;

import com.gabriaum.ultimate.itemactions.infra.service.ConsoleService;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class UltimateItemActionsMain extends JavaPlugin {
    @Getter
    protected static UltimateItemActionsMain instance;

    @Override
    public void onLoad() {
        ConsoleService.log("Starting loading operations...");

        saveDefaultConfig();
    }

    @Override
    public void onEnable() {
        ConsoleService.log("Apparently no problems were found and the plugin was started.");
    }

    @Override
    public void onDisable() {

    }
}