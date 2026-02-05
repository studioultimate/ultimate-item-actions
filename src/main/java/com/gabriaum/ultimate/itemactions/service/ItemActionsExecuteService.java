package com.gabriaum.ultimate.itemactions.service;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.github.paperspigot.Title;

import java.util.List;

public class ItemActionsExecuteService {
    public void handle(
            Player player,
            List<String> actions
    ) {
        for (String action : actions) {
            if (checkSendMessage(player, action)) continue;
            if (checkTitle(player, action)) continue;
            if (checkConsole(player, action)) continue;

            checkPlaySound(player, action);
        }
    }

    private Boolean checkSendMessage(Player player, String action) {
        if (action.startsWith("sendmessage:")) {
            action = action.replace("sendmessage:", "");
            player.sendMessage(action);
            return true;
        }

        return false;
    }

    private Boolean checkTitle(Player player, String action) {
        if (action.startsWith("title:")) {
            action = action.replace("title:", "");
            String[] split = action.split(";");
            if (split.length > 4) {
                String title = split[0];
                String subtitle = split[1];
                int fadeIn = Integer.parseInt(split[2]);
                int stay = Integer.parseInt(split[3]);
                int fadeOut = Integer.parseInt(split[4]);

                player.sendTitle(
                        new Title(
                                title,
                                subtitle,
                                fadeIn,
                                stay,
                                fadeOut
                        )
                );
            }

            return true;
        }

        return false;
    }

    private Boolean checkConsole(Player player, String action) {
        if (action.startsWith("console:")) {
            action = action
                    .replace("console:", "")
                    .replace("{player}", player.getName());

            Bukkit.dispatchCommand(
                    Bukkit.getConsoleSender(),
                    action
            );

            return true;
        }

        return false;
    }

    private Boolean checkPlaySound(Player player, String action) {
        if (action.startsWith("play:")) {
            try {
                action = action.replace("play:", "");
                Sound sound = Sound.valueOf(action);
                player.playSound(
                        player.getLocation(),
                        sound,
                        1f,
                        1f
                );
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            return true;
        }

        return false;
    }
}