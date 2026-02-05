package com.gabriaum.ultimate.itemactions.command.argument;

import me.saiintbrisson.minecraft.command.command.Context;
import org.bukkit.command.CommandSender;

public interface CommandArgumentHandler {
    String getAlias();
    String getPermission();
    void execute(Context<CommandSender> context);
}
