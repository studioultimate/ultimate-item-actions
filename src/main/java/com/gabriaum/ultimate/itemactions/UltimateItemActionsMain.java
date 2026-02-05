package com.gabriaum.ultimate.itemactions;

import com.gabriaum.ultimate.itemactions.infra.service.ConsoleService;
import com.gabriaum.ultimate.itemactions.manager.UltimateItemActionsManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class UltimateItemActionsMain extends JavaPlugin {
    @Getter
    protected static UltimateItemActionsMain instance;

    private UltimateItemActionsManager manager;

    @Override
    public void onLoad() {
        ConsoleService.log("Starting loading operations...");

        saveDefaultConfig();
    }

    @Override
    public void onEnable() {
        loadManagers();

        ConsoleService.log("Apparently no problems were found and the plugin was started.");
    }

    private void loadManagers() {
        manager = new UltimateItemActionsManager();
    }

    @Override
    public void onDisable() {

    }
}