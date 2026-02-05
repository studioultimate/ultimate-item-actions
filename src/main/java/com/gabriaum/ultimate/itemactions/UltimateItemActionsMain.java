package com.gabriaum.ultimate.itemactions;

import com.gabriaum.ultimate.itemactions.infra.service.ConsoleService;
import com.gabriaum.ultimate.itemactions.infra.util.ConfigUtil;
import com.gabriaum.ultimate.itemactions.listener.PlayerInteractListener;
import com.gabriaum.ultimate.itemactions.loader.UltimateItemActionsLoader;
import com.gabriaum.ultimate.itemactions.manager.UltimateItemActionsManager;
import lombok.Getter;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class UltimateItemActionsMain extends JavaPlugin {
    @Getter
    protected static UltimateItemActionsMain instance;

    private ConfigUtil itemsConfig;
    private UltimateItemActionsManager manager;

    @Override
    public void onLoad() {
        ConsoleService.log("Starting loading operations...");

        loadConfigurations();
    }

    private void loadConfigurations() {
        saveDefaultConfig();
        itemsConfig = new ConfigUtil(this, "items.yml");
    }

    @Override
    public void onEnable() {
        loadManagers();
        loadListeners();

        ConsoleService.log("Apparently no problems were found and the plugin was started.");
    }

    private void loadManagers() {
        manager = new UltimateItemActionsManager();
        UltimateItemActionsLoader loader = new UltimateItemActionsLoader(itemsConfig, manager);
        loader.load();
    }

    private void loadListeners() {
        PluginManager pluginManager = getServer().getPluginManager();

        pluginManager.registerEvents(new PlayerInteractListener(), this);
    }

    @Override
    public void onDisable() {

    }
}