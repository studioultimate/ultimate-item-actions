package com.gabriaum.ultimate.itemactions.listener.edit;

import com.gabriaum.ultimate.itemactions.UltimateItemActions;
import com.gabriaum.ultimate.itemactions.UltimateItemActionsMain;
import com.gabriaum.ultimate.itemactions.UltimateItemActionsRegistry;
import com.gabriaum.ultimate.itemactions.menu.UltimateItemActionsEditMenu;
import com.gabriaum.ultimate.itemactions.service.ItemActionsUpdateParamService;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ItemActionEditDurabilityListener implements Listener {
    private final ItemActionsUpdateParamService updateParamService = new ItemActionsUpdateParamService();

    @EventHandler(priority = EventPriority.LOW)
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (player.hasMetadata("ultimate_item_actions_edit_durability")) {
            String key = player.getMetadata("ultimate_item_actions_edit_durability").get(0).asString();
            UltimateItemActions ultimateItemActions = UltimateItemActionsRegistry.getByKey(key);
            if (ultimateItemActions == null) {
                player.removeMetadata("ultimate_item_actions_edit_durability", UltimateItemActionsMain.getInstance());
                return;
            }

            event.setCancelled(true);

            int durabiltiy = 0;
            try {
                durabiltiy = Integer.parseInt(event.getMessage());
            } catch (Exception ignored) {
                player.sendMessage("§cYou must enter an integer.");
                return;
            }

            updateParamService.updateDurability(
                    ultimateItemActions,
                    (short) durabiltiy
            );

            player.removeMetadata("ultimate_item_actions_edit_durability", UltimateItemActionsMain.getInstance());
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