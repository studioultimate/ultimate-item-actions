package com.gabriaum.ultimate.itemactions.listener;

import com.gabriaum.ultimate.itemactions.UltimateItemActions;
import com.gabriaum.ultimate.itemactions.UltimateItemActionsRegistry;
import com.gabriaum.ultimate.itemactions.service.ItemActionsExecuteService;
import de.tr7zw.changeme.nbtapi.NBT;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.atomic.AtomicReference;

public class PlayerInteractListener implements Listener {
    private final ItemActionsExecuteService executeService = new ItemActionsExecuteService();

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction().name().contains("RIGHT_")) {
            Player player = event.getPlayer();
            ItemStack item = event.getItem();
            if (item == null || item.getType().equals(Material.AIR)) return;

            AtomicReference<UltimateItemActions> reference = new AtomicReference<>(null);
            NBT.get(item, nbt -> {
                if (nbt.hasTag("ultimate_item_actions")) {
                    String key = nbt.getString("ultimate_item_actions");
                    reference.set(UltimateItemActionsRegistry.getByKey(key));
                }
            });

            UltimateItemActions ultimateItemActions = reference.get();
            if (ultimateItemActions == null) return;

            if (item.getAmount() > 1)
                item.setAmount(item.getAmount() - 1);
            else
                player.getInventory().remove(item);

            executeService.handle(
                    player,
                    ultimateItemActions.getActions()
            );
        }
    }
}