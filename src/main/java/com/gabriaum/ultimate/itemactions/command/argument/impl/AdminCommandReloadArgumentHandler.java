package com.gabriaum.ultimate.itemactions.command.argument.impl;

import com.gabriaum.ultimate.itemactions.UltimateItemActions;
import com.gabriaum.ultimate.itemactions.UltimateItemActionsMain;
import com.gabriaum.ultimate.itemactions.command.argument.CommandArgumentHandler;
import com.gabriaum.ultimate.itemactions.loader.UltimateItemActionsLoader;
import com.gabriaum.ultimate.itemactions.manager.UltimateItemActionsManager;
import me.saiintbrisson.minecraft.command.command.Context;
import org.bukkit.command.CommandSender;

public class AdminCommandReloadArgumentHandler implements CommandArgumentHandler {
    @Override
    public String getAlias() {
        return "reload";
    }

    @Override
    public String getPermission() {
        return "ultimateitemactions.admin.reload";
    }

    @Override
    public void execute(Context<CommandSender> context) {
        try {
            UltimateItemActionsManager manager = UltimateItemActionsMain.getInstance().getManager();
            manager.clear();

            UltimateItemActionsMain.getInstance().reloadConfig();
            new UltimateItemActionsLoader(
                    UltimateItemActionsMain.getInstance().getItemsConfig(),
                    manager
            );

            context.sendMessage("§aThe files have been reloaded successfully.");
        } catch (Exception ex) {
            ex.printStackTrace();
            context.sendMessage("§cThere was a problem reloading the files.");
        }
    }
}
