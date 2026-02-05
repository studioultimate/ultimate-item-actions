package com.gabriaum.ultimate.itemactions;

import com.gabriaum.ultimate.itemactions.command.AdminCommand;
import com.gabriaum.ultimate.itemactions.infra.service.ConsoleService;
import com.gabriaum.ultimate.itemactions.infra.util.ConfigUtil;
import com.gabriaum.ultimate.itemactions.listener.PlayerInteractListener;
import com.gabriaum.ultimate.itemactions.listener.edit.ItemActionEditDisplayNameListener;
import com.gabriaum.ultimate.itemactions.loader.UltimateItemActionsLoader;
import com.gabriaum.ultimate.itemactions.manager.UltimateItemActionsManager;
import com.gabriaum.ultimate.itemactions.menu.UltimateItemActionsEditMenu;
import lombok.Getter;
import me.devnatan.inventoryframework.ViewFrame;
import me.saiintbrisson.bukkit.command.BukkitFrame;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class UltimateItemActionsMain extends JavaPlugin {
    @Getter
    protected static UltimateItemActionsMain instance;

    private ConfigUtil itemsConfig;
    private UltimateItemActionsManager manager;
    private ViewFrame viewFrame;

    @Override
    public void onLoad() {
        ConsoleService.log("Starting loading operations...");

        instance = this;
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
        loadInventories();
        loadCommands();

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
        pluginManager.registerEvents(new ItemActionEditDisplayNameListener(), this);
    }

    private void loadInventories() {
        viewFrame = ViewFrame.create(this)
                .with(
                        new UltimateItemActionsEditMenu()
                )
                .register();
    }

    private void loadCommands() {
        BukkitFrame bukkitFrame = new BukkitFrame(this);

        bukkitFrame.registerCommands(new AdminCommand());
    }

    @Override
    public void onDisable() {

    }
}