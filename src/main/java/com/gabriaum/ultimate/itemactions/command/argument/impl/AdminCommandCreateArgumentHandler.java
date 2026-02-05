package com.gabriaum.ultimate.itemactions.command.argument.impl;

import com.gabriaum.ultimate.itemactions.UltimateItemActions;
import com.gabriaum.ultimate.itemactions.UltimateItemActionsMain;
import com.gabriaum.ultimate.itemactions.UltimateItemActionsRegistry;
import com.gabriaum.ultimate.itemactions.command.argument.CommandArgumentHandler;
import com.gabriaum.ultimate.itemactions.menu.UltimateItemActionsEditMenu;
import com.gabriaum.ultimate.itemactions.service.ItemActionsDefaultCreateService;
import me.saiintbrisson.minecraft.command.command.Context;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AdminCommandCreateArgumentHandler implements CommandArgumentHandler {
    private final ItemActionsDefaultCreateService defaultCreateService = new ItemActionsDefaultCreateService();

    @Override
    public String getAlias() {
        return "create";
    }

    @Override
    public String getPermission() {
        return "ultimateitemactions.admin.create";
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
            if (ultimateItemActions != null) {
                context.sendMessage("§cA custom item with this key already exists.");
                return;
            }

            ultimateItemActions = defaultCreateService.create(args[1]);
            Player player = (Player) context.getSender();

            player.sendMessage(
                    String.format(
                            "§aYou have successfully created item %s.",
                            args[1]
                    )
            );
            player.playSound(
                    player.getLocation(),
                    Sound.LEVEL_UP,
                    1f,
                    1f
            );

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
        context.sendMessage("§cUse: §a/" + context.getLabel() + " create <item-key>");
        context.sendMessage(" ");
    }
}