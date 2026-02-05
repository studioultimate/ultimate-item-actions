package com.gabriaum.ultimate.itemactions.service;

import com.gabriaum.ultimate.itemactions.UltimateItemActions;
import com.gabriaum.ultimate.itemactions.infra.util.ItemBuilder;
import de.tr7zw.changeme.nbtapi.NBT;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemActionsGiveableService {
    public void giveTo(
            Player player,
            UltimateItemActions itemActions,
            int amount
    ) {
        ItemStack item = new ItemBuilder(
                itemActions.getIcon(),
                amount,
                itemActions.getDurability()
        )
                .setName(itemActions.getDisplayName())
                .addLore(itemActions.getDescription())
                .getStack();

        NBT.modify(item, nbt -> {
            nbt.setString("ultimate_item_actions", itemActions.getKey());
        });

        player.getInventory().addItem(item);
    }
}