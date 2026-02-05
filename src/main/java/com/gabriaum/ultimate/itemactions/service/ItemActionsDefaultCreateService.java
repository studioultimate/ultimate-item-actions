package com.gabriaum.ultimate.itemactions.service;

import com.gabriaum.ultimate.itemactions.UltimateItemActions;
import com.gabriaum.ultimate.itemactions.UltimateItemActionsMain;
import com.gabriaum.ultimate.itemactions.infra.util.ConfigUtil;
import org.bukkit.Material;

import java.util.ArrayList;

public class ItemActionsDefaultCreateService {
    public UltimateItemActions create(String key) {
        ConfigUtil configUtil = UltimateItemActionsMain.getInstance().getItemsConfig();

        UltimateItemActions ultimateItemActions = new UltimateItemActions(
                key,
                "...",
                Material.INK_SACK,
                (short) 8,
                new ArrayList<>(),
                new ArrayList<>()
        );

        configUtil.set(key + ".icon", ultimateItemActions.getIcon().name());
        configUtil.set(key + ".durability", ultimateItemActions.getDurability());
        configUtil.set(key + ".display_name", ultimateItemActions.getDisplayName());
        configUtil.set(key + ".description", ultimateItemActions.getDescription());
        configUtil.set(key + ".actions", ultimateItemActions.getActions());

        UltimateItemActionsMain.getInstance().getManager().add(ultimateItemActions);
        return ultimateItemActions;
    }
}