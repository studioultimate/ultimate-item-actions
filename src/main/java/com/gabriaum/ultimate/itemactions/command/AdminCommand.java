package com.gabriaum.ultimate.itemactions.command;

import com.gabriaum.ultimate.itemactions.command.argument.CommandArgumentHandler;
import com.gabriaum.ultimate.itemactions.command.argument.impl.*;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

public class AdminCommand {
    protected final List<CommandArgumentHandler> argumentHandlers = new ArrayList<>(
            Arrays.asList(
                    new AdminCommandReloadArgumentHandler(),
                    new AdminCommandCreateArgumentHandler(),
                    new AdminCommandEditArgumentHandler(),
                    new AdminCommandGiveArgumentHandler(),
                    new AdminCommandRemoveArgumentHandler()
            )
    );

    @Command(
            name = "ultimateitemactions",
            aliases = {"uitemactions", "uiactions"},
            permission = "ultimateitemactions.admin"
    )
    public void execute(Context<CommandSender> context) {
        String[] args = context.getArgs();
        if (args.length < 1) {
            sendHelpMessage(context);
            return;
        }

        CommandArgumentHandler argumentHandler = getByArg(args[0]);
        if (argumentHandler == null) {
            sendHelpMessage(context);
            return;
        }

        if (context.getSender() instanceof Player) {
            Player player = (Player) context.getSender();
            if (player == null) return;

            if (!player.hasPermission(argumentHandler.getPermission())) {
                player.sendMessage("§cYou do not have permission to perform this function.");
                return;
            }
        }

        argumentHandler.execute(context);
    }

    private void sendHelpMessage(Context<CommandSender> context) {
        context.sendMessage(" ");
        context.sendMessage("§6Use of §a/" + context.getLabel() + "§6:");
        context.sendMessage("§e/" + context.getLabel() + " create <item-key> §b- Create a new custom item action.");
        context.sendMessage("§e/" + context.getLabel() + " edit <item-key> §b- Edit a custom item action.");
        context.sendMessage("§e/" + context.getLabel() + " remove <item-key> §b- Remove custom item action.");
        context.sendMessage("§e/" + context.getLabel() + " give <target> <item-key> [amount] §b- Send a custom item to a player.");
        context.sendMessage(" ");
    }

    private CommandArgumentHandler getByArg(String arg) {
        if (arg == null) return null;

        return argumentHandlers.stream()
                .filter(handler -> handler.getAlias().equals(arg))
                .findFirst()
                .orElse(null);
    }
}