package com.gabriaum.ultimate.itemactions.listener.edit;

import com.gabriaum.ultimate.itemactions.UltimateItemActions;
import com.gabriaum.ultimate.itemactions.UltimateItemActionsMain;
import com.gabriaum.ultimate.itemactions.UltimateItemActionsRegistry;
import com.gabriaum.ultimate.itemactions.menu.UltimateItemActionsEditMenu;
import com.gabriaum.ultimate.itemactions.service.ItemActionsUpdateParamService;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ItemActionEditIconListener implements Listener {
    private final ItemActionsUpdateParamService updateParamService = new ItemActionsUpdateParamService();

    @EventHandler(priority = EventPriority.LOW)
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (player.hasMetadata("ultimate_item_actions_edit_icon")) {
            String key = player.getMetadata("ultimate_item_actions_edit_icon").get(0).asString();
            UltimateItemActions ultimateItemActions = UltimateItemActionsRegistry.getByKey(key);
            if (ultimateItemActions == null) {
                player.removeMetadata("ultimate_item_actions_edit_icon", UltimateItemActionsMain.getInstance());
                return;
            }

            event.setCancelled(true);

            if (!event.getMessage().equalsIgnoreCase("cancel")) {
                Material icon = null;
                try {
                    icon = Material.getMaterial(event.getMessage());
                    if (icon == null || icon.equals(Material.AIR)) {
                        player.sendMessage("§cIcon not found.");
                        return;
                    }
                } catch (Exception ignored) {
                    player.sendMessage("§cIcon not found.");
                    return;
                }

                updateParamService.updateIcon(
                        ultimateItemActions,
                        icon
                );
            }

            player.removeMetadata("ultimate_item_actions_edit_icon", UltimateItemActionsMain.getInstance());
            UltimateItemActionsMain.getInstance()
                    .getViewFrame()
                    .open(
                            UltimateItemActionsEditMenu.class,
                            player,
                            ultimateItemActions
                    );
        }
    }
}