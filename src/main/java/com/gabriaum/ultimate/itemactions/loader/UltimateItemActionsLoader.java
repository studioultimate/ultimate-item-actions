package com.gabriaum.ultimate.itemactions.loader;

import com.gabriaum.ultimate.itemactions.UltimateItemActions;
import com.gabriaum.ultimate.itemactions.infra.service.ConsoleService;
import com.gabriaum.ultimate.itemactions.infra.util.ConfigUtil;
import com.gabriaum.ultimate.itemactions.manager.UltimateItemActionsManager;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class UltimateItemActionsLoader {
    private final ConfigUtil itemsConfig;
    private final UltimateItemActionsManager manager;

    public void load() {
        for (String key : itemsConfig.getKeys(false)) {
            ConsoleService.log(
                    String.format(
                            "Reading and caching item %s",
                            key
                    )
            );

            String displayName = itemsConfig.getString(key + ".displayName", key);
            Material material = Material.getMaterial(itemsConfig.getString(key + ".icon"));
            if (material == null || material.equals(Material.AIR)) {
                ConsoleService.error(
                        String.format(
                                "Item %s was not loaded because its material is null/empty.",
                                key
                        )
                );

                continue;
            }

            short durability = Short.parseShort(itemsConfig.getString(key + ".durability", "0"));

            List<String> description = new ArrayList<>();
            if (itemsConfig.contains(key + ".description"))
                description = itemsConfig.getStringList(key + ".description");

            List<String> actions = new ArrayList<>();
            if (itemsConfig.contains(key + ".actions"))
                actions = itemsConfig.getStringList(key + ".actions");

            UltimateItemActions ultimateItemActions = new UltimateItemActions(
                    key,
                    displayName,
                    material,
                    durability,
                    description,
                    actions
            );

            manager.add(ultimateItemActions);

            ConsoleService.fine(
                    String.format(
                            "Item %s was loaded correctly and is now operating.",
                            key
                    )
            );
        }
    }
}