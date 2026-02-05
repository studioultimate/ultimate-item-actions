package com.gabriaum.ultimate.itemactions.listener.edit;

import com.gabriaum.ultimate.itemactions.UltimateItemActions;
import com.gabriaum.ultimate.itemactions.UltimateItemActionsMain;
import com.gabriaum.ultimate.itemactions.UltimateItemActionsRegistry;
import com.gabriaum.ultimate.itemactions.menu.UltimateItemActionsEditDescriptionMenu;
import com.gabriaum.ultimate.itemactions.service.ItemActionsUpdateParamService;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ItemActionEditDescriptionListener implements Listener {
    private final ItemActionsUpdateParamService updateParamService = new ItemActionsUpdateParamService();

    @EventHandler(priority = EventPriority.LOW)
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (player.hasMetadata("ultimate_item_actions_edit_description")) {
            String key = player.getMetadata("ultimate_item_actions_edit_description").get(0).asString();
            UltimateItemActions ultimateItemActions = UltimateItemActionsRegistry.getByKey(key);
            if (ultimateItemActions == null) {
                player.removeMetadata("ultimate_item_actions_edit_description", UltimateItemActionsMain.getInstance());
                return;
            }

            event.setCancelled(true);

            if (!event.getMessage().equalsIgnoreCase("cancel")) {
                updateParamService.addDescription(
                        ultimateItemActions,
                        event.getMessage()
                );
            }

            player.removeMetadata("ultimate_item_actions_edit_description", UltimateItemActionsMain.getInstance());
            UltimateItemActionsMain.getInstance()
                    .getViewFrame()
                    .open(
                            UltimateItemActionsEditDescriptionMenu.class,
                            player,
                            ultimateItemActions
                    );
        }
    }
}