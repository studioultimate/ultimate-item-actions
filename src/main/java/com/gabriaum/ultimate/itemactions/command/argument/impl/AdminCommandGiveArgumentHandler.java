package com.gabriaum.ultimate.itemactions.command.argument.impl;

import com.gabriaum.ultimate.itemactions.UltimateItemActions;
import com.gabriaum.ultimate.itemactions.UltimateItemActionsRegistry;
import com.gabriaum.ultimate.itemactions.command.argument.CommandArgumentHandler;
import com.gabriaum.ultimate.itemactions.service.ItemActionsGiveableService;
import me.saiintbrisson.minecraft.command.command.Context;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AdminCommandGiveArgumentHandler implements CommandArgumentHandler {
    private final ItemActionsGiveableService giveableService = new ItemActionsGiveableService();

    @Override
    public String getAlias() {
        return "give";
    }

    @Override
    public String getPermission() {
        return "ultimateitemactions.admin.give";
    }

    @Override
    public void execute(Context<CommandSender> context) {
        String[] args = context.getArgs();
        if (args.length < 3) {
            sendHelpMessage(context);
            return;
        }

        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            context.sendMessage("§cTarget not found.");
            return;
        }

        String itemKey = args[2];
        int amount = 1;
        if (args.length > 3) {
            try {
                amount = Integer.parseInt(args[3]);
            } catch (NumberFormatException ignored) {
                context.sendMessage("§cYou need to enter a number.");
                return;
            }
        }

        UltimateItemActions ultimateItemActions = UltimateItemActionsRegistry.getByKey(itemKey);
        if (ultimateItemActions == null) {
            context.sendMessage("§cNo custom items were found with this key.");
            return;
        }

        giveableService.giveTo(
                target,
                ultimateItemActions,
                amount
        );

        context.sendMessage(
                String.format(
                        "§aYou have successfully sent item %s to player %s.",
                        ultimateItemActions.getKey(),
                        target.getName()
                )
        );
    }

    private void sendHelpMessage(Context<CommandSender> context) {
        context.sendMessage(" ");
        context.sendMessage("§cUse: §a/" + context.getLabel() + " give <target> <item-key> [amount]");
        context.sendMessage(" ");
    }
}