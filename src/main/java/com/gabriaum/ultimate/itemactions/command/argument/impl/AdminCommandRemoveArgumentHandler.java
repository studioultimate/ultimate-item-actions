package com.gabriaum.ultimate.itemactions.command.argument.impl;

import com.gabriaum.ultimate.itemactions.UltimateItemActions;
import com.gabriaum.ultimate.itemactions.UltimateItemActionsMain;
import com.gabriaum.ultimate.itemactions.UltimateItemActionsRegistry;
import com.gabriaum.ultimate.itemactions.command.argument.CommandArgumentHandler;
import me.saiintbrisson.minecraft.command.command.Context;
import org.bukkit.command.CommandSender;

public class AdminCommandRemoveArgumentHandler implements CommandArgumentHandler {
    @Override
    public String getAlias() {
        return "remove";
    }

    @Override
    public String getPermission() {
        return "ultimateitemactions.admin.remove";
    }

    @Override
    public void execute(Context<CommandSender> context) {
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

        UltimateItemActionsMain.getInstance().getItemsConfig().set(args[1], null);

        context.sendMessage(
                String.format(
                        "§cYou have successfully removed item %s.",
                        args[1]
                )
        );
    }

    private void sendHelpMessage(Context<CommandSender> context) {
        context.sendMessage(" ");
        context.sendMessage("§cUse: §a/" + context.getLabel() + " remove <item-key>");
        context.sendMessage(" ");
    }
}