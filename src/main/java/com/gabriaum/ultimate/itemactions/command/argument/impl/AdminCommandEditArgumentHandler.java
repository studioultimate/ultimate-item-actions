package com.gabriaum.ultimate.itemactions.command.argument.impl;

import com.gabriaum.ultimate.itemactions.UltimateItemActions;
import com.gabriaum.ultimate.itemactions.UltimateItemActionsMain;
import com.gabriaum.ultimate.itemactions.UltimateItemActionsRegistry;
import com.gabriaum.ultimate.itemactions.command.argument.CommandArgumentHandler;
import com.gabriaum.ultimate.itemactions.menu.UltimateItemActionsEditMenu;
import me.saiintbrisson.minecraft.command.command.Context;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AdminCommandEditArgumentHandler implements CommandArgumentHandler {
    @Override
    public String getAlias() {
        return "edit";
    }

    @Override
    public String getPermission() {
        return "ultimateitemactions.admin.edit";
    }

    @Override
    public void execute(Context<CommandSender> context) {
        if (context.getSender() instanceof Player) {
            String[] args = context.getArgs();
            if (args.length < 2) {
                sendHelpMessage(context);
                return;
            }

            UltimateItemActions ultimateItemActions = UltimateItemActionsRegistry.getByKey(args[1]);
            if (ultimateItemActions == null) {
                context.sendMessage("§cNo custom items were found with this key.");
                return;
            }

            Player player = (Player) context.getSender();
            UltimateItemActionsMain.getInstance()
                    .getViewFrame()
                    .open(
                            UltimateItemActionsEditMenu.class,
                            player,
                            ultimateItemActions
                    );
        } else
            context.sendMessage("§cThis command is only available to players.");
    }

    private void sendHelpMessage(Context<CommandSender> context) {
        context.sendMessage(" ");
        context.sendMessage("§cUse: §a/" + context.getLabel() + " edit <item-key>");
        context.sendMessage(" ");
    }
}