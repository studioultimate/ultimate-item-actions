package com.gabriaum.ultimate.itemactions.service;

import com.gabriaum.ultimate.itemactions.UltimateItemActions;
import com.gabriaum.ultimate.itemactions.UltimateItemActionsMain;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class ItemActionsUpdateParamService {
    public void updateDisplayName(
            UltimateItemActions ultimateItemActions,
            String displayName
    ) {
        ultimateItemActions.setDisplayName(displayName);
        UltimateItemActionsMain.getInstance()
                .getItemsConfig()
                .set(ultimateItemActions.getKey() + ".display_name", displayName);

        UltimateItemActionsMain.getInstance().getItemsConfig().save();
    }

    public void updateIcon(
            UltimateItemActions ultimateItemActions,
            Material icon
    ) {
        ultimateItemActions.setIcon(icon);
        UltimateItemActionsMain.getInstance()
                .getItemsConfig()
                .set(ultimateItemActions.getKey() + ".icon", icon.name());

        UltimateItemActionsMain.getInstance().getItemsConfig().save();
    }

    public void updateDurability(
            UltimateItemActions ultimateItemActions,
            short durability
    ) {
        ultimateItemActions.setDurability(durability);
        UltimateItemActionsMain.getInstance()
                .getItemsConfig()
                .set(ultimateItemActions.getKey() + ".durability", durability);

        UltimateItemActionsMain.getInstance().getItemsConfig().save();
    }

    public void addDescription(
            UltimateItemActions ultimateItemActions,
            String line
    ) {
        List<String> description = ultimateItemActions.getDescription();
        if (description == null) {
            description = new ArrayList<>();
            ultimateItemActions.setDescription(description);
        }

        description.add(line);

        UltimateItemActionsMain.getInstance()
                .getItemsConfig()
                .set(ultimateItemActions.getKey() + ".description", description);

        UltimateItemActionsMain.getInstance().getItemsConfig().save();
    }

    public void removeDescription(
            UltimateItemActions ultimateItemActions,
            int lineIndex
    ) {
        List<String> description = ultimateItemActions.getDescription();
        if (description == null || description.isEmpty()) return;

        if (lineIndex < 0 || lineIndex >= description.size()) return;

        description.remove(lineIndex);

        UltimateItemActionsMain.getInstance()
                .getItemsConfig()
                .set(ultimateItemActions.getKey() + ".description", description);

        UltimateItemActionsMain.getInstance().getItemsConfig().save();
    }

    public void addAction(
            UltimateItemActions ultimateItemActions,
            String line
    ) {
        List<String> actions = ultimateItemActions.getActions();
        if (actions == null) {
            actions = new ArrayList<>();
            ultimateItemActions.setActions(actions);
        }

        actions.add(line);

        UltimateItemActionsMain.getInstance()
                .getItemsConfig()
                .set(ultimateItemActions.getKey() + ".actions", actions);

        UltimateItemActionsMain.getInstance().getItemsConfig().save();
    }

    public void removeAction(
            UltimateItemActions ultimateItemActions,
            int lineIndex
    ) {
        List<String> actions = ultimateItemActions.getActions();
        if (actions == null || actions.isEmpty()) return;

        if (lineIndex < 0 || lineIndex >= actions.size()) return;

        actions.remove(lineIndex);

        UltimateItemActionsMain.getInstance()
                .getItemsConfig()
                .set(ultimateItemActions.getKey() + ".actions", actions);

        UltimateItemActionsMain.getInstance().getItemsConfig().save();
    }
}